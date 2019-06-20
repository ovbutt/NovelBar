package com.ovais.www.novalbar;

public class InformationMessageEvent {

    public String calories, carbCalories, fatCalories, proteinCalories, rda, portionSize;


    public InformationMessageEvent(){}

    public InformationMessageEvent(String calories, String carbCalories, String fatCalories, String proteinCalories, String rda, String portionSize) {
        this.calories = calories;
        this.carbCalories = carbCalories;
        this.fatCalories = fatCalories;
        this.proteinCalories = proteinCalories;
        this.rda = rda;
        this.portionSize = portionSize;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCarbCalories() {
        return carbCalories;
    }

    public void setCarbCalories(String carbCalories) {
        this.carbCalories = carbCalories;
    }

    public String getFatCalories() {
        return fatCalories;
    }

    public void setFatCalories(String fatCalories) {
        this.fatCalories = fatCalories;
    }

    public String getProteinCalories() {
        return proteinCalories;
    }

    public void setProteinCalories(String proteinCalories) {
        this.proteinCalories = proteinCalories;
    }

    public String getRda() {
        return rda;
    }

    public void setRda(String rda) {
        this.rda = rda;
    }

    public String getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(String portionSize) {
        this.portionSize = portionSize;
    }
}
