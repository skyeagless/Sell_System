package com.imooc.sell.ViewObject;

import com.imooc.sell.DTO.OrderDTO;
import lombok.Data;

import java.util.List;

//http请求给前端的对象
@Data
public class ListResultVO {
    //错误码
    private Integer code;
    //提示信息
    private String msg;
    //返回的具体内容
    private List<OrderDTO> data;
}
