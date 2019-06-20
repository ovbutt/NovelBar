package com.ovais.www.novalbar;

import android.util.Log;

import com.google.firebase.firestore.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class FormulaFirestore {

    private Map<String, Object> formulaMap = new HashMap<>();
    private String barName;
    private String privacy;


    public FormulaFirestore() {}

    public FormulaFirestore(Map<String, Object> formulaMap, String barName, String privacy) {
        this.formulaMap = formulaMap;
        this.barName = barName;
        this.privacy = privacy;
    }

    public Map<String, Object> getFormulaMap() {
        return formulaMap;
    }

    public void setFormulaMap(Map<String, Object> formulaMap) {
        this.formulaMap = formulaMap;
        Log.d("Formula Firestore", "setFormulaMap: " + formulaMap.toString());
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
