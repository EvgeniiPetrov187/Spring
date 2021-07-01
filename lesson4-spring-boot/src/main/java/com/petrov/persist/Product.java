package com.petrov.persist;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;

// 2. Создать класс Товар (Product), с полями id, title, cost;
public class Product {

    private Long id;

    @NotBlank
    private String name;

    @Max(value = 1000)
    private Double cost;

    public Product() {
    }

    public Product(String name, Double cost) {
        this.name = name;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
