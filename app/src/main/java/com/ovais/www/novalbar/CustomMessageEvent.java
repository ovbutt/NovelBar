package com.ovais.www.novalbar;

public class CustomMessageEvent {

    public String customMessage, customMessage2;


    public CustomMessageEvent(){}

    public CustomMessageEvent(String customMessage, String customMessage2) {
        this.customMessage = customMessage;
        this.customMessage2 = customMessage2;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }

    public String getCustomMessage2() {
        return customMessage2;
    }

    public void setCustomMessage2(String customMessage2) {
        this.customMessage2 = customMessage2;
    }
}
