package com.tp.AirBnBLikeetLight.repository;

import com.tp.AirBnBLikeetLight.entity.AppUser;
import com.tp.AirBnBLikeetLight.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    public Order findById(String orderId);

    public List<Order> findAllByUser(AppUser appUser);
}
