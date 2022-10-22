package com.qh.auroraremark.Interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.qh.auroraremark.dto.UserDTO;
import com.qh.auroraremark.utils.RedisConstants;
import com.qh.auroraremark.utils.UserHolder;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 刷新令牌拦截器
 *
 * @author qh
 * @date 2022/10/22 14:03:13
 */
public class RefreshTokenInterceptor implements HandlerInterceptor {
    private final StringRedisTemplate stringRedisTemplate;

    public RefreshTokenInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //从请求头中获取token
        String token = request.getHeader("authorization");
        if (StringUtils.isEmpty(token)) {
            //不存在token
            return true;
        }
        //从redis中获取用户
        Map<Object, Object> userMap =
                stringRedisTemplate.opsForHash()
                        .entries(RedisConstants.LOGIN_USER_KEY + token);
        //用户不存在
        if (userMap.isEmpty()) {
            return true;
        }
        //hash转UserDTO存入ThreadLocal
        UserHolder.saveUser(BeanUtil.fillBeanWithMap(userMap, new UserDTO(), false));
        //token续命
        stringRedisTemplate.expire(RedisConstants.LOGIN_USER_KEY + token, RedisConstants.LOGIN_USER_TTL, TimeUnit.MINUTES);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserHolder.removeUser();
    }
}