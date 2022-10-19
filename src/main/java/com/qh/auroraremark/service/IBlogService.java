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
    Result queryBlogLikes(Long id);

    /**
     * 点赞
     *
     * @param id id
     * @return {@link Result}
     */
    Result likeBlog(Long id);
}
