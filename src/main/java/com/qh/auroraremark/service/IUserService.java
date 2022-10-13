package com.qh.auroraremark.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.auroraremark.dto.LoginFormDTO;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.entity.User;

import javax.servlet.http.HttpSession;


public interface IUserService extends IService<User> {
    /**
     * 用户登录
     *
     * @param loginForm 登录表单
     * @param session   会话
     * @return {@link Result}
     */
    Result login(LoginFormDTO loginForm, HttpSession session);

    /**
     * 发送手机短信验证码
     *
     * @param phone   电话
     * @param session 会话
     * @return {@link Result}
     */
    Result sedCode(String phone, HttpSession session);

    Result sign();
}
