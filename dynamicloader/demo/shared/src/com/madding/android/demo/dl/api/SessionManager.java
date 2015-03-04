package com.madding.android.demo.dl.api;

public class SessionManager {

    private static Session session;

    public static Session getSession() {
        return session;
    }

    public static void setSession(Session session) {
        SessionManager.session = session;
    }
}
