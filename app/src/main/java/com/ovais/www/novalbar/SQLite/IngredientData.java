package com.ovais.www.novalbar.SQLite;

import android.content.Context;

public class IngredientData {

    private int id;
    private String bitternessFlag;
    private int calories;
    private int carbCalorie;
    private String category;
    private int fatCalorie;
    private String name;
    private int portionFlag;
    private int portionSize;
    private double proteinCalorie;
    private int quantity;
    private String rda;
    private int servingSize;
    private int unhealthyFlag;

    public IngredientData (){
    }

    public IngredientData(String bitternessFlag, int calories, int carbCalorie, String category, int fatCalorie, String name, int portionFlag, int portionSize, double proteinCalorie, int quantity, String rda, int servingSize, int unhealthyFlag) {
        this.bitternessFlag = bitternessFlag;
        this.calories = calories;
        this.carbCalorie = carbCalorie;
        this.category = category;
        this.fatCalorie = fatCalorie;
        this.name = name;
        this.portionFlag = portionFlag;
        this.portionSize = portionSize;
        this.proteinCalorie = proteinCalorie;
        this.quantity = quantity;
        this.rda = rda;
        this.servingSize = servingSize;
        this.unhealthyFlag = unhealthyFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBitternessFlag() {
        return bitternessFlag;
    }

    public void setBitternessFlag(String bitternessFlag) {
        this.bitternessFlag = bitternessFlag;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getCarbCalorie() {
        return carbCalorie;
    }

    public void setCarbCalorie(int carbCalorie) {
        this.carbCalorie = carbCalorie;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFatCalorie() {
        return fatCalorie;
    }

    public void setFatCalorie(int fatCalorie) {
        this.fatCalorie = fatCalorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPortionFlag() {
        return portionFlag;
    }

    public void setPortionFlag(int portionFlag) {
        this.portionFlag = portionFlag;
    }

    public int getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(int portionSize) {
        this.portionSize = portionSize;
    }

    public double getProteinCalorie() {
        return proteinCalorie;
    }

    public void setProteinCalorie(double proteinCalorie) {
        this.proteinCalorie = proteinCalorie;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getRda() {
        return rda;
    }

    public void setRda(String rda) {
        this.rda = rda;
    }

    public int getServingSize() {
        return servingSize;
    }

    public void setServingSize(int servingSize) {
        this.servingSize = servingSize;
    }

    public int getUnhealthyFlag() {
        return unhealthyFlag;
    }

    public void setUnhealthyFlag(int unhealthyFlag) {
        this.unhealthyFlag = unhealthyFlag;
    }
}