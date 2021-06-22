package com.petrov;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("com.petrov")
public class AppConfig {

    //4. При каждом запросе корзины из контекста, должна создаваться новая корзина.
    // Использовать scope prototype
    @Bean
    @Scope("prototype")
    public Cart cart() {
        return new Cart();
    }

    @Bean
    public ProductRepository productRepository() {
        return new ProductRepository();
    }
}
