package com.petrov;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class Main {
    public static void main(String[] args) {

        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        // 3. Написать консольное приложение, позволяющее управлять корзиной.
        Cart cart  = context.getBean("cart", Cart.class);
        cart.put(1);
        cart.put(2);
        cart.put(5);
        cart.showList();

        System.out.println();


        Cart cart2 = context.getBean("cart", Cart.class);
        cart2.put(3);
        cart2.put(4);
        cart2.showList();

        System.out.println();

        cart2.remove(4);
        cart2.put(5);
        cart2.showList();
    }
}
