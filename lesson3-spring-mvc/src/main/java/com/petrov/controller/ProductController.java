package com.petrov.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.petrov.persist.Product;
import com.petrov.persist.ProductRepository;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;
    private int command; //переменная для метода update

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // 4. Сделать страницу, на которой отображаются все товары из репозитория.
    @GetMapping
    public String listPage(Model model) {
        logger.info("Product list page requested");
        model.addAttribute("products", productRepository.findAll());
        return "products";
    }

    // 3. Сделать форму для добавления товара в репозиторий и логику работы этой формы;
    @GetMapping("/new")
    public String newProductForm(Model model) {
        logger.info("New product page requested");
        model.addAttribute("product", new Product());
        command = 1;
        return "new_product_form";
    }

    //5. * Создать страницу редактирования товара
    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        model.addAttribute("product", productRepository.findById(id));
        command = 2;
        return "new_product_form";
    }

    @PostMapping
    public String update(Product product) {
        logger.info("Saving product");
        if (command == 1)
            productRepository.insert(product);
        if (command == 2)
            productRepository.update(product);
        return "redirect:/products";
    }

    // удаление продукта
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting product");
        model.addAttribute("product", productRepository.findById(id));
        productRepository.delete(id);
        return "redirect:/products";
    }
}
