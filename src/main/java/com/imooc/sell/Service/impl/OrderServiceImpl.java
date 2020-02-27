package com.imooc.sell.Service.impl;

import com.imooc.sell.DTO.CartDTO;
import com.imooc.sell.DTO.OrderDTO;
import com.imooc.sell.Dao.OrderDetailDao;
import com.imooc.sell.Dao.OrderMasterDao;
import com.imooc.sell.Entity.OrderDetail;
import com.imooc.sell.Entity.OrderMaster;
import com.imooc.sell.Entity.ProductInfo;
import com.imooc.sell.Service.OrderService;
import com.imooc.sell.Utils.KeyUtil;
import com.imooc.sell.convert.OrderMaster2OrderDTO;
import com.imooc.sell.enums.ExceptionResultEnum;
import com.imooc.sell.enums.OrderStatusEnum;
import com.imooc.sell.enums.PayStatusEnum;
import com.imooc.sell.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDetailDao repository;

    @Autowired
    private OrderMasterDao masterRepository;

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        String orderId = KeyUtil.genUniqueKey();
        orderDTO.setOrderId(orderId);

        BigDecimal orderAmount = new BigDecimal(0);
        List<CartDTO> cartDTOList = new ArrayList<>();

        //查询商品
        for(OrderDetail orderDetail: orderDTO.getOrderDetailList()){
            ProductInfo productInfo =
                    productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ExceptionResultEnum.PRODUCT_NOT_EXIST);
            }
            //计算订单总价
            orderAmount = productInfo.getProductPrice().multiply(
                    new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库(orderdetail)
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            repository.save(orderDetail);

            //购物单序列
            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),
                    orderDetail.getProductQuantity());
            cartDTOList.add(cartDTO);
        }

        //写入数据库(orderMaster)
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);

        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        masterRepository.save(orderMaster);

        //扣库存
        productInfoService.decreaseStock(cartDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findone(String orderId) {
        OrderMaster orderMaster = masterRepository.findById(orderId).get();
        if(orderMaster==null){throw new SellException(ExceptionResultEnum.ORDER_NOT_EXIST);}

        List<OrderDetail> orderDetailList = repository.findByOrderId(orderId);
        if(orderDetailList.size()==0){throw new SellException(ExceptionResultEnum.ORDER_DETAIL_NOT_EXIST);}

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage =
                masterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.
                convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage =
                new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    public Page<OrderDTO> findAllList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = masterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.
                convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage =
                new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //先判断订单的状态
        if(!orderDTO.getOrderStatus().equals(
            OrderStatusEnum.NEW.getCode()
        )){
            log.error("【取消订单】订单状态不正确,orderId={},orderStatus={}",
                    orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【取消订单】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ExceptionResultEnum.ORDER_UPDATE_ERROR);
        }
        //返还库存
        if(orderDTO.getOrderDetailList().size()==0){
            log.error("【取消订单】订单中无商品详情,orderDTO={}",orderDTO);
            throw new SellException(ExceptionResultEnum.ORDER_NO_PRODUCT_DETAILS);
        }

        List<CartDTO> cartDTOList = new ArrayList<>();
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for(OrderDetail orderDetail : orderDetailList){
            CartDTO cartDTO = new CartDTO(
                    orderDetail.getProductId(),
                    orderDetail.getProductQuantity()
            );
            cartDTOList.add(cartDTO);
        }
        productInfoService.increaseStock(cartDTOList);

        //如果已支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(
                OrderStatusEnum.NEW.getCode()
        )){
            log.error("【完结订单】订单状态不正确,orderId={},orderStatus={}",
                    orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionResultEnum.ORDER_STATUS_ERROR);
        }
        //修改状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【完结订单】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ExceptionResultEnum.ORDER_FINISHED_ERROR);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(
                OrderStatusEnum.NEW.getCode()
        )){
            log.error("【支付订单】订单状态不正确,orderId={},orderStatus={}",
                    orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ExceptionResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[支付订单]订单支付状态不正确，orderDTO={}",orderDTO);
            throw new SellException(ExceptionResultEnum.ORDER_PAID_ERROR);
        }
        //修改支付状态
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = masterRepository.save(orderMaster);
        if(updateResult==null){
            log.error("【支付订单】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ExceptionResultEnum.ODDER_PAID_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
