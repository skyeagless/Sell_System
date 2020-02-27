package com.imooc.sell.Utils;

import com.imooc.sell.DTO.OrderDTO;
import com.imooc.sell.ViewObject.ListResultVO;

import java.util.List;

public class ListResultVOUtil {
    public static ListResultVO success(List<OrderDTO> data){
        ListResultVO listResultVO = new ListResultVO();
        listResultVO.setData(data);
        listResultVO.setCode(0);
        listResultVO.setMsg("Success");
        return listResultVO;
    }
    public static ListResultVO success(){
        return success(null);
    }
    public static ListResultVO error(Integer code,String msg){
        ListResultVO listResultVO = new ListResultVO();
        listResultVO.setCode(code);
        listResultVO.setMsg(msg);
        return listResultVO;
    }
}
