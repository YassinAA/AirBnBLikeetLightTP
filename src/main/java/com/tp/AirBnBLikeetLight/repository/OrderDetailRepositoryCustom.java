package com.tp.AirBnBLikeetLight.repository;

import com.tp.AirBnBLikeetLight.model.OrderDetailInfo;

import java.util.List;

public interface OrderDetailRepositoryCustom {

    List<OrderDetailInfo> listOrderDetailInfos(String orderId);

}
