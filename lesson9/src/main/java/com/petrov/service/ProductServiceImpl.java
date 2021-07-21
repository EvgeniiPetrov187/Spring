package com.petrov.service;

import com.petrov.controller.ProductListParam;
import com.petrov.persist.Product;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.ProductSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Page<Product> findWithFilter(ProductListParam productListParam) {
        Specification<Product> spec = Specification.where(null);
        if (productListParam.getTitleFilter() != null && !productListParam.getTitleFilter().isEmpty()) {
            spec = spec.and(ProductSpecifications.productPrefix(productListParam.getTitleFilter()));
        }
        if (productListParam.getMinPriceFilter() != null) {
            spec = spec.and(ProductSpecifications.minPrice(productListParam.getMinPriceFilter()));
        }
        if (productListParam.getMaxPriceFilter() != null) {
            spec = spec.and(ProductSpecifications.maxPrice(productListParam.getMaxPriceFilter()));
        }


        if (productListParam.getSort() != null && !productListParam.getSort().isEmpty()) {
            return productRepository.findAll(spec,
                    PageRequest.of(
                            Optional.ofNullable(productListParam.getPage()).orElse(1) - 1,
                            Optional.ofNullable(productListParam.getSize()).orElse(3),
                            Optional.of(Optional.ofNullable(productListParam.getDirection()).orElse("asc").equalsIgnoreCase("desc") ?
                                    Sort.by(productListParam.getSort()).descending() :
                                    Sort.by(productListParam.getSort()).ascending()).get()));
        } else {
            return productRepository.findAll(spec,
                    PageRequest.of(
                            Optional.ofNullable(productListParam.getPage()).orElse(1) - 1,
                            Optional.ofNullable(productListParam.getSize()).orElse(3)));
        }
    }

    @Override
    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public void save(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
