package com.imooc.sell.Service.impl;

import com.imooc.sell.DTO.OrderDTO;
import com.imooc.sell.Service.BuyerService;
import com.imooc.sell.Service.OrderService;
import com.imooc.sell.enums.ExceptionResultEnum;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderid) {
        OrderDTO orderDTO = orderService.findone(orderid);
        if(orderDTO == null){
            return null;
        }
        //判断是否是自己的订单
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[查询订单列表] 订单的openid不一致，openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ExceptionResultEnum.ORDER_OPENID_NOT_EQUAL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderid) {
        OrderDTO orderDTO = orderService.findone(orderid);
        orderService.cancel(orderDTO);

        if(orderDTO == null){
            log.error("[取消订单列表] 找不到这个订单，openid={}",openid);
            throw new SellException(ExceptionResultEnum.ORDER_NOT_EXIST);
        }
        //判断是否是自己的订单
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[取消订单列表] 订单的openid不一致，openid={},orderDTO={}",openid,orderDTO);
            throw new SellException(ExceptionResultEnum.ORDER_OPENID_NOT_EQUAL);
        }
        return orderDTO;
    }
}
