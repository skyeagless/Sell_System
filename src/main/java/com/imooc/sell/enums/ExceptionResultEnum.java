package com.imooc.sell.enums;

import lombok.Getter;

@Getter
public enum ExceptionResultEnum {
    SUCCESS(0,"成功"),
    PARAMS_NOT_RIGHT(1,"表单参数不正确"),
    ORDER_DETAIL_EMPTY(2,"购物车为空"),
    ORDER_OPENID_NOT_EQUAL(3,"订单的Openid不一致"),
    PRODUCT_NOT_EXIST(10,"商品不存在"),
    PRODUCT_STOCK_ERROR(11,"库存不足"),
    ORDER_NOT_EXIST(12,"订单不存在"),
    ORDER_DETAIL_NOT_EXIST(13,"订单详细信息不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_ERROR(15,"更新失败"),
    ORDER_NO_PRODUCT_DETAILS(16,"订单中无商品详情"),
    ORDER_FINISHED_ERROR(17,"完结失败"),
    ORDER_PAID_ERROR(18,"支付状态错误"),
    ODDER_PAID_UPDATE_ERROR(19,"订单支付状态更新失败"),
    PRODUCT_STATUS_ERROR(20,"上下架状态不正确");
    private Integer code;
    private String message;
    ExceptionResultEnum(Integer code,String message){
        this.code = code;
        this.message = message;
    }
}
