package com.petrov.persist;


import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameStartsWith (String prefix);
    List<Product> findProductsByCost(BigDecimal cost);
    List<Product> findProductsByCostAfter(BigDecimal cost);
    List<Product> findProductsByCostBefore(BigDecimal cost);
    List<Product> findProductsByCostBetween(BigDecimal cost,BigDecimal cost2);
}
