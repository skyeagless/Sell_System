package com.imooc.sell.Dao;

import com.imooc.sell.Entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//泛型的第一個參數為綁定的實體類, 第二個參數為主鍵id的類型
//JPA findby语法总结：https://www.jianshu.com/p/7edcd476e08a
public interface ProductCategoryDao extends
        JpaRepository<ProductCategory,Integer> {
    //根据type编号来查找
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);
}
