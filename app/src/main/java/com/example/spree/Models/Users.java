package com.example.spree.Models;

public class Users {

    private String name, phone, password, image, adddress;

    public Users(){

    }

    public Users(String name, String phone, String password, String image, String adddress) {
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.image = image;
        this.adddress = adddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdddress() {
        return adddress;
    }

    public void setAdddress(String adddress) {
        this.adddress = adddress;
    }
}
