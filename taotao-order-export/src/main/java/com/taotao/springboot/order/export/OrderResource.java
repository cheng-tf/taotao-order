package com.taotao.springboot.order.export;

import com.taotao.springboot.order.domain.request.OrderInfo;
import com.taotao.springboot.order.domain.result.TaotaoResult;

/**
 * <p>Title: OrderResource</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-06 23:39</p>
 * @author ChengTengfei
 * @version 1.0
 */
public interface OrderResource {

    /**
     * 下单
     */
    TaotaoResult createOrder(OrderInfo orderInfo);

}
