package com.soam.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maelfosso on 8/29/16.
 */
public class Place implements Serializable {

    private String name;
    private Location location;
    private Contact contact;
    private List<Category> categories = new ArrayList<Category>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getJoinCategories() {
        if (categories.size() == 0) {
            return "";
        }

        String s = categories.get(0).getName();
        for (int i = 1; i < categories.size(); i++) {
            s += " - " + categories.get(i).getName();
        }

        return s;
    }
}
