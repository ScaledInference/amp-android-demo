package com.scaledinference.amped.store.model;

import android.content.Context;

import com.scaledinference.amped.store.R;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import amp.core.Amp;

public class CheckoutSession {
    private static Amp amp;
    private static String AMP_PROJECT_KEY = "338b3c1e7faf483a";
    private static String COLOR_KEY = "color";
    private static String FREE_SHIPPING_TIME_KEY = "free_shipping_time";
    private static String AMPED_CART_KEY = "AmpedCart";
    private static String CUSTOMER_CONTEXT_KEY = "CustomerContext";
    private static String GREEN_COLOR_KEY = "green";
    private static String ORANGE_COLOR_KEY = "orange";
    private static String RED_COLOR_KEY = "red";

    private Order order = new Order();
    private Date startDate = new Date();
    private Integer theme;
    // Time given to place an order to get free shipping in minutes
    private int freeShippingTimeLimit;

    CheckoutSession() {
        amp.session("some_user_id");

        // Context before decide
        Customer customer = new Customer();
        amp.observe(CUSTOMER_CONTEXT_KEY, customer.info());

        Map<String, List<Object>> candidates = new HashMap<>();
        candidates.put(COLOR_KEY, Arrays.<Object>asList(GREEN_COLOR_KEY, ORANGE_COLOR_KEY, RED_COLOR_KEY));
        candidates.put(FREE_SHIPPING_TIME_KEY, Arrays.<Object>asList(0, 30, 60, 120));

        Map<String, Object> decision = amp.decide(AMPED_CART_KEY, candidates);
        theme = getAppThemeByKey((String) decision.get(COLOR_KEY));
        freeShippingTimeLimit = (Integer) decision.get(FREE_SHIPPING_TIME_KEY);
    }

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
        amp.observe(name, properties);
    }

    public static void initAmp(Context context) {
        amp = new Amp(context, AMP_PROJECT_KEY);
    }

    private int getAppThemeByKey(String colorKey) {
        if (GREEN_COLOR_KEY.equals(colorKey)) {
            return R.style.AppThemeGreen;
        }

        if (ORANGE_COLOR_KEY.equals(colorKey)) {
            return R.style.AppThemeOrange;
        }

        return  R.style.AppThemeRed;
    }
}
