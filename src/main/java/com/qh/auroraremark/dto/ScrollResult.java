package com.qh.auroraremark.dto;

import lombok.Data;

import java.util.List;

/**
 * 查询博客信息数据传输
 *
 * @author qh
 * @date 2022/10/20 14:17:43
 */
@Data
public class ScrollResult {
    /**
     * 小于指定时间戳的笔记
     */
    private List<?> list;
    /**
     * 查询推送的最小时间戳
     */
    private Long minTime;
    /**
     * 偏移量
     */
    private Integer offset;
}
