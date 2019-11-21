package com.tp.AirBnBLikeetLight.repository;

import com.tp.AirBnBLikeetLight.entity.AppUser;
import com.tp.AirBnBLikeetLight.entity.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository  extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    public Product findByCode(String code);

    public List<Product> findAllByUser(AppUser appUser);
}
