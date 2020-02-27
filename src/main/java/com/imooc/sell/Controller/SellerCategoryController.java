package com.imooc.sell.Controller;

import com.imooc.sell.Entity.ProductCategory;
import com.imooc.sell.Form.CategoryForm;
import com.imooc.sell.Service.ProductCategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

//卖家类目controller
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){
        List<ProductCategory> categoryList = productCategoryService.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);
    }

    @GetMapping("/modify")
    public ModelAndView modify(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                                          Map<String,Object> map){
        if(categoryId != null){
            ProductCategory productCategory = productCategoryService.findOne(categoryId);
            map.put("productCategory",productCategory);
        }
        return new ModelAndView("category/modify",map);
    }

    //新增类目表单
    @PostMapping("/save")
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/modify");
            return new ModelAndView("common/error",map);
        }
        ProductCategory productCategory = new ProductCategory();
        try{
            //不是新增
            if(!(form.getCategoryId() == null)){
                productCategory = productCategoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form,productCategory);
            productCategoryService.save(productCategory);
        }catch(Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/modify");
            return new ModelAndView("common/error",map);
        }
        map.put("msg","成功处理商品类别内容");
        map.put("url","/sell/seller/category/list");
        return new ModelAndView("common/success",map);
    }
}
