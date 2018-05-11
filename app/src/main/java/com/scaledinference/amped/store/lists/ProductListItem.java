package com.scaledinference.amped.store.lists;

import com.scaledinference.amped.store.model.Product;

public class ProductListItem implements ListItem {
    private Product product;

    public ProductListItem(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return product;
    }
}
