package com.petrov.controller;

import com.petrov.persist.Product;
import com.petrov.persist.ProductRepository;
import com.petrov.persist.ProductSpecifications;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
                           @RequestParam("maxPriceFilter") Optional<BigDecimal> maxPriceFilter,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           @RequestParam("sort") Optional<String> sort) {
        logger.info("Product list page requested");


        Specification<Product> spec = Specification.where(null);
        if (titleFilter.isPresent() && !titleFilter.get().isEmpty())
            spec = spec.and(ProductSpecifications.productPrefix(titleFilter.get()));
        if (minPriceFilter.isPresent())
            spec = spec.and(ProductSpecifications.minPrice(minPriceFilter.get()));
        if (maxPriceFilter.isPresent())
            spec = spec.and(ProductSpecifications.maxPrice(maxPriceFilter.get()));

        if (sort.isPresent()) { // проверка на наличие сортировки
            model.addAttribute("products", productRepository.findAll(spec,
                    PageRequest.of(page.orElse(1) - 1, size.orElse(3), Sort.by(sort.get()).ascending())));
        } else {
            model.addAttribute("products", productRepository.findAll(spec,
                    PageRequest.of(page.orElse(1) - 1, size.orElse(3))));
        }
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
