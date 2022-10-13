package com.qh.auroraremark.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.entity.Shop;


public interface IShopService extends IService<Shop> {
    /**
     * 根据id查询商铺信息
     *
     * @param id id
     * @return {@link Result}
     */
    Result queryById(Long id);

    /**
     * 更新商铺信息
     *
     * @param shop 商店
     * @return {@link Result}
     */
    Result update(Shop shop);

    /**
     * 根据商铺类型分页查询商铺信息
     *
     * @param typeId  id类型
     * @param current 当前
     * @param x       x
     * @param y       y
     * @return {@link Result}
     */
    Result queryShopByType(Integer typeId, Integer current, Double x, Double y);
}
