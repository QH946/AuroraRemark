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
}
