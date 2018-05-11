package com.scaledinference.amped.store.model;

public class UserSession {
    private static final UserSession INSTANCE = new UserSession();

    private Session session;

    public Session getSession() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    public void endSession() {
        session = null;
    }

    public static UserSession getInstance(){
        return INSTANCE;
    }
}
