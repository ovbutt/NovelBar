package com.ovais.www.novalbar;

import android.util.Log;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.Map;

@IgnoreExtraProperties
public class SearchFirestore {

    private String uid;
    private String barName;
    private String privacy;
    //public Map<String, Object> recipe;

    public SearchFirestore () {}

    public SearchFirestore(String uid, String barName, String privacy) {
        this.uid = uid;
        this.barName = barName;
        this.privacy = privacy;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
        Log.d("SearchFirestore", "setBarUID: " + uid);

    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
        Log.d("SearchFirestore", "setBarName: " + barName);
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
        Log.d("SearchFirestore", "setPrivacy: " + privacy);
    }
}
