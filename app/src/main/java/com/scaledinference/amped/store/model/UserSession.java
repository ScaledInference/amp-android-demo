package com.scaledinference.amped.store.model;

public class UserSession {
    private static final UserSession INSTANCE = new UserSession();

    private Session mSession;

    public Session getSession() {
        if (mSession == null) {
            mSession = new Session();
        }
        return mSession;
    }

    public void endSession() {
        mSession = null;
    }

    public static UserSession getInstance(){
        return INSTANCE;
    }
}
