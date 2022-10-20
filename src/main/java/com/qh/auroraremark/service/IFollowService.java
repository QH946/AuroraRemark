package com.qh.auroraremark.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.entity.Follow;


public interface IFollowService extends IService<Follow> {

    /**
     * 关注
     *
     * @param followUserId 遵循用户id
     * @param isFollow     是遵循
     * @return {@link Result}
     */
    Result follow(Long followUserId, Boolean isFollow);

    /**
     * 查询是否关注
     *
     * @param followUserId 遵循用户id
     * @return {@link Result}
     */
    Result isFollow(Long followUserId);

    /**
     * 共同关注
     *
     * @param id id
     * @return {@link Result}
     */
    Result followCommons(Long id);
}
