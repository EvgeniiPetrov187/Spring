package com.petrov.controller;

import com.petrov.persist.Product;
import com.petrov.service.ProductService;
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

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping
    public String listPage(Model model,
                           ProductListParam productListParam) {
        logger.info("Product list page requested");
        model.addAttribute("products", productService.findWithFilter(productListParam));
        return "products";
    }

    @GetMapping("/new")
    public String newProductForm(Model model) {
        logger.info("New product page requested");
        model.addAttribute("product", new Product());
        return "new_product_form";
    }

    @GetMapping("/{id}")
    public String editProduct(@PathVariable("id") Long id, Model model) {
        logger.info("Edit product page requested");

        model.addAttribute("product", productService.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found")));
        return "new_product_form";
    }

    @PostMapping
    public String update(@Valid Product product, BindingResult result) {
        logger.info("Saving product");

        if (result.hasErrors()) {
            return "new_product_form";
        }

        productService.save(product);
        return "redirect:/products";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting product");
        model.addAttribute("product", productService.findById(id));
        productService.deleteById(id);
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
