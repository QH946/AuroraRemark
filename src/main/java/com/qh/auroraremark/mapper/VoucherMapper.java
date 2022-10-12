package com.qh.auroraremark.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qh.auroraremark.entity.Voucher;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * 券映射器
 *
 * @author qh
 * @date 2022/10/12 16:53:20
 */
public interface VoucherMapper extends BaseMapper<Voucher> {

    List<Voucher> queryVoucherOfShop(@Param("shopId") Long shopId);
}
