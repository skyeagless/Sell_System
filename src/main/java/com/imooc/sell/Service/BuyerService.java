package com.imooc.sell.Service;


import com.imooc.sell.DTO.OrderDTO;

//买家查询一个总订单和删除一个总订单
public interface BuyerService {
   OrderDTO findOrderOne(String openid,String orderid);
   OrderDTO cancelOrder(String openid,String orderid);
}
