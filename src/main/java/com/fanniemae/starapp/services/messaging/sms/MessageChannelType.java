package com.fanniemae.starapp.services.messaging.sms;

public enum MessageChannelType {

    WHATSAPP("WHATSAPP"),
    VOICE("VOICE"),
    SMS("SMS");

    private String type;

    MessageChannelType(String type){
        this.type = type;
    }

    public String getTypeValue(){
        return type;
    }

    public static MessageChannelType convertType(String type){

        if(WHATSAPP.type.equalsIgnoreCase(type)){
            return WHATSAPP;
        }else if(VOICE.type.equalsIgnoreCase(type)){
            return VOICE;
        }else{
            return SMS;
        }
    }
}
