package com.petrov.service;

import com.petrov.controller.ProductListParam;
import com.petrov.persist.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Page<Product> findWithFilter(ProductListParam productListParam);

    Optional<Product> findById(Long id);

    void save(Product product);

    void deleteById(Long id);

    List<Product> findAll();

}
