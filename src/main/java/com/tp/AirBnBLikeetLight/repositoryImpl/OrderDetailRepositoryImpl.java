package com.tp.AirBnBLikeetLight.repositoryImpl;

import com.tp.AirBnBLikeetLight.entity.OrderDetail;
import com.tp.AirBnBLikeetLight.model.OrderDetailInfo;
import com.tp.AirBnBLikeetLight.repository.OrderDetailRepositoryCustom;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
public class OrderDetailRepositoryImpl implements OrderDetailRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<OrderDetailInfo> listOrderDetailInfos(String orderId) {
        String sql = "Select new " + OrderDetailInfo.class.getName() //
                + "(d.id, d.product.code, d.product.name , d.type, d.adress, d.persons,d.price,d.amount) "//
                + " from " + OrderDetail.class.getName() + " d "//
                + " where d.order.id = :orderId ";

        Query query = this.entityManager.createQuery(sql, OrderDetailInfo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }
}
