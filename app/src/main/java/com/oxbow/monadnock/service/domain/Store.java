package com.oxbow.monadnock.service.domain;


public class Store {

    private long id;

    private String number;

    private String address;

    private String city;

    private String state;

    private String zip;

    private String phone;

    public Store() {
    }

    public long getId() {
        return this.id;
    }

    // for tests ONLY
    public void setId(long id) {
        this.id = id;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getFullAddress() {
        return String.format("%s, %s, %s.", address, city, state);
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Store {" +
                "id=" + id +
                ", number=" + number +
                ", city=" + city +
                ", state=" + state +
                ", phone='" + phone + '\'' +
                '}';
    }
}
