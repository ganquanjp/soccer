package com.soccer.domain.mapper.soccer;

import com.soccer.domain.soccer.WcUser;

public interface WcUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(WcUser record);

    int insertSelective(WcUser record);

    WcUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(WcUser record);

    int updateByPrimaryKey(WcUser record);
}