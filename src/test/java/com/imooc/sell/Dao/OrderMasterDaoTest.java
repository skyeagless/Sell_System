package com.imooc.sell.Dao;

import com.imooc.sell.Entity.OrderMaster;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class OrderMasterDaoTest {
    @Autowired
    private OrderMasterDao repository;

    private final String OPENID = "001";

    @Test
    void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("000002");
        orderMaster.setBuyerName("skyeagle");
        orderMaster.setBuyerPhone("13032803779");
        orderMaster.setBuyerAddress("CHINA ANHUI");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(100.83));
        repository.save(orderMaster);
    }

    @Test
    void findByBuyerOpenid() {
        PageRequest request = PageRequest.of(0,3);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID,request);
        System.out.println(result.getTotalElements());
        System.out.println(result.getTotalPages());
    }
}