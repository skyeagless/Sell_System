package com.imooc.sell.Service;

import com.imooc.sell.Entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    //买家端口
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
    ProductCategory save(ProductCategory productCategory);
}
