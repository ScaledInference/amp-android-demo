package com.scaledinference.amped.store.model;

import com.scaledinference.amped.store.R;

import java.util.Date;
import java.util.Map;

public class Session {
    private Order mOrder = new Order();
    private Date mStartDate = new Date();
    private Integer mTheme = R.style.AppThemeOrange;
    // Time given to place an order to get free shipping in minutes
    private int freeShippingTimeLimit = 30;

    public Order getOrder() {
        return mOrder;
    }

    public int getTheme() {
        return mTheme;
    }

    public long getMinutesLeftForFreeShipping() {
        long minutesPassed = (System.currentTimeMillis() - mStartDate.getTime())/(1000 * 60);
        long minutesLeft = freeShippingTimeLimit - minutesPassed;
        return minutesLeft < 0 ? 0 : minutesLeft;
    }

    public void reportEvent(String name, Map<String, Object> properties) {

    }
}
