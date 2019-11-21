package com.tp.AirBnBLikeetLight.repositoryImpl;

import com.tp.AirBnBLikeetLight.entity.AppUser;
import com.tp.AirBnBLikeetLight.entity.Order;
import com.tp.AirBnBLikeetLight.entity.OrderDetail;
import com.tp.AirBnBLikeetLight.entity.Product;
import com.tp.AirBnBLikeetLight.model.CartInfo;
import com.tp.AirBnBLikeetLight.model.CartLineInfo;
import com.tp.AirBnBLikeetLight.model.CustomerInfo;
import com.tp.AirBnBLikeetLight.model.OrderInfo;
import com.tp.AirBnBLikeetLight.repository.AppUserRepository;
import com.tp.AirBnBLikeetLight.repository.OrderRepository;
import com.tp.AirBnBLikeetLight.repository.OrderRepositoryCustom;
import com.tp.AirBnBLikeetLight.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Transactional
public class OrderRepositoryImpl implements OrderRepositoryCustom{

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    AppUserRepository appUserRepository;


    @Override
    public Integer getMaxOrderNum() {
        try {
            String sql = "Select count (o.orderNum) from " + Order.class.getName() + " o ";
            Query query = entityManager.createQuery(sql);
            return query.getSingleResult() != null ? Integer.parseInt(query.getSingleResult().toString()) : 0;
        } catch (NoResultException e) {
            return (int) 0L;
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Order saveOrder(CartInfo cartInfo) {

        int orderNum = this.getMaxOrderNum() + 1;
        Order order = new Order();

        order.setId(UUID.randomUUID().toString());
        order.setOrderNum(orderNum);
        order.setOrderDate(new Date());
        order.setAmount(cartInfo.getAmountTotal());

        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        order.setCustomerName(customerInfo.getName());
        order.setCustomerEmail(customerInfo.getEmail());
        order.setCustomerPhone(customerInfo.getPhone());
        order.setCustomerAddress(customerInfo.getAddress());

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = appUserRepository.findByUserName(userDetails.getUsername());
        order.setUser(appUser);

        this.entityManager.persist(order);

        List<CartLineInfo> lines = cartInfo.getCartLines();

        for (CartLineInfo line : lines) {
            OrderDetail detail = new OrderDetail();
            detail.setId(UUID.randomUUID().toString());
            detail.setOrder(order);
            detail.setAmount(line.getAmount());
            detail.setPrice(line.getProductInfo().getPrice());
            detail.setPersons(line.getPersons());
            detail.setAdress(line.getProductInfo().getAdress());
            detail.setType(line.getProductInfo().getType());

            String code = line.getProductInfo().getCode();
            Product product = productRepository.findByCode(code);
            product.setReserve(true);
            detail.setProduct(product);

            this.entityManager.persist(product);
            this.entityManager.persist(detail);
        }

        // Order Number!
        cartInfo.setOrderNum(orderNum);
        // Flush
        this.entityManager.flush();

        return order;
    }

    @Override
    public OrderInfo getOrderInfo(String orderId) {
        Order order = orderRepository.findById(orderId);
        if (order == null) {
            return null;
        }
        return new OrderInfo(order.getId(), order.getOrderDate(), //
                order.getOrderNum(), order.getAmount(), order.getCustomerName(), //
                order.getCustomerAddress(), order.getCustomerEmail(), order.getCustomerPhone());
    }
}
