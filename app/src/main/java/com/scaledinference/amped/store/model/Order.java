package com.scaledinference.amped.store.model;

import com.scaledinference.amped.store.OrderUpdateListener;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private float discount = 0.05f;
    private List<CheckoutItem> checkoutItems = new ArrayList<>();
    private List<OrderUpdateListener> listeners = new ArrayList<>();

    public void addListener(OrderUpdateListener orderUpdateListener) {
        listeners.add(orderUpdateListener);
    }

    public void removeListener(OrderUpdateListener orderUpdateListener) {
        listeners.remove(orderUpdateListener);
    }

    public void notifyOrderUpdated() {
        for (OrderUpdateListener listener: listeners) {
            listener.orderUpdated();
        }
    }

    public void add(Product product) {
        CheckoutItem checkoutItem = getCheckoutItem(product);
        if (checkoutItem == null) {
            checkoutItem = new CheckoutItem(product);
            checkoutItems.add(checkoutItem);
        }
        checkoutItem.setCount(checkoutItem.getCount() + 1);

        notifyOrderUpdated();
    }

    public int addedCount(Product product) {
        CheckoutItem checkoutItem = getCheckoutItem(product);
        if (checkoutItem != null) {
            return checkoutItem.getCount();
        }

        return 0;
    }

    public int getTotalCount() {
        int count = 0;
        for (CheckoutItem item: checkoutItems) {
            count += item.getCount();
        }
        return count;
    }

    public float getSubtotalCost() {
        float count = 0;
        for (CheckoutItem item: checkoutItems) {
            count += item.getProduct().getPrice() * item.getCount();
        }
        return count;
    }

    public float getDiscountAmount() {
        return getSubtotalCost() * discount;
    }

    public float getTotalCost() {
        return getSubtotalCost() * (1 - discount);
    }

    private CheckoutItem getCheckoutItem(Product product) {
        for (CheckoutItem item: checkoutItems) {
            if (item.getProduct().getId().equals(product.getId())) {
                return item;
            }
        }

        return null;
    }

    public List<CheckoutItem> getCheckoutItems() {
        return checkoutItems;
    }

}