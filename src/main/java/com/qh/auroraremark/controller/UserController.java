package com.qh.auroraremark.controller;


import cn.hutool.core.bean.BeanUtil;
import com.qh.auroraremark.dto.LoginFormDTO;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.dto.UserDTO;
import com.qh.auroraremark.entity.User;
import com.qh.auroraremark.entity.UserInfo;
import com.qh.auroraremark.service.IUserInfoService;
import com.qh.auroraremark.service.IUserService;
import com.qh.auroraremark.utils.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IUserInfoService userInfoService;


    /**
     * 发送手机验证码
     */
    @PostMapping("code")
    public Result sendCode(@RequestParam("phone") String phone, HttpSession session) {

        return userService.sedCode(phone, session);
    }


    /**
     * 用户登录
     *
     * @param loginForm 登录参数，包含手机号、验证码；或者手机号、密码
     * @param session   会话
     * @return {@link Result}
     */
    @PostMapping("/login")
    public Result login(@RequestBody LoginFormDTO loginForm, HttpSession session) {

        return userService.login(loginForm, session);
    }


    /**
     * 用户登出
     *
     * @return {@link Result}
     */
    @PostMapping("/logout")
    public Result logout() {
        UserHolder.removeUser();
        return Result.ok("登出成功");
    }

    /**
     * 获取当前登录的用户
     *
     * @return {@link Result}
     */
    @GetMapping("/me")
    public Result me() {
        UserDTO user = UserHolder.getUser();
        return Result.ok(user);
    }

    @GetMapping("/info/{id}")
    public Result info(@PathVariable("id") Long userId) {
        // 查询详情
        UserInfo info = userInfoService.getById(userId);
        if (info == null) {
            // 没有详情，应该是第一次查看详情
            return Result.ok();
        }
        info.setCreateTime(null);
        info.setUpdateTime(null);
        // 返回
        return Result.ok(info);
    }

    /**
     * 根据id查询用户
     *
     * @param userId 用户id
     * @return {@link Result}
     */
    @GetMapping("/{id}")
    public Result queryUserById(@PathVariable("id") Long userId) {
        // 查询详情
        User user = userService.getById(userId);
        if (user == null) {
            return Result.ok();
        }
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        // 返回
        return Result.ok(userDTO);
    }

    /**
     * 用户签到
     *
     * @return {@link Result}
     */
    @PostMapping("/sign")
    public Result sign(){
        return userService.sign();
    }

    /**
     * 统计签到次数
     *
     * @return {@link Result}
     */
    @GetMapping("/sign/count")
    public Result signCount(){
        return userService.signCount();
    }
}
