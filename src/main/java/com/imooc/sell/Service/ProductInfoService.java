package com.imooc.sell.Service;

import com.imooc.sell.DTO.CartDTO;
import com.imooc.sell.Entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {
    ProductInfo findOne(String productId);
    //只需查找上架的商品
    List<ProductInfo> findUpAll();
    //管理端查找
    Page<ProductInfo> findAll(Pageable pageable);
    ProductInfo save(ProductInfo productInfo);
    //加库存
    void increaseStock(List<CartDTO> cartDTOS);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOS);
    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);

}
