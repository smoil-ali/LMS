package com.example.lms.Model;

import java.io.Serializable;
import java.util.List;

public class UserData_EnrollmentHistoryModel implements Serializable {
    UserData userData;
    List<EnrollHistoryUserData> list;

    public UserData getUserData() {
        return userData;
    }

    public void setUserData(UserData userData) {
        this.userData = userData;
    }

    public List<EnrollHistoryUserData> getList() {
        return list;
    }

    public void setList(List<EnrollHistoryUserData> list) {
        this.list = list;
    }
}
