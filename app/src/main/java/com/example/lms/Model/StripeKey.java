package com.example.lms.Model;

public class StripeKey {
    private String active;
    private String testmode;
    private String public_key;
    private String secret_key;
    private String public_live_key;
    private String secret_live_key;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getTestmode() {
        return testmode;
    }

    public void setTestmode(String testmode) {
        this.testmode = testmode;
    }

    public String getPublic_key() {
        return public_key;
    }

    public void setPublic_key(String public_key) {
        this.public_key = public_key;
    }

    public String getSecret_key() {
        return secret_key;
    }

    public void setSecret_key(String secret_key) {
        this.secret_key = secret_key;
    }

    public String getPublic_live_key() {
        return public_live_key;
    }

    public void setPublic_live_key(String public_live_key) {
        this.public_live_key = public_live_key;
    }

    public String getSecret_live_key() {
        return secret_live_key;
    }

    public void setSecret_live_key(String secret_live_key) {
        this.secret_live_key = secret_live_key;
    }
}
