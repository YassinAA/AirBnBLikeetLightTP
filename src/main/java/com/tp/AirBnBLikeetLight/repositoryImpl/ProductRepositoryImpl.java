package com.tp.AirBnBLikeetLight.repositoryImpl;

import com.tp.AirBnBLikeetLight.entity.AppUser;
import com.tp.AirBnBLikeetLight.entity.Product;
import com.tp.AirBnBLikeetLight.form.ProductForm;
import com.tp.AirBnBLikeetLight.repository.AppUserRepository;
import com.tp.AirBnBLikeetLight.repository.ProductRepository;
import com.tp.AirBnBLikeetLight.repository.ProductRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.util.Date;

@Transactional
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    AppUserRepository appUserRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public Product saveProduct(ProductForm productForm) {

        String code = productForm.getCode();

        Product product = null;

        boolean isNew = false;
        if (code != null) {
            product = productRepository.findByCode(code);
        }
        if (product == null) {
            isNew = true;
            product = new Product();
            product.setCreateDate(new Date());
        }
        product.setCode(code);
        product.setName(productForm.getName());
        product.setPrice(productForm.getPrice());
        product.setType(productForm.getType());
        product.setAdress(productForm.getAdress());
        product.setPersons(productForm.getPersons());
        product.setReserve(false);

        if (productForm.getFileData() != null) {
            byte[] image = null;
            try {
                image = productForm.getFileData().getBytes();
            } catch (IOException e) {
            }
            if (image != null && image.length > 0) {
                product.setImage(image);
            }
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        AppUser appUser = appUserRepository.findByUserName(userDetails.getUsername());
        product.setUser(appUser);

        if (isNew) {
            this.entityManager.persist(product);
        }
        // If error in DB, Exceptions will be thrown out immediately
        this.entityManager.flush();
        return product;
    }

}
