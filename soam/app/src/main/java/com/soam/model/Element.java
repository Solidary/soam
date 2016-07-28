package com.soam.model;

import java.io.Serializable;

/**
 * Created by maelfosso on 7/17/16.
 */
public class Element implements Serializable {

    private String name;
    private Double price;

    public Element() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
