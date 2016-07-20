package com.soam.model;

import java.util.List;

/**
 * Created by maelfosso on 7/17/16.
 */
public class Need {

    private String description;
    private String place;
    private List<Element> elements;

    public Need() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public List<Element> getElements() {
        return elements;
    }

    public void setElements(List<Element> elements) {
        this.elements = elements;
    }

    public Double getAmount() {
        double p = 0;
        for (Element e: elements) {
            if (e.getPrice() != null) {
                p += e.getPrice();
            }
        }

        return p;
    }

}
