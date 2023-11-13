package com.atguigu.spzx.order.service;

import com.atguigu.spzx.model.dto.h5.OrderInfoDto;
import com.atguigu.spzx.model.entity.order.OrderInfo;
import com.atguigu.spzx.model.vo.h5.TradeVo;
import com.github.pagehelper.PageInfo;

//业务接口
public interface OrderInfoService {
    TradeVo getTrade();

    //提交订单
    Long submitOrder(OrderInfoDto orderInfoDto);

    OrderInfo getOrderInfo(Long orderId);

    //获取订单信息1
    TradeVo buy(Long skuId);

    //获取订单分页列表
    PageInfo<OrderInfo> findUserPage(Integer page, Integer limit, Integer orderStatus);

    //远程调用：根据订单编号获取订单信息
    OrderInfo getByOrderNo(String orderNo) ;

    //远程调用：更新订单状态
    void updateOrderStatus(String orderNo, Integer orderStatus);


}