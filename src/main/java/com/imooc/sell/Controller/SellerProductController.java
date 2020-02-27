package com.imooc.sell.Controller;

import com.imooc.sell.Entity.ProductCategory;
import com.imooc.sell.Entity.ProductInfo;
import com.imooc.sell.Form.ProductForm;
import com.imooc.sell.Service.ProductCategoryService;
import com.imooc.sell.Service.ProductInfoService;
import com.imooc.sell.Utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

//卖家端商品界面
@Controller
@RequestMapping("/seller/product")
@Slf4j
public class SellerProductController {
    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private ProductCategoryService productCategoryService;

    //商品列表(page从第一页开始,size一页有多少条数据)
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value ="size",defaultValue = "5") Integer size,
                             Map<String,Object> map){
        PageRequest request = PageRequest.of(page-1,size);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map);
    }

    //商品上架
    @GetMapping("/onsale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                                Map<String,Object> map){
        try{
            productInfoService.onSale(productId);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        map.put("msg","成功修改上架状态");
        return new ModelAndView("common/success",map);
    }

    //商品下架
    @GetMapping("/offsale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){
        try{
            productInfoService.offSale(productId);
        }catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg","成功修改下架状态");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    //新增或者修改(是否有productId)
    @GetMapping("/modify")
    public ModelAndView modify(@RequestParam(value = "productId",required = false) String productId,
                       Map<String,Object> map){
        if(productId != null){
            ProductInfo productInfo = productInfoService.findOne(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有的类目
        List<ProductCategory> productCategoryList =
                productCategoryService.findAll();
        map.put("productCategoryList",productCategoryList);
        return new ModelAndView("product/modify",map);
    }

    //提交产品表单
    @PostMapping("/save")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/modify");
            return new ModelAndView("common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
        try{
            //不是新增
            if(!form.getProductId().equals("")){
                productInfo = productInfoService.findOne(form.getProductId());
            }else{
                //是新增要加一个id
                form.setProductId(KeyUtil.genUniqueKey());
            }
            BeanUtils.copyProperties(form,productInfo);
            productInfoService.save(productInfo);
        }catch(Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/modify");
            return new ModelAndView("common/error",map);
        }
        map.put("msg","成功处理商品内容");
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }
}
