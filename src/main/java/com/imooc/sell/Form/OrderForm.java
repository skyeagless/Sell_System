package com.imooc.sell.Form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class OrderForm {
    @NotEmpty(message="姓名必填")
    private String name;
    @NotEmpty(message = "手机号必填")
    private String phone;
    @NotEmpty(message = "买家地址必填")
    private String address;
    @NotEmpty(message = "openid比填")
    private String openid;

    //购物车信息(Json格式的字符)
    @NotEmpty(message = "购物车信息不为空")
    private String items;
}

