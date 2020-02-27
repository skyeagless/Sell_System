package com.imooc.sell.Service.impl;

import com.imooc.sell.Entity.ProductInfo;
import com.imooc.sell.enums.ProductStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl infoService;

    @Test
    void findOne() {
        ProductInfo productInfo = infoService.findOne("123456");
        Assertions.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    void findUpAll() {
        List<ProductInfo> productInfoList = infoService.findUpAll();
        Assertions.assertNotEquals(0,productInfoList.size());
    }

    @Test
    void findAll() {
        PageRequest request = PageRequest.of(0,1);
        Page<ProductInfo> productInfoPage = infoService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("654321");
        productInfo.setProductName("方便面");
        productInfo.setProductPrice(new BigDecimal(2.65));
        productInfo.setProductStock(1000);
        productInfo.setProductDescription("非常好吃");
        productInfo.setProductIcon("HaoJinDao.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(10);
        ProductInfo result = infoService.save(productInfo);
        Assertions.assertNotNull(result);
    }

    @Test
    public void onSale(){
        ProductInfo productInfo = infoService.onSale("654321");
        Assertions.assertEquals(ProductStatusEnum.UP.getCode(),productInfo.getProductStatus());
    }

    @Test
    public void offSale(){
        ProductInfo productInfo = infoService.offSale("654321");
        Assertions.assertEquals(ProductStatusEnum.DOWN.getCode(),productInfo.getProductStatus());
    }
}