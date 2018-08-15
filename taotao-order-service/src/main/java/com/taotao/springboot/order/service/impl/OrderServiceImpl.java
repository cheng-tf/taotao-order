package com.taotao.springboot.order.service.impl;

import com.taotao.springboot.order.common.utils.JacksonUtils;
import com.taotao.springboot.order.domain.pojo.TbOrderItem;
import com.taotao.springboot.order.domain.pojo.TbOrderShipping;
import com.taotao.springboot.order.domain.request.OrderInfo;
import com.taotao.springboot.order.domain.result.TaotaoResult;
import com.taotao.springboot.order.mapper.TbOrderItemMapper;
import com.taotao.springboot.order.mapper.TbOrderMapper;
import com.taotao.springboot.order.mapper.TbOrderShippingMapper;
import com.taotao.springboot.order.service.OrderService;
import com.taotao.springboot.order.service.cache.CacheService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * <p>Title: OrderServiceImpl</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-06 23:37</p>
 * @author ChengTengfei
 * @version 1.0
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, isolation= Isolation.DEFAULT)
public class OrderServiceImpl implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private TbOrderMapper orderMapper;

    @Autowired
    private TbOrderItemMapper orderItemMapper;

    @Autowired
    private TbOrderShippingMapper orderShippingMapper;

    @Autowired
    private CacheService cacheService;

    @Value("${ORDER_ID_GEN_KEY}")
    private String ORDER_ID_GEN_KEY;

    @Value("${ORDER_ID_BEGIN_VALUE}")
    private String ORDER_ID_BEGIN_VALUE;

    @Value("${ORDER_ITEM_ID_GEN_KEY}")
    private String ORDER_ITEM_ID_GEN_KEY;

    @Override
    public TaotaoResult createOrder(OrderInfo orderInfo) {
        // #1 基于Redis的incr计数生成订单ID
        if (!cacheService.exists(ORDER_ID_GEN_KEY)) {
            // 设置初始值
            cacheService.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
        }
        String orderId = cacheService.incr(ORDER_ID_GEN_KEY).toString();
        // #2 插入订单数据
        orderInfo.setOrderId(orderId);
        orderInfo.setPostFee("0");//免邮费
        orderInfo.setStatus(1);//1-未付款 2-已付款 3-未发货 4-已发货 5-交易成功 6-交易关闭
        orderInfo.setCreateTime(new Date());
        orderInfo.setUpdateTime(new Date());
        orderMapper.insert(orderInfo);
        log.info("订单数据 order = {}", JacksonUtils.objectToJson(orderInfo));
        // #3 插入订单明细数据
        List<TbOrderItem> orderItems = orderInfo.getOrderItems();
        for (TbOrderItem tbOrderItem : orderItems) {
            // #3.1 基于Redis的incr生成订单明细ID
            String oid = cacheService.incr(ORDER_ITEM_ID_GEN_KEY).toString();
            tbOrderItem.setId(oid);
            tbOrderItem.setOrderId(orderId);
            orderItemMapper.insert(tbOrderItem);
            log.info("订单明细数据 orderItem = {}", JacksonUtils.objectToJson(tbOrderItem));
        }
        // #4 插入订单物流数据
        TbOrderShipping orderShipping = orderInfo.getOrderShipping();
        orderShipping.setOrderId(orderId);
        orderShipping.setCreated(new Date());
        orderShipping.setUpdated(new Date());
        orderShippingMapper.insert(orderShipping);
        log.info("订单物流数据 orderShipping = {}", JacksonUtils.objectToJson(orderShipping));
        // #5 返回订单ID
        return TaotaoResult.ok(orderId);
    }

}

