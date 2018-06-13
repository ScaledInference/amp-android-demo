package com.scaledinference.amped.store.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Customer {
    private static final String COUNTRY_KEY = "country";
    private static final String TYPE_KEY = "type";
    private static final String CONNECTION_KEY = "connection";
    private static final String NEW_CUSTOMER = "new_customer";
    private static final String RETURNING_CUSTOMER = "returning_customer";
    private static final String SLOW_CONNECTION = "slow";
    private static final String FAST_CONNECTION = "fast";

    private boolean hasSlowConnection;
    private boolean isNew;
    private String country;

    Customer() {
        Random random = new Random();
        hasSlowConnection = random.nextBoolean();
        isNew = random.nextBoolean();

        int index = random.nextInt(COUNTRIES.length);
        country = COUNTRIES[index];
    }


    Map<String, Object> info() {
        Map<String, Object> info = new HashMap<>();
        info.put(COUNTRY_KEY, country);
        info.put(TYPE_KEY, isNew ? NEW_CUSTOMER : RETURNING_CUSTOMER);
        info.put(CONNECTION_KEY, hasSlowConnection ? SLOW_CONNECTION : FAST_CONNECTION);
        return info;
    }

    private static final String[] COUNTRIES = new String[]{ "United States", "United Kingdom",
            "Russia", "Ukraine", "Israel", "India", "China", "Turkey"};
}
