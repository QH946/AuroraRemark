package com.qh.auroraremark.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.auroraremark.entity.BlogComments;
import com.qh.auroraremark.mapper.BlogCommentsMapper;
import com.qh.auroraremark.service.IBlogCommentsService;
import org.springframework.stereotype.Service;


@Service
public class BlogCommentsServiceImpl extends ServiceImpl<BlogCommentsMapper, BlogComments> implements IBlogCommentsService {

}
