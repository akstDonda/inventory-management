package com.miniproject.inventorymanagement.adapters;

public class employeelist {
    String UserEMail;

    public employeelist(){}

    public employeelist(String UserEMail) {
        this.UserEMail = UserEMail;
    }

    public String getUserEMail() {
        return UserEMail;
    }

    public void setUserEMail(String userEMail) {
        UserEMail = userEMail;
    }
}
