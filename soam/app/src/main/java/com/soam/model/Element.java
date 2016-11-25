package com.soam.model;

import com.plumillonforge.android.chipview.Chip;

import java.io.Serializable;

/**
 * Created by maelfosso on 7/17/16.
 */
public class Element implements Serializable, Chip {

    private String image;
    private String name;
    private Double price;

    private int position;

    public Element() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String getText() {

        return getName() + " -- " + String.valueOf(price);
    }
}
