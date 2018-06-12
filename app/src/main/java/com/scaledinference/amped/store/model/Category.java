package com.scaledinference.amped.store.model;

import java.util.List;

public class Category {
    private String title;
    private List<Product> products;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Product> getProducts() {
        return products;
    }
}
