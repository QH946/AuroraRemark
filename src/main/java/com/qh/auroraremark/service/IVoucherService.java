package com.qh.auroraremark.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.entity.Voucher;


public interface IVoucherService extends IService<Voucher> {

    /**
     * 查询店铺的优惠卷
     *
     * @param shopId 商店id
     * @return {@link Result}
     */
    Result queryVoucherOfShop(Long shopId);

    /**
     * 添加秒杀卷
     *
     * @param voucher 凭证
     */
    void addSeckillVoucher(Voucher voucher);
}
