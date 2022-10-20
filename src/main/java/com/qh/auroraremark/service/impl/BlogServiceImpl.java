package com.qh.auroraremark.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.dto.ScrollResult;
import com.qh.auroraremark.dto.UserDTO;
import com.qh.auroraremark.entity.Blog;
import com.qh.auroraremark.entity.Follow;
import com.qh.auroraremark.entity.User;
import com.qh.auroraremark.mapper.BlogMapper;
import com.qh.auroraremark.service.IBlogService;
import com.qh.auroraremark.service.IFollowService;
import com.qh.auroraremark.service.IUserService;
import com.qh.auroraremark.utils.SystemConstants;
import com.qh.auroraremark.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.qh.auroraremark.utils.RedisConstants.BLOG_LIKED_KEY;
import static com.qh.auroraremark.utils.RedisConstants.FEED_KEY;


@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

    @Resource
    private IUserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IFollowService followService;

    @Override
    public Result queryBlogById(Long id) {
        //查询blog
        Blog blog = this.getById(id);
        if (blog == null) {
            return Result.fail("笔记不存在");
        }
        //查询与blog有关的用户
        queryBlogUser(blog);
        return Result.ok(blog);
    }

    @Override
    public Result queryHotBlog(Integer current) {
        // 根据用户查询
        Page<Blog> page = this.query()
                .orderByDesc("liked")
                .page(new Page<>(current, SystemConstants.MAX_PAGE_SIZE));
        // 获取当前页数据
        List<Blog> records = page.getRecords();
        // 查询用户
        records.forEach(blog -> {
            Long userId = blog.getUserId();
            User user = userService.getById(userId);
            blog.setName(user.getNickName());
            blog.setIcon(user.getIcon());
        });
        return Result.ok();
    }


    /**
     * 查询点赞排行榜
     *
     * @param id id
     * @return {@link Result}
     */
    @Override
    public Result queryBlogLikes(Long id) {
        String key = BLOG_LIKED_KEY + id;
        //查询top5的点赞用户 zrange key 0 4
        Set<String> top5 = stringRedisTemplate.opsForZSet().range(key, 0, 4);
        if (top5 == null || top5.isEmpty()) {
            return Result.ok(Collections.emptyList());
        }
        //解析出其中的用户id
        List<Long> ids = top5.stream()
                .map(Long::valueOf)
                .collect(Collectors.toList());
        String idStr = StrUtil.join(",", ids);
        //根据用户id查询用户 WHERE id IN ( 5 , 1 ) ORDER BY FIELD(id, 5, 1)
        List<UserDTO> userDTOS =
                userService
                        .query()
                        .in("id", ids)
                        .last("ORDER BY FIELD(id," + idStr + ")")
                        .list()
                        .stream()
                        .map(user -> BeanUtil.copyProperties(user, UserDTO.class))
                        .collect(Collectors.toList());
        return Result.ok(userDTOS);
    }

    /**
     * 点赞
     *
     * @param id id
     * @return {@link Result}
     */
    @Override
    public Result likeBlog(Long id) {
        //获取登录用户
        Long userId = UserHolder.getUser().getId();
        //判断当前用户是否已经点赞
        String key = BLOG_LIKED_KEY + id;
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        if (score == null) {
            //如果未点赞，可以点赞，点赞数+1
            boolean isSuccess = this.update()
                    .setSql("liked = liked + 1")
                    .eq("id", id)
                    .update();
            // 3.2.保存用户到Redis的set集合  zadd key value score
            if (isSuccess) {
                stringRedisTemplate.opsForZSet().add(key, userId.toString(), System.currentTimeMillis());
            }
        } else {
            //如果已点赞，取消点赞，点赞数-1
            boolean isSuccess = this.update()
                    .setSql("liked = liked - 1")
                    .eq("id", id)
                    .update();
            //把用户从Redis的set集合移除
            if (isSuccess) {
                stringRedisTemplate.opsForZSet().remove(key, userId.toString());
            }
        }
        return Result.ok();
    }

    /**
     * 保存探店笔记并推送到粉丝的收件箱
     *
     * @param blog 博客
     * @return {@link Result}
     */
    @Override
    public Result saveBlog(Blog blog) {
        //获取登录用户
        UserDTO user = UserHolder.getUser();
        blog.setUserId(user.getId());
        //保存探店笔记
        boolean isSuccess = this.save(blog);
        if (!isSuccess) {
            return Result.fail("新增笔记失败");
        }
        //查询笔记作者的所有粉丝 select * from tb_follow where follow_user_id = ?
        List<Follow> follows = followService.query()
                .eq("follow_user_id", user.getId())
                .list();
        //推送笔记id给所有粉丝
        for (Follow follow : follows) {
            //获取粉丝id
            Long userId = follow.getUserId();
            //推送
            String key = FEED_KEY + userId;
            stringRedisTemplate.opsForZSet().add(key, blog.getId().toString(),
                    System.currentTimeMillis());
        }
        return Result.ok(blog.getId());
    }

    /**
     * 分页查询推送的博客
     *
     * @param max    马克斯
     * @param offset 抵消
     * @return {@link Result}
     */
    @Override
    public Result queryBlogOfFollow(Long max, Integer offset) {
        //获取当前用户
        Long userId = UserHolder.getUser().getId();
        //查询邮件箱 ZREVRANGEBYSCORE key Max Min LIMIT offset count
        String key = FEED_KEY + userId;
        Set<ZSetOperations.TypedTuple<String>> typedTuples =
                stringRedisTemplate
                        .opsForZSet()
                        .reverseRangeByScoreWithScores(key, 0, max, offset, 2);
        //非空判断
        if (typedTuples == null || typedTuples.isEmpty()) {
            return Result.ok();
        }
        //解析数据 blogId、minTime（时间戳）、offset
        ArrayList<Object> ids = new ArrayList<>(typedTuples.size());
        long minTime = 0;
        int os = 1;
        for (ZSetOperations.TypedTuple<String> tuple : typedTuples) {
            //获取id
            ids.add(Long.valueOf(tuple.getValue()));
            //获取分数（时间戳）
            long time = tuple.getScore().longValue();
            if (time == minTime) {
                os++;
            } else {
                minTime = time;
                os = 1;
            }
        }
        //根据id查询blog
        String idStr = StrUtil.join(",", ids);
        List<Blog> blogs = this.query()
                .in("id", ids)
                .last("ORDER BY FIELD(id," + idStr + ")")
                .list();
        for (Blog blog : blogs) {
            //查询与blog相关的用户
            queryBlogUser(blog);
            //查看blog是否被点赞
            isBlogLiked(blog);
        }
        //封装并返回
        ScrollResult r = new ScrollResult();
        r.setList(blogs);
        r.setOffset(os);
        r.setMinTime(minTime);
        return Result.ok(r);
    }

    /**
     * 查询与博客相关的用户信息
     *
     * @param blog 博客
     */
    private void queryBlogUser(Blog blog) {
        Long userId = blog.getUserId();
        User user = userService.getById(userId);
        blog.setName(user.getNickName());
        blog.setIcon(user.getIcon());
    }

    /**
     * 查询博客是否被点赞
     *
     * @param blog 博客
     */
    private void isBlogLiked(Blog blog) {
        // 1.获取登录用户
        UserDTO user = UserHolder.getUser();
        if (user == null) {
            // 用户未登录，无需查询是否点赞
            return;
        }
        Long userId = user.getId();
        // 2.判断当前登录用户是否已经点赞
        String key = "blog:liked:" + blog.getId();
        Double score = stringRedisTemplate.opsForZSet().score(key, userId.toString());
        blog.setIsLike(score != null);
    }
}
