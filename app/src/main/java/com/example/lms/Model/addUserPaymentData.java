package com.example.lms.Model;

public class addUserPaymentData {
    String paypalClientId  = "";
    String paypalSecretId = "";
    String StripePublicKey = "";
    String stripeSecretKey = "";

    public addUserPaymentData() {
    }

    public String getPaypalClientId() {
        return paypalClientId;
    }

    public void setPaypalClientId(String paypalClientId) {
        this.paypalClientId = paypalClientId;
    }

    public String getPaypalSecretId() {
        return paypalSecretId;
    }

    public void setPaypalSecretId(String paypalSecretId) {
        this.paypalSecretId = paypalSecretId;
    }

    public String getStripePublicKey() {
        return StripePublicKey;
    }

    public void setStripePublicKey(String stripePublicKey) {
        StripePublicKey = stripePublicKey;
    }

    public String getStripeSecretKey() {
        return stripeSecretKey;
    }

    public void setStripeSecretKey(String stripeSecretKey) {
        this.stripeSecretKey = stripeSecretKey;
    }
}
