package com.petrov;

import com.petrov.persist.Product;
import com.petrov.persist.ProductRepository;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootstrapListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext sc =  sce.getServletContext();
        ProductRepository productRepository = new ProductRepository();

        // добавление продуктов
        productRepository.save(new Product(1L, "Apple", 300d));
        productRepository.save(new Product(2L, "Banana", 400d));
        productRepository.save(new Product(3L, "Cheese", 500d));
        productRepository.save(new Product(4L, "Meat", 1000d));
        productRepository.save(new Product(5L, "Pineapple", 500d));
        productRepository.save(new Product(6L, "Onion", 100d));
        productRepository.save(new Product(7L, "Bread" , 60d));
        productRepository.save(new Product(8L, "Yoghurt", 500d));
        productRepository.save(new Product(9L, "Tee", 1000d));
        productRepository.save(new Product(10L, "Water", 50d));

        sc.setAttribute("productRepository", productRepository);
    }
}
