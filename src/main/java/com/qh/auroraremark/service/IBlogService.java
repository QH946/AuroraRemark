package com.qh.auroraremark.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.entity.Blog;


public interface IBlogService extends IService<Blog> {
    /**
     * 查看探店笔记
     *
     * @param id id
     * @return {@link Result}
     */
    Result queryBlogById(Long id);

    /**
     * 查询热门博客
     *
     * @param current 当前
     * @return {@link Result}
     */
    Result queryHotBlog(Integer current);


    /**
     * 查询点赞排行榜
     *
     * @param id id
     * @return {@link Result}
     */
    Result queryBlogLikesById(Long id);

    /**
     * 点赞
     *
     * @param id id
     * @return {@link Result}
     */
    Result likeBlog(Long id);

    /**
     * 保存探店笔记并推送到粉丝的收件箱
     *
     * @param blog 博客
     * @return {@link Result}
     */
    Result saveBlog(Blog blog);

    /**
     * 分页查询推送的博客
     *
     * @param max    马克斯
     * @param offset 抵消
     * @return {@link Result}
     */
    Result queryBlogOfFollow(Long max, Integer offset);

}
