package com.petrov.controller;

import com.petrov.persist.Product;
import com.petrov.persist.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;

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
        return "new_product_form";
    }

    //5. * Создать страницу редактирования товара
    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Edit product page requested");

        model.addAttribute("product", productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        return "new_product_form";
    }

    @PostMapping
    public String update(@Valid Product product, BindingResult result) {
        logger.info("Saving product");

        if (result.hasErrors()) {
            return "new_product_form";
        }
        /*
        if (product.getCost() > 1000){
            result.rejectValue("cost", "","Error message");
            return "new_product_form";
        }
        */

        productRepository.save(product);
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

    @ExceptionHandler
    public ModelAndView notFoundExceptionHandler(NotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("not_found");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.setStatus(HttpStatus.NOT_FOUND);
        return modelAndView;
    }

}
