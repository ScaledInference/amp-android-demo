package com.scaledinference.amped.store;

import com.scaledinference.amped.store.model.CheckoutSession;

public class AmpedStoreApplication extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CheckoutSession.initAmp(this);
    }
}
