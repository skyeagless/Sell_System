package com.imooc.sell.Service.impl;

import com.imooc.sell.DTO.OrderDTO;
import com.imooc.sell.Entity.OrderDetail;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Slf4j
class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    private final String OPENID = "001";
    private final String ORDER_ID = "2112711581233024233";

    @Test
    void create() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("LyuYang");
        orderDTO.setBuyerAddress("SWJTU");
        orderDTO.setBuyerPhone("13855920298");
        orderDTO.setBuyerOpenid(OPENID);

        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("123456");
        orderDetail.setProductQuantity(10);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("654321");
        orderDetail2.setProductQuantity(10);

        orderDetailList.add(orderDetail);
        orderDetailList.add(orderDetail2);

        //建立好购物车类(List<orderDTO>
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.info("[创建订单]result={}",result);
    }

    @Test
    void findone() {
        OrderDTO result = orderService.findone(ORDER_ID);
        log.info("查询单个订单 result={}",result);
        Assertions.assertNotNull(result);
    }

    @Test
    void findList() {
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(OPENID,pageRequest);
        Assertions.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    void findAllList(){
        PageRequest pageRequest = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findAllList(pageRequest);
        Assertions.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    void cancel() {
        OrderDTO result = orderService.findone(ORDER_ID);
        OrderDTO resultcancel = orderService.cancel(result);
        Assertions.assertEquals(OrderStatusEnum.CANCEL.getCode(),resultcancel.getOrderStatus());
    }

    @Test
    void finish() {
        OrderDTO result = orderService.findone(ORDER_ID);
        OrderDTO resultfinish = orderService.finish(result);
        Assertions.assertEquals(OrderStatusEnum.FINISHED.getCode(),resultfinish.getOrderStatus());
    }

    @Test
    void paid() {
        OrderDTO result = orderService.findone(ORDER_ID);
        OrderDTO resultpaid = orderService.paid(result);
        Assertions.assertEquals(PayStatusEnum.SUCCESS.getCode(),resultpaid.getPayStatus());
    }
}