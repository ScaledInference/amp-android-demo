package com.scaledinference.amped.store.model;

public class CheckoutItem {
    private Product product;
    private int count;

    CheckoutItem(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
