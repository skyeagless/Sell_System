package com.imooc.sell;

import com.imooc.sell.Dao.ProductCategoryDao;
import com.imooc.sell.Entity.ProductCategory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.Assertions;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProductCategoryDaoTest {
    @Autowired
    private ProductCategoryDao repository;
    @Test
    @Transactional
    public void saveTest(){
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("最爱");
        productCategory.setCategoryType(1);
        repository.save(productCategory);
        Assertions.assertNotNull(productCategory);
    }

    @Test
    public void getOneTest(){
        ProductCategory productCategory = repository.findById(1).get();
        System.out.println(productCategory.toString());
    }

    @Test
    public void updateTest(){
        ProductCategory productCategory = repository.findById(1).get();
        productCategory.setCategoryType(4);
        repository.save(productCategory);
    }

    @Test
    public void findByCategoryTypeTest(){
        List<Integer> list = Arrays.asList(2,3);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assertions.assertEquals(1,result.size());
    }
}
