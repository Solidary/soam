package com.soam.model;

import java.io.Serializable;

/**
 * Created by maelfosso on 10/23/16.
 */
public class Category implements Serializable {

    String photo;
    String name;
    String description;

    public Category() {
    }

    public Category(String photo, String name, String description) {
        this.photo = photo;
        this.name = name;
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
