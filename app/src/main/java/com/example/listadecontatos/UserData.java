package com.example.listadecontatos;

import android.net.Uri;

import com.example.listadecontatos.firebase.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class UserData implements Serializable {

    private String username, email, name, site, password, number, id, img;

    public UserData(String username, String email, String name, String site, String password, String number) {
        this.username = username;
        this.email = email;
        this.name = name;
        this.site = site;
        this.password = password;
        this.number = number;
    }

    public UserData() {
    }

    public void save(){
        DatabaseReference reference = FirebaseConfig.getFirebase();
        reference.child("users").child(getId()).setValue(this);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Exclude
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
