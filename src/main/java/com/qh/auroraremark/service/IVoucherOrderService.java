package com.qh.auroraremark.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.qh.auroraremark.dto.Result;
import com.qh.auroraremark.entity.VoucherOrder;


public interface IVoucherOrderService extends IService<VoucherOrder> {

    /**
     * 秒杀下单
     *
     * @param voucherId 券id
     */
    Result seckillVoucher(Long voucherId);

    /**
     * 创建优惠卷订单
     *
     * @param voucherId 券id
     * @return {@link Result}
     */
    Result createVoucherOrder(Long voucherId);
}
