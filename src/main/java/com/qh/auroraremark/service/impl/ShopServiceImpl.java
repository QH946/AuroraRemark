package com.qh.auroraremark.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.entity.Shop;
import com.qh.auroraremark.mapper.ShopMapper;
import com.qh.auroraremark.service.IShopService;
import com.qh.auroraremark.utils.CacheClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.concurrent.TimeUnit;

import static com.qh.auroraremark.utils.RedisConstants.CACHE_SHOP_KEY;
import static com.qh.auroraremark.utils.RedisConstants.CACHE_SHOP_TTL;

@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop> implements IShopService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private CacheClient cacheClient;

    /**
     * 根据id查询商铺信息
     *
     * @param id id
     * @return {@link Result}
     */
    @Override
    public Result queryById(Long id) {
        // 互斥锁解决缓存击穿
         /*Shop shop1 = cacheClient
                .queryWithMutex(CACHE_SHOP_KEY, id, Shop.class, this::getById, CACHE_SHOP_TTL, TimeUnit.MINUTES);*/

        // 逻辑过期解决缓存击穿
        /* Shop shop2= cacheClient
                 .queryWithLogicalExpire(CACHE_SHOP_KEY, id, Shop.class, this::getById, 20L, TimeUnit.SECONDS);*/
        // 解决缓存穿透
        Shop shop = cacheClient
                .queryWithPassThrough(CACHE_SHOP_KEY, id, Shop.class,
                        this::getById, CACHE_SHOP_TTL, TimeUnit.MINUTES);
        if (shop == null) {
            //不存在，返回错误
            return Result.fail("店铺不存在");
        }
        //返回
        return Result.ok(shop);
    }

    /**
     * 更新商铺信息
     *
     * @param shop 商店
     * @return {@link Result}
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result update(Shop shop) {
        Long id = shop.getId();
        if (id == null) {
            return Result.fail("店铺id不能为空");
        }
        //更新数据库
        this.updateById(shop);
        //删除缓存
        stringRedisTemplate.delete(CACHE_SHOP_KEY + id);
        return Result.ok();
    }

    /**
     * 根据商铺类型分页查询商铺信息
     *
     * @param typeId  id类型
     * @param current 当前
     * @param x       x
     * @param y       y
     * @return {@link Result}
     */
    @Override
    public Result queryShopByType(Integer typeId, Integer current, Double x, Double y) {
        return null;
    }
}
