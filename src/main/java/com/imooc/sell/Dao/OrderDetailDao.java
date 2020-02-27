package com.imooc.sell.Dao;

import com.imooc.sell.Entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailDao extends
        JpaRepository<OrderDetail,String> {
    //根据orderId,获取orderDetail.
    List<OrderDetail> findByOrderId(String orderId);
}
