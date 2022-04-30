package com.svo.svo.other.Utils;

public class AppException extends Exception {
    protected boolean logged;

    public AppException() {
    }

    public AppException(String msg) {
        super(msg);
    }

    public AppException(String message, Throwable cause, boolean logged) {
        super(message, cause);
        this.logged = logged;
    }

    public boolean isLogged() {
        return this.logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }
}