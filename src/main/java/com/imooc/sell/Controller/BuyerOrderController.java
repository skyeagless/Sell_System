package com.imooc.sell.Controller;

import com.imooc.sell.DTO.OrderDTO;
import com.imooc.sell.Form.OrderForm;
import com.imooc.sell.Service.BuyerService;
import com.imooc.sell.Service.OrderService;
import com.imooc.sell.Utils.BuyerResultVOUtil;
import com.imooc.sell.Utils.ListResultVOUtil;
import com.imooc.sell.ViewObject.BuyerResultVO;
import com.imooc.sell.ViewObject.ListResultVO;
import com.imooc.sell.convert.OrderForm2OrderDTO;
import com.imooc.sell.enums.ExceptionResultEnum;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//用户的订购
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public BuyerResultVO create(@Valid OrderForm orderForm,
                                BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确,orderForm={}",orderForm);
            throw new SellException(ExceptionResultEnum.PARAMS_NOT_RIGHT.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());

        }
        OrderDTO orderDTO = OrderForm2OrderDTO.convert(orderForm);
        if(orderDTO.getOrderDetailList().size()==0){
            log.error("【创建订单】购物车为空");
            throw new SellException(ExceptionResultEnum.ORDER_DETAIL_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);

        //orderDTO--->buyerResultVO
        List<Map<String,String>> data = new ArrayList<>();
        HashMap<String,String> myMap = new HashMap<String, String>();
        myMap.put("orderId",createResult.getOrderId());
        data.add(myMap);

        BuyerResultVO buyerResultVO = BuyerResultVOUtil.success(data);
        return buyerResultVO;
    }

    //订单列表
    @GetMapping("/list")
    public ListResultVO list(@RequestParam("openid") String openid,
                             @RequestParam(value = "page",defaultValue = "0") Integer page,
                             @RequestParam(value = "size",defaultValue = "10") Integer size){
        if(openid.isEmpty()){
            log.error("[查询订单列表] openid为空");
            throw new SellException(ExceptionResultEnum.PARAMS_NOT_RIGHT);
        }
        PageRequest request = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid,request);
        return ListResultVOUtil.success(orderDTOPage.getContent());
    }

    //查看单个订单
    @GetMapping("/detail")
    public ListResultVO detail(@RequestParam("openid") String openid,
                             @RequestParam("orderid") String orderid){
        if(openid.isEmpty()){
            log.error("[查询订单列表] openid为空");
            throw new SellException(ExceptionResultEnum.PARAMS_NOT_RIGHT);
        }

        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderid);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderDTOList.add(orderDTO);
        return ListResultVOUtil.success(orderDTOList);
    }

    //取消订单
    @PostMapping("/cancel")
    public ListResultVO cancel(@RequestParam("openid") String openid,
                               @RequestParam("orderid") String orderid){

        buyerService.cancelOrder(openid,orderid);
        return ListResultVOUtil.success();
    }
}
