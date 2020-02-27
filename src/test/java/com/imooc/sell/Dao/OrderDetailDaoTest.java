package com.imooc.sell.Dao;

import com.imooc.sell.Entity.OrderDetail;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderDetailDaoTest {

    @Autowired
    private OrderDetailDao repository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("001");
        orderDetail.setOrderId("000001");
        orderDetail.setProductIcon("http://xx.jpg");
        orderDetail.setProductId("123456");
        orderDetail.setProductName("方便面");
        orderDetail.setProductPrice(new BigDecimal(1.2));
        orderDetail.setProductQuantity(2);
        repository.save(orderDetail);
    }

    @Test
    void findByOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("000001");
        Assertions.assertNotNull(orderDetailList);
    }
}