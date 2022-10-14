package com.qh.auroraremark.utils;


import com.qh.auroraremark.dto.UserDTO;

/**
 * 管理用户
 *
 * @author qh
 * @date 2022/10/13 21:26:42
 */
public class UserHolder {
    private static final ThreadLocal<UserDTO> tl = new ThreadLocal<>();

    public static void saveUser(UserDTO user){
        tl.set(user);
    }

    public static UserDTO getUser(){
        return tl.get();
    }

    public static void removeUser(){
        tl.remove();
    }
}
