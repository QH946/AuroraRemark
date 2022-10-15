package com.qh.auroraremark.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.entity.Voucher;


public interface IVoucherService extends IService<Voucher> {

    /**
     * 查询优惠卷
     *
     * @param shopId 商店id
     * @return {@link Result}
     */
    Result queryVoucherOfShop(Long shopId);

    /**
     * 添加优惠卷
     *
     * @param voucher 凭证
     */
    void addSeckillVoucher(Voucher voucher);
}
