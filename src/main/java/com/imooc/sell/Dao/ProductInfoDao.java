package com.imooc.sell.Dao;

import com.imooc.sell.Entity.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductInfoDao
        extends JpaRepository<ProductInfo,String>{
   //查询上架的产品
   List<ProductInfo> findByProductStatus(Integer productStatus);
}

