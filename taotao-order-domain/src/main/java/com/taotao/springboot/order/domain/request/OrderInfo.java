package com.taotao.springboot.order.domain.request;

import com.taotao.springboot.order.domain.pojo.TbOrder;
import com.taotao.springboot.order.domain.pojo.TbOrderItem;
import com.taotao.springboot.order.domain.pojo.TbOrderShipping;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Title: OrderInfo</p>
 * <p>Description: </p>
 * <p>Company: bupt.edu.cn</p>
 * <p>Created: 2018-05-06 23:34</p>
 * @author ChengTengfei
 * @version 1.0
 */
public class OrderInfo extends TbOrder implements Serializable {

    private static final long serialVersionUID = -4361909179412264822L;

    //订单明细列表信息
    private List<TbOrderItem> orderItems;
    // 订单物流信息
    private TbOrderShipping orderShipping;

    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }
    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }


}
