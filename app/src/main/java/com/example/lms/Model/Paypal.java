package com.example.lms.Model;

public class Paypal {
    String active;
    String mode;
    String sandbox;
    String sandbox_client_id;
    String sandbox_secret_key;
    String production_client_id;
    String production_secret_key;

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getSandbox() {
        return sandbox;
    }

    public void setSandbox(String sandbox) {
        this.sandbox = sandbox;
    }

    public String getSandbox_client_id() {
        return sandbox_client_id;
    }

    public void setSandbox_client_id(String sandbox_client_id) {
        this.sandbox_client_id = sandbox_client_id;
    }

    public String getSandbox_secret_key() {
        return sandbox_secret_key;
    }

    public void setSandbox_secret_key(String sandbox_secret_key) {
        this.sandbox_secret_key = sandbox_secret_key;
    }

    public String getProduction_client_id() {
        return production_client_id;
    }

    public void setProduction_client_id(String production_client_id) {
        this.production_client_id = production_client_id;
    }

    public String getProduction_secret_key() {
        return production_secret_key;
    }

    public void setProduction_secret_key(String production_secret_key) {
        this.production_secret_key = production_secret_key;
    }
}
