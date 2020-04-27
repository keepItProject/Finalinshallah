package com.example.myapplication.Data;

import android.widget.ImageView;

import java.io.Serializable;

public class Userinvoice implements Serializable {
    private String id;


    private String user_id;
    private String type;
    private String name;
    private String notify;
    private String number;
    private String PDate;
    private String EDate;
    private String period;
    private String categoryId;
    private String serviceProvider;
    private String serviceProviderPhone;
    private String serviceProviderWebsite;
    private String image;




    public String toString() {
        return "Userinvoice{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", user_id='" + user_id + '\'' +
                ", type='" + type + '\'' +
                ", number='" + number + '\'' +
                ", PDate='" + PDate + '\'' +
                ", EDate='" + EDate + '\'' +
                ", period='" + period + '\'' +
                ", serviceProvider='" + serviceProvider + '\'' +
                ", serviceProviderPhone='" + serviceProviderPhone + '\'' +
                ", serviceProviderWebsite='" + serviceProviderWebsite + '\'' +
                ", notify='" + notify + '\'' +




                '}';
    }
    public String getNotify() {
        return notify;
    }

    public void setNotify(String notify) {
        this.notify = notify;
    }

    public String getId() {
        return id;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    public String getPDate() {
        return PDate;
    }

    public String getEDate() {
        return EDate;
    }

    public String getPeriod() {
        return period;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public String getServiceProviderPhone() {
        return serviceProviderPhone;
    }

    public String getServiceProviderWebsite() {
        return serviceProviderWebsite;
    }

    public String getImage() {
        return image;

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPDate(String PDate) {
        this.PDate = PDate;
    }

    public void setEDate(String EDate) {
        this.EDate = EDate;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public void setServiceProviderPhone(String serviceProviderPhone) {
        this.serviceProviderPhone = serviceProviderPhone;
    }

    public void setServiceProviderWebsite(String serviceProviderWebsite) {
        this.serviceProviderWebsite = serviceProviderWebsite;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
