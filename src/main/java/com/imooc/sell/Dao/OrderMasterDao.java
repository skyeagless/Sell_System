package com.imooc.sell.Dao;

import com.imooc.sell.Entity.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderMasterDao extends
        JpaRepository<OrderMaster,String> {
    //分页查每个人的订单
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);
}
