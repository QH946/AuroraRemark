package com.qh.auroraremark.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.auroraremark.entity.Blog;
import com.qh.auroraremark.mapper.BlogMapper;
import com.qh.auroraremark.service.IBlogService;
import org.springframework.stereotype.Service;


@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements IBlogService {

}
