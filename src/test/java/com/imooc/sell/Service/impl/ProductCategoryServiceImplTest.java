package com.imooc.sell.Service.impl;

import com.imooc.sell.Entity.ProductCategory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ProductCategoryServiceImplTest {
    @Autowired
    private ProductCategoryServiceImpl categoryService;

    @Test
    void findOne() {
        ProductCategory productCategory = categoryService.findOne(1);
        Assertions.assertEquals(1,productCategory.getCategoryId());
    }

    @Test
    void findAll() {
        List<ProductCategory> productCategoryList = categoryService.findAll();
        Assertions.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    void findByCategoryTypeIn() {
        List<ProductCategory> productCategoryList =
                categoryService.findByCategoryTypeIn(Arrays.asList(2,4));
        Assertions.assertEquals(2,productCategoryList.size());
    }

    @Test
    void save() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("男生66");
        productCategory.setCategoryType(10);
        ProductCategory saveresult = categoryService.save(productCategory);
        Assertions.assertNotNull(saveresult);
    }
}