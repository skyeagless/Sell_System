package com.imooc.sell.Controller;

import com.imooc.sell.Entity.ProductCategory;
import com.imooc.sell.Entity.ProductInfo;
import com.imooc.sell.Service.ProductCategoryService;
import com.imooc.sell.Service.ProductInfoService;
import com.imooc.sell.Utils.ResultVOUtil;
import com.imooc.sell.ViewObject.ProductInfoVO;
import com.imooc.sell.ViewObject.ProductVO;
import com.imooc.sell.ViewObject.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer/product")
public class BuyProductController {

    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ResultVO list(){
        //1. 查询所有上架商品
        List<ProductInfo> productInfoList = productInfoService.findUpAll();

        //2. 查询类目
        List<Integer> categoryTypeList = new ArrayList<>();
        for(ProductInfo productInfo:productInfoList){
            categoryTypeList.add(productInfo.getCategoryType());
        }
        //返回ProductCategory数组，输入categoryType数组
        List<ProductCategory> productCategoryList =
                productCategoryService.findByCategoryTypeIn(categoryTypeList);

        //3. 数据拼装
        List<ProductVO> productVOList = new ArrayList<>();
        for(ProductCategory productCategory: productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            for(ProductInfo productInfo : productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }

            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }
}
