package com.scaledinference.amped.store.model;

import com.scaledinference.amped.store.R;

import java.util.Date;
import java.util.Map;

public class CheckoutSession {
    private Order order = new Order();
    private Date startDate = new Date();
    private Integer theme = R.style.AppThemeOrange;
    // Time given to place an order to get free shipping in minutes
    private int freeShippingTimeLimit = 30;

    public Order getOrder() {
        return order;
    }

    public int getTheme() {
        return theme;
    }

    public long getMinutesLeftForFreeShipping() {
        long minutesPassed = (System.currentTimeMillis() - startDate.getTime())/(1000 * 60);
        long minutesLeft = freeShippingTimeLimit - minutesPassed;
        return minutesLeft < 0 ? 0 : minutesLeft;
    }

    public void reportEvent(String name, Map<String, Object> properties) {

    }
}
