package com.imooc.sell.Service;

import com.imooc.sell.DTO.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    //创建订单
    OrderDTO create(OrderDTO orderDTO);

    //查询单个订单
    OrderDTO findone(String orderId);

    //查询某个人的订单列表
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    //查询所有人的订单列表
    Page<OrderDTO> findAllList(Pageable pageable);

    //取消订单
    OrderDTO cancel(OrderDTO orderDTO);

    //完结订单
    OrderDTO finish(OrderDTO orderDTO);

    //支付订单
    OrderDTO paid(OrderDTO orderDTO);
}
