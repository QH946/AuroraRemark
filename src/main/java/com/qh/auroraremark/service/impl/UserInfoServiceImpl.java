package com.qh.auroraremark.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.auroraremark.entity.UserInfo;
import com.qh.auroraremark.mapper.UserInfoMapper;
import com.qh.auroraremark.service.IUserInfoService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;


@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements IUserInfoService {

    @Override
    public boolean saveBatch(Collection<UserInfo> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdateBatch(Collection<UserInfo> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean updateBatchById(Collection<UserInfo> entityList, int batchSize) {
        return false;
    }

    @Override
    public boolean saveOrUpdate(UserInfo entity) {
        return false;
    }

    @Override
    public UserInfo getOne(Wrapper<UserInfo> queryWrapper, boolean throwEx) {
        return null;
    }

    @Override
    public Map<String, Object> getMap(Wrapper<UserInfo> queryWrapper) {
        return null;
    }

    @Override
    public <V> V getObj(Wrapper<UserInfo> queryWrapper, Function<? super Object, V> mapper) {
        return null;
    }

    @Override
    public UserInfoMapper getBaseMapper() {
        return null;
    }

    @Override
    public Class<UserInfo> getEntityClass() {
        return null;
    }
}
