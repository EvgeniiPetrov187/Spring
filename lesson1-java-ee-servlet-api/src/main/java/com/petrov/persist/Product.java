package com.petrov.persist;

public class Product {

    private Long id;

    private String name;

    //стоимость продуктов
    private Double coast;


    public Product(Long id, String name, Double coast) {
        this.id = id;
        this.name = name;
        this.coast = coast;
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

    public Double getCoast() {
        return coast;
    }

    public void setCoast(Double coast) {
        this.coast = coast;
    }
}
