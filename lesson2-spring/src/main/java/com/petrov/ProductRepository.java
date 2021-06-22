package com.petrov;

import com.petrov.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("productRepository")
public class ProductRepository {

    //1. Есть класс Product (id, название, цена). Товары хранятся в бине ProductRepository,
    // в виде List<Product>, при старте в него нужно добавить 5 любых товаров.
    private List<Product> list = new ArrayList<>();

    public ProductRepository() {
        list.add(new Product(1, "apple", 100d));
        list.add(new Product(2, "ananas", 200d));
        list.add(new Product(3, "banana", 150d));
        list.add(new Product(4, "tomato", 100d));
        list.add(new Product(5, "potato", 50d));
    }

    public List<Product> getList() {
        return list;
    }

    public Product findById(Integer id) {
        for (Product pr : list) {
            if (pr.getId().equals(id)) {
                return pr;
            }
        }
        return null;
    }
}
