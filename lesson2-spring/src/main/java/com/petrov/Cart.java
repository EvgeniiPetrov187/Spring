package com.petrov;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("cart")
public class Cart {

    //2. ProductRepository позволяет получить весь список или один товар по id.
    // Создаем бин Cart, в который можно добавлять и удалять товары по id.
    List<Product> cart = new ArrayList<>();

    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    @Autowired
    private final ProductRepository productRepository = context.getBean("productRepository", ProductRepository.class);

    public void put(Integer id) {
        cart.add(productRepository.findById(id));
    }

    public void remove(Integer id) {
        cart.remove(productRepository.findById(id));
    }

    public void showList() {
        for (Product pr : this.cart) {
            System.out.println(pr.getName() + " costs " + pr.getCost() + " p");
        }
    }
}
