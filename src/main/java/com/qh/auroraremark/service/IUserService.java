package com.qh.auroraremark.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.auroraremark.dto.LoginFormDTO;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.entity.User;

import javax.servlet.http.HttpSession;


public interface IUserService extends IService<User> {
    Result login(LoginFormDTO loginForm, HttpSession session);

    Result sedCode(String phone, HttpSession session);

    Result sign();
}
