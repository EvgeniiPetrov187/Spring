package com.petrov;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


import javax.persistence.EntityManagerFactory;

@Configuration
@ComponentScan("com.petrov")
public class AppConfig {

    @Bean // бин для EntityManagerFactory
    public EntityManagerFactory serviceFactory(){ return new ServiceFactory().getEntityManagerFactory(); }

    @Bean // бин для покупателя
    public CostumerDao costumerDao(EntityManagerFactory emf) {
        return new CostumerDao(emf);
    }

    @Bean // бин для продукта
    public ProductDao productDao(EntityManagerFactory emf) {
       return new ProductDao(emf);
    }
}