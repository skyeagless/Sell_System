package com.imooc.sell.Utils;

import com.imooc.sell.ViewObject.ProductVO;
import com.imooc.sell.ViewObject.ResultVO;

import java.util.List;

public class ResultVOUtil {
    public static ResultVO success(List<ProductVO> productVOS){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(productVOS);
        resultVO.setCode(0);
        resultVO.setMsg("Success");
        return resultVO;
    }
    public static ResultVO success(){
        return success(null);
    }
    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(code);
        resultVO.setMsg(msg);
        return resultVO;
    }

}
