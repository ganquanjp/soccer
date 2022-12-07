package com.soccer.domain.mapper.soccer;

import com.soccer.domain.soccer.WcOrder;

public interface WcOrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(WcOrder record);

    int insertSelective(WcOrder record);

    WcOrder selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(WcOrder record);

    int updateByPrimaryKey(WcOrder record);
}