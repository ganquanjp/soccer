package com.soccer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.soccer.bean.resultbean.SoccerOrderBean;
import com.soccer.domain.soccer.CodeMst;
import com.soccer.domain.soccer.WcOrder;
import com.soccer.domain.soccer.WcOrderDetail;
import com.soccer.domain.soccer.WcUser;
import com.soccer.domain.mapper.soccer.WcOrderMapper;
import com.soccer.domain.mapper.soccer.WcUserMapper;
import com.soccer.domain.mapper.soccer.CodeMstMapper;
import com.soccer.domain.mapper.soccer.custmapper.SoccerMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SoccerService {

    @Autowired
    SoccerMapper soccerMapper;

    @Autowired
    WcOrderMapper wcOrderMapper;

    @Autowired
    WcUserMapper wcUserMapper;

    @Autowired
    CodeMstMapper codeMstMapper;

    public List<SoccerOrderBean> getSoccerOrderList(String orderName, String orderStatus, String stageId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("orderName", orderName);
        params.put("orderStatus", orderStatus);
        params.put("stageId", stageId);
        return soccerMapper.selectAllOrders(params);
    }

    public void newOrder(WcOrder wcOrder){
        wcOrderMapper.insert(wcOrder);
    }

    public WcOrderDetail selectOrder(String orderId){
        WcOrder wcOrder = wcOrderMapper.selectByPrimaryKey(orderId);
        WcOrderDetail wcOrderDetail = new WcOrderDetail();
        wcOrderDetail.setOrderId(wcOrder.getOrderId());
        wcOrderDetail.setName(wcOrder.getName());
        wcOrderDetail.setUserName(wcUserMapper.selectByPrimaryKey(wcOrder.getName()).getName());
        wcOrderDetail.setCodeId(wcOrder.getCodeId());
        wcOrderDetail.setStageName(codeMstMapper.selectByPrimaryKey("001", wcOrder.getCodeId()).getCodeName());
        wcOrderDetail.setTeam(wcOrder.getTeam());
        wcOrderDetail.setRsName(
            wcOrder.getRs().equals("")?wcOrder.getRs():
            codeMstMapper.selectByPrimaryKey("003", wcOrder.getRs()).getCodeName()
            );
        wcOrderDetail.setHandicap(wcOrder.getHandicap());
        wcOrderDetail.setOdds(wcOrder.getOdds());
        wcOrderDetail.setAmount(wcOrder.getAmount());
        wcOrderDetail.setResult(wcOrder.getResult());
        wcOrderDetail.setStatus(wcOrder.getStatus());
        wcOrderDetail.setConfirmTime(wcOrder.getConfirmTime());
        return wcOrderDetail;
    }

    public List<WcUser> getAllUsers(){
        return soccerMapper.selectAllUsers();
    }

    public List<CodeMst> getAllStages(){
        return soccerMapper.selectAllStages();
    }

    public List<CodeMst> getAllStatus(){
        return soccerMapper.selectAllStatus();
    }

    public void updateOrder(WcOrder wcOrder){
        wcOrderMapper.updateByPrimaryKeySelective(wcOrder);
    }

    public void updateOrderFinished(String orderName){
        soccerMapper.updateOrderFinished(orderName);
    }

    public List<CodeMst> getAllRs(){
        return soccerMapper.selectAllRs();
    }
}
