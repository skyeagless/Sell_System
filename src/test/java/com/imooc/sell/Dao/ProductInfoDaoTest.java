package com.imooc.sell.Dao;

import com.imooc.sell.Entity.ProductInfo;
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
class ProductInfoDaoTest {
    @Autowired
    private ProductInfoDao repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("皮蛋");
        productInfo.setProductPrice(new BigDecimal(12.65));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("好吃");
        productInfo.setProductIcon("dddd.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(10);

        ProductInfo result = repository.save(productInfo);
        Assertions.assertNotNull(result);
    }

    @Test
    public void findByProductStatus(){
        List<ProductInfo> productInfoList = repository.findByProductStatus(0);
        Assertions.assertNotEquals(0,productInfoList.size());
    }
}