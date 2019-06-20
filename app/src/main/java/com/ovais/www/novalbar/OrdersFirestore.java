package com.ovais.www.novalbar;

import android.util.Log;

import com.google.firebase.firestore.IgnoreExtraProperties;

@IgnoreExtraProperties
public class OrdersFirestore {

    private String address;
    private String barName;
    private String city;
    private String email;
    private String id;
    private String mobile;
    private String numberOfBars;
    private String postalCode;
    private String privacy;
    private String userName;
    private String status;


    public OrdersFirestore () {}

    public OrdersFirestore (String address, String barName, String city, String email, String id, String mobile, String numberOfBars, String postalCode, String privacy, String status,String userName) {
        this.address = address;
        this.barName = barName;
        this.city = city;
        this.email = email;
        this.id = id;
        this.mobile = mobile;
        this.numberOfBars = numberOfBars;
        this.postalCode = postalCode;
        this.privacy = privacy;
        this.status = status;
        this.userName = userName;


    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
        Log.d("OrdersFirestore", "setAddress: " + address);
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
        Log.d("OrdersFirestore", "setBarName: " + barName);
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
        Log.d("OrdersFirestore", "setCity: " + city);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        Log.d("OrdersFirestore", "setEmail: " + email);
    }

    public String getId() {
        Log.d("OrdersFirestore", "getID: " + id);
        return id;
    }

    public void setId(String id) {
        this.id = id;
        Log.d("OrdersFirestore", "setId: " + id);
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        Log.d("OrdersFirestore", "setMobile: " + mobile);
    }

    public String getNumberOfBars() {
        return numberOfBars;
    }

    public void setNumberOfBars(String numberOfBars) {
        this.numberOfBars = numberOfBars;
        Log.d("OrdersFirestore", "setNumberOfBars: " + numberOfBars);
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
        Log.d("OrdersFirestore", "setpostalcode: " + postalCode);
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
        Log.d("OrdersFirestore", "setPrivacy: " + privacy);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        Log.d("OrdersFirestore", "setUserName: " + userName);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
