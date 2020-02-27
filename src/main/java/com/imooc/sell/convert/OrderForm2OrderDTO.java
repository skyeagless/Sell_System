package com.imooc.sell.convert;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.imooc.sell.DTO.OrderDTO;
import com.imooc.sell.Entity.OrderDetail;
import com.imooc.sell.Form.OrderForm;
import com.imooc.sell.enums.ExceptionResultEnum;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderForm2OrderDTO {
    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try{
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>(){}.getType());
        }catch(Exception e){
            log.error("【对象转换】错误,string={}",orderForm.getItems());
            throw new SellException(ExceptionResultEnum.PARAMS_NOT_RIGHT);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
