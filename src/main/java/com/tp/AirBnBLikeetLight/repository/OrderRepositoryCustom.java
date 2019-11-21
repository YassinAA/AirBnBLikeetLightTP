package com.tp.AirBnBLikeetLight.repository;

import com.tp.AirBnBLikeetLight.entity.Order;
import com.tp.AirBnBLikeetLight.model.CartInfo;
import com.tp.AirBnBLikeetLight.model.OrderInfo;

public interface OrderRepositoryCustom {

    Integer getMaxOrderNum();
    Order saveOrder(CartInfo cartInfo);
    OrderInfo getOrderInfo(String orderId);
}
