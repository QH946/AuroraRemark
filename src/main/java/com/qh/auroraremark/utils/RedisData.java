package com.qh.auroraremark.utils;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * redis数据
 *
 * @author qh
 * @date 2022/10/13 21:27:50
 */
@Data
public class RedisData {
    private LocalDateTime expireTime;
    private Object data;
}
