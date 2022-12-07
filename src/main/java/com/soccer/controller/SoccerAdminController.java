package com.soccer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.soccer.bean.resultbean.SoccerOrderBean;
import com.soccer.domain.soccer.WcOrder;
import com.soccer.domain.soccer.WcOrderDetail;
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

@RestController
public class SoccerAdminController extends BaseController {

    @Autowired
    private SoccerService soccerService;
     
    /**
     * 转到管理员主画面
     * 
     * @param orderName
     * @return
     */
    @GetMapping("/socceradmin")
    public ModelAndView SoccerAdmin() {
        return setModelAndView("SoccerAdminMenu", "", null);
    }

   /**
     * 确认中订单一览
     * 
     * @param orderName
     * @return
     */
    @RequestMapping(value="/confirmorderlist", method=RequestMethod.POST)
    public ModelAndView getWcConfirmList(
        @RequestParam(name = "orderStatus") String orderStatus
    ) {
        return setModelAndView(
              "SoccerAdminConfirmList"
            , "soccerOrderList"
            , getSoccerOrderList(
                      ""
                    , orderStatus
                    , ""
                )
            );
    }

   /**
     * 转到订单详细画面
     * 
     * @return
     */
    @RequestMapping(value="/orderdetail", method=RequestMethod.POST)
    public ModelAndView ConfirmOrder(
        @RequestParam(name = "orderId") String orderId
    ) {
        WcOrderDetail wcOrderDetail = soccerService.selectOrder(orderId);
        return setModelAndView(
              "SoccerAdminConfirmOrder"
            , "wcOrderDetail"
            , wcOrderDetail
            );
    }

  /**
     * 订单确认
     * 
     * @return
     */
    @RequestMapping(value="/confirmorder", method=RequestMethod.POST)
    public ModelAndView confirmOrder(
        @ModelAttribute WcOrder wcOrder, Model model
    ) {
        wcOrder.setStatus("1");
        soccerService.updateOrder(wcOrder);
        return setModelAndView(
              "SoccerAdminConfirmList"
            , "soccerOrderList"
            , getSoccerOrderList(
                      wcOrder.getName()
                    , "0"
                    , wcOrder.getCodeId()
                )
            );
    }

      /**
     * 订单关闭
     * 
     * @return
     */
    @RequestMapping(value="/closeorder", method=RequestMethod.POST)
    public ModelAndView closeOrder(
        @ModelAttribute WcOrder wcOrder, Model model
    ) {
        wcOrder.setStatus("9");
        soccerService.updateOrder(wcOrder);
        return setModelAndView(
              "SoccerAdminConfirmList"
            , "soccerOrderList"
            , getSoccerOrderList(
                      wcOrder.getName()
                    , "9"
                    , wcOrder.getCodeId()
                )
            );
    }

    /**
     * 结算用戶选择
     * 
     * @return
     */
    @RequestMapping(value="/userlist", method=RequestMethod.POST)
    public ModelAndView getUserList() {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("userList", soccerService.getAllUsers());
        data.put("showFlag", "0");
        data.put("userName","");
        return setModelAndView(
                "SoccerUserList"
                , "data"
                , data
            );
    }
    
    /**
     * 用戶结算订单一览
     * 
     * @return
     */
    @RequestMapping(value="/userorderlist", method=RequestMethod.POST)
    public ModelAndView getUserOrderList(
        @RequestParam(name = "orderName") String orderName,
        @RequestParam(name = "orderStatus") String orderStatus
    ) {
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("userList", soccerService.getAllUsers());
        data.put("soccerOrderList", getSoccerOrderList(orderName, orderStatus, ""));
        data.put("showFlag", "1");
        data.put("userName", orderName);
        return setModelAndView(
                "SoccerUserList"
                , "data"
                , data
            );
    }

     /**
     * 结算(该方法已废止)
     * 
     * @return
     */
    @RequestMapping(value="/payorder", method=RequestMethod.POST)
    public ModelAndView payorder(
        @ModelAttribute WcOrder wcOrder, Model model
    ) {
        wcOrder.setStatus("2");
        soccerService.updateOrder(wcOrder);
        return setModelAndView(
              "SoccerAdminConfirmList"
            , "soccerOrderList"
            , getSoccerOrderList(
                      ""
                    , "1"
                    , wcOrder.getCodeId()
                )
            );
    }

    /**
     * 订单结束
     * 
     * @return
     */
    @RequestMapping(value="/finishorder", method=RequestMethod.POST)
    public ModelAndView finishOrder(
        @RequestParam(name = "orderName") String orderName
    ) {
        soccerService.updateOrderFinished(orderName);

        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("userList", soccerService.getAllUsers());
        data.put("showFlag", "0");
        data.put("userName","");
        return setModelAndView(
                "SoccerUserList"
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
