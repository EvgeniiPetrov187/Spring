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
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/products")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @GetMapping
    public String listPage(Model model,
                           @RequestParam("titleFilter") Optional<String> titleFilter,
                           @RequestParam("minPriceFilter") Optional<BigDecimal> minPriceFilter,
                           @RequestParam("maxPriceFilter") Optional<BigDecimal> maxPriceFilter) {
        logger.info("Product list page requested");

        List<Product> list;
        // вспомогательный список
        List<Product> startList = productRepository.findAll();

        //фильтр по названию
        if (titleFilter.isPresent()) {
            list = productRepository.findByNameStartsWith(titleFilter.get());
        } else {
            list = productRepository.findAll();
        }
        // фильтр по минимуму и максимуму
        if (minPriceFilter.isPresent() && maxPriceFilter.isPresent()) {
            startList.removeAll(productRepository.findProductsByCostBetween(minPriceFilter.get(), maxPriceFilter.get()));
            list.removeAll(startList);
            // фильтр по минимуму
        } else if (minPriceFilter.isPresent()) {
            startList.removeAll(productRepository.findProductsByCostAfter(minPriceFilter.get()));
            startList.removeAll(productRepository.findProductsByCost(minPriceFilter.get()));
            list.removeAll(startList);
            // фильтр по максимуму
        } else if (maxPriceFilter.isPresent()) {
            startList.removeAll(productRepository.findProductsByCostBefore(maxPriceFilter.get()));
            startList.removeAll(productRepository.findProductsByCost(maxPriceFilter.get()));
            list.removeAll(startList);
        }

        model.addAttribute("products", list);
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

        productRepository.save(product);
        return "redirect:/products";
    }

    // удаление продукта
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id, Model model) {
        logger.info("Deleting product");
        model.addAttribute("product", productRepository.findById(id));
        productRepository.deleteById(id);
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
