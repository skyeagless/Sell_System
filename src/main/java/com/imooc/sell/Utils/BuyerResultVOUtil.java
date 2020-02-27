package com.imooc.sell.Utils;

import com.imooc.sell.ViewObject.BuyerResultVO;

import java.util.List;
import java.util.Map;

public class BuyerResultVOUtil {
    public static BuyerResultVO success(List<Map<String,String>> data){
        BuyerResultVO buyerResultVO = new BuyerResultVO();
        buyerResultVO.setData(data);
        buyerResultVO.setCode(0);
        buyerResultVO.setMsg("Success");
        return buyerResultVO;
    }
    public static BuyerResultVO success(){
        return success(null);
    }
    public static BuyerResultVO error(Integer code,String msg){
        BuyerResultVO buyerResultVO = new BuyerResultVO();
        buyerResultVO.setCode(code);
        buyerResultVO.setMsg(msg);
        return buyerResultVO;
    }
}
