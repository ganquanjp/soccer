package com.soccer.domain.mapper.soccer.custmapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.soccer.bean.resultbean.SoccerOrderBean;
import com.soccer.domain.soccer.CodeMst;
import com.soccer.domain.soccer.WcUser;

public interface SoccerMapper {
    List<SoccerOrderBean> selectAllOrders(Map<String, Object> params);

    List<WcUser> selectAllUsers();
    List<CodeMst> selectAllStages();
    List<CodeMst> selectAllStatus();
    List<CodeMst> selectAllRs();

    int updateOrderFinished(@Param("orderName")String orderName);
}
