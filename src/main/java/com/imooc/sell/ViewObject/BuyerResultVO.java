package com.imooc.sell.ViewObject;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class BuyerResultVO {
    //错误码
    private Integer code;
    //提示信息
    private String msg;
    //返回的具体内容
    List<Map<String,String>> data;
}


