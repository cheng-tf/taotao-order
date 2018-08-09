package com.taotao.springboot.order.web.controller;

import com.alibaba.dubbo.config.annotation.Service;
import com.taotao.springboot.order.common.utils.JacksonUtils;
import com.taotao.springboot.order.domain.request.OrderInfo;
import com.taotao.springboot.order.domain.result.TaotaoResult;
import com.taotao.springboot.order.export.OrderResource;
import com.taotao.springboot.order.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>Title: OrderResourceImpl</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-06 23:47</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Service(interfaceClass = OrderResource.class)
@Controller
public class OrderResourceImpl implements OrderResource {

    private static final Logger log = LoggerFactory.getLogger(OrderResourceImpl.class);

    @Autowired
    private OrderService orderService;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        TaotaoResult res = null;
        try {
            log.info("下单 createOrder orderInfo = {}", JacksonUtils.objectToJson(orderInfo));
            res = orderService.createOrder(orderInfo);
            log.info("下单 createOrder res = {}", JacksonUtils.objectToJson(res));
        } catch (Exception e){
            log.error("### Call OrderResourceImpl.createOrder error = {}", e);
        }
        return res;
    }


}
