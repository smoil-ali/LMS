package com.example.lms.Model;

public class addContainer {

    public static AddBasicUserModel model = new AddBasicUserModel();
    public static AddUserLogindata addUserLogindata = new AddUserLogindata();
    public static addUserSocialData addUserSocialData = new addUserSocialData();
    public static addUserPaymentData addUserPaymentData = new addUserPaymentData();

    public static com.example.lms.Model.addUserPaymentData getAddUserPaymentData() {
        return addUserPaymentData;
    }

    public static void setAddUserPaymentData(com.example.lms.Model.addUserPaymentData addUserPaymentData) {
        addContainer.addUserPaymentData = addUserPaymentData;
    }

    public static com.example.lms.Model.addUserSocialData getAddUserSocialData() {
        return addUserSocialData;
    }

    public static void setAddUserSocialData(com.example.lms.Model.addUserSocialData addUserSocialData) {
        addContainer.addUserSocialData = addUserSocialData;
    }

    public static AddUserLogindata getAddUserLogindata() {
        return addUserLogindata;
    }

    public static void setAddUserLogindata(AddUserLogindata addUserLogindata) {
        addContainer.addUserLogindata = addUserLogindata;
    }

    public static AddBasicUserModel getModel() {
        return model;
    }

    public static void setModel(AddBasicUserModel model) {
        addContainer.model = model;
    }
}
