package com.ovais.www.novalbar.shareprefer;

import android.content.Context;
import android.content.SharedPreferences;

public class  SharePrefer {

    private Context ctx;
    private SharedPreferences sp;
    private String name;
    private String email;
    private String imageURL;
    private String uid;

    public String getEmail() {
        email = sp.getString("email", "Set Email");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sp.edit().putString("email", email).apply();
    }

    public String getImageURL() {
        imageURL = sp.getString("imageURL", "No Image");
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
        sp.edit().putString("imageURL", imageURL).apply();
    }

    public SharePrefer(Context ctx) {
        this.ctx = ctx;
        sp = ctx.getSharedPreferences("UserCredientials", Context.MODE_PRIVATE);
    }

    public String getName() {
        name = sp.getString("name", "User Name");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sp.edit().putString("name", name).apply();
    }

    public String getUid() {
        uid = sp.getString("uid", "null");
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        sp.edit().putString("uid",uid).apply();
    }
}
