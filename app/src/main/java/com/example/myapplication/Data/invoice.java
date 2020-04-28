package com.example.myapplication.Data;

import java.io.Serializable;

public class invoice implements Serializable {

    private String name;
    private String number;
    private String PDate;
    private String EDate;
    private String period;
    private String id;
    private String categoryId;
    private String serviceProvider;
    private String  serviceProviderPhone;
    private String serviceProviderWebsite;
    private String image;
    private String web;
    public invoice() {
    }
    public invoice(String id,String name, String number,String PDate, String EDate,String period,String categoryId,String serviceProvider,String  serviceProviderPhone,String serviceProviderWebsite,String image, String web) {
        this.name = name;
        this.PDate = PDate;
        this.EDate = EDate;
        this.id=id;
        this.number=number;
        this.period=period;
        this.categoryId=categoryId;
        this.serviceProvider=serviceProvider;
        this.serviceProviderPhone=serviceProviderPhone;
        this.serviceProviderWebsite=serviceProviderWebsite;
        this.image=image;
        this.web=web;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public invoice(String name, String PDate, String EDate) {
        this.name = name;
        this.PDate = PDate;
        this.EDate = EDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPDate() {
        return PDate;
    }

    public void setPDate(String PDate) {
        this.PDate = PDate;
    }

    public String getEDate() {
        return EDate;
    }

    public void setEDate(String EDate) {
        this.EDate = EDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public String getServiceProviderPhone() {
        return serviceProviderPhone;
    }

    public void setServiceProviderPhone(String serviceProviderPhone) {
        this.serviceProviderPhone = serviceProviderPhone;
    }

    public String getServiceProviderWebsite() {
        return serviceProviderWebsite;
    }

    public void setServiceProviderWebsite(String serviceProviderWebsite) {
        this.serviceProviderWebsite = serviceProviderWebsite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
