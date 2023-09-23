package com.miniproject.inventorymanagement.adapters;

public class Employee {
    String UserEMail,cat_id;

    public Employee(){}

    public Employee(String UserEMail, String cat_id) {
        this.UserEMail = UserEMail;
        this.cat_id=cat_id;
    }

    public String getUserEMail() {
        return UserEMail;
    }

    public void setUserEMail(String userEMail) {
        UserEMail = userEMail;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
}
