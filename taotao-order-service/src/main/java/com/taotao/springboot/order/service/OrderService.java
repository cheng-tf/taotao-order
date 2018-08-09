package com.taotao.springboot.order.service;

import com.taotao.springboot.order.domain.request.OrderInfo;
import com.taotao.springboot.order.domain.result.TaotaoResult;

/**
 * <p>Title: OrderService</p>
 * <p>Description: 订单管理Service</p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-06 23:32</p>
 * @author ChengTengfei
 * @version 1.0
 */
public interface OrderService {

    /**
     * 下单
     */
    TaotaoResult createOrder(OrderInfo orderInfo);

}
