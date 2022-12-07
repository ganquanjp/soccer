package com.soccer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.soccer.bean.resultbean.SoccerOrderBean;
import com.soccer.domain.soccer.WcOrder;
import com.soccer.service.SoccerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
public class SoccerController extends BaseController {

    @Autowired
    private SoccerService soccerService;

    /**
     * 转到主菜单
     * 
     * @param orderName
     * @return
     */
    @GetMapping("/soccer")
    public ModelAndView gotoSoccerMenu() {
        return setModelAndView("SoccerMenu", "", null);
    }

    /**
     * 转到订单一览画面
     * 
     * @param orderName
     * @return
     */
    @PostMapping("/soccerorderlist")
    public ModelAndView gotoSoccerOrderList(
        @RequestParam(name = "orderName") String orderName,
        @RequestParam(name = "orderStatus") String orderStatus,
        @RequestParam(name = "stageId") String stageId,
        @RequestParam(name = "showFlag") String showFlag
    ) {
        HashMap<String, Object> data = new HashMap<String, Object>();

        data.put("userList", soccerService.getAllUsers());
        data.put("stageList", soccerService.getAllStages());
        data.put("statusList", soccerService.getAllStatus());
        data.put("soccerOrderList", getSoccerOrderList(orderName, orderStatus, stageId));
        data.put("orderName", orderName);
        data.put("orderStatus", orderStatus);
        data.put("stageId", stageId);
        data.put("showFlag", showFlag);

        return setModelAndView(
              "SoccerOrderList"
            , "data"
            , data
            );
    }
 
    /**
     * 转到下单画面
     * 
     * @param orderName
     * @return
     */
    @PostMapping("/soccerorder")
    public ModelAndView gotoSoccerOrder() {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("userList", soccerService.getAllUsers());
        data.put("stageList", soccerService.getAllStages());
        data.put("rsList", soccerService.getAllRs());
        return setModelAndView("SoccerOrder", "data", data);
    }

    /**
     * 新订单生成
     * 
     * @param orderName
     * @return
     */
    @RequestMapping(value="/newsoccerorder", method=RequestMethod.POST)
    public ModelAndView newSoccerOrder(
        @ModelAttribute WcOrder wcOrder, Model model
    ) {
        wcOrder.setGroupId("001");
        wcOrder.setStatus("0");
        soccerService.newOrder(wcOrder);

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("userList", soccerService.getAllUsers());
        data.put("stageList", soccerService.getAllStages());
        data.put("statusList", soccerService.getAllStatus());
        data.put("soccerOrderList", getSoccerOrderList(wcOrder.getName(), wcOrder.getStatus(), wcOrder.getCodeId()));
        data.put("orderName", wcOrder.getName());
        data.put("orderStatus", wcOrder.getStatus());
        data.put("stageId", wcOrder.getCodeId());
        data.put("showFlag", "1");

        return setModelAndView(
              "SoccerOrderList"
            , "data"
            , data
            );
    }

    /**
     * 订单一览取得
     * 
     * @return
     */
    private List<SoccerOrderBean> getSoccerOrderList(String orderName, String orderStatus, String stage){
        List<SoccerOrderBean> soccerOrderList = new ArrayList<>();
        soccerOrderList = soccerService.getSoccerOrderList(orderName, orderStatus, stage);
        return soccerOrderList;
    }

    /**
     * 设置返回页面
     * 
     * @param viewName
     * @param attributeName
     * @param attributeValue
     * @return
     */
    private ModelAndView setModelAndView(String viewName, String attributeName, Object attributeValue){
        // 获取详细内容
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject(attributeName, attributeValue);
        // html
        modelAndView.setViewName(viewName);
        return modelAndView;
    }
}
