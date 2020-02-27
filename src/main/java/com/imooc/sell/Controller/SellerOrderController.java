package com.imooc.sell.Controller;

import com.imooc.sell.DTO.OrderDTO;
import com.imooc.sell.Service.OrderService;
import com.imooc.sell.enums.ExceptionResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderService;

    //订单列表(page从第一页开始,size一页有多少条数据)
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value ="size",defaultValue = "5") Integer size,
                             Map<String,Object> map)
    {
        PageRequest request = PageRequest.of(page-1,size);
        Page<OrderDTO> orderDTOPage = orderService.findAllList(request);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map);
    }

    //取消订单
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId") String orderId,
                                Map<String,Object> map){
        try{
            OrderDTO orderDTO = orderService.findone(orderId);
            orderService.cancel(orderDTO);
        }catch(Exception e){
            log.error("【卖家取消订单】查询不到订单");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg", ExceptionResultEnum.SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    //订单详情
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){

        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findone(orderId);
        }catch(Exception e){
            log.error("【卖家查询订单详情】发生异常");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail",map);
    }


    //完结订单
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId") String orderId,
                                 Map<String,Object> map){
        try{
            OrderDTO orderDTO = orderService.findone(orderId);
            orderService.finish(orderDTO);
        }catch(Exception e){
            log.error("【卖家完结订单】查询不到订单");
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg", ExceptionResultEnum.SUCCESS.getMessage());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }


}
