package com.tp.AirBnBLikeetLight.repository;

import com.tp.AirBnBLikeetLight.entity.Product;
import com.tp.AirBnBLikeetLight.form.ProductForm;

public interface ProductRepositoryCustom {

    Product saveProduct(ProductForm productForm);
}
