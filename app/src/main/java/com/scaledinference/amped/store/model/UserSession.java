package com.scaledinference.amped.store.model;

public class UserSession {
    private static final UserSession INSTANCE = new UserSession();

    private CheckoutSession checkoutSession;

    public CheckoutSession getCheckoutSession() {
        if (checkoutSession == null) {
            checkoutSession = new CheckoutSession();
        }
        return checkoutSession;
    }

    public void endSession() {
        checkoutSession = null;
    }

    public static UserSession getInstance(){
        return INSTANCE;
    }
}
