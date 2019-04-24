package com.fanniemae.starapp.providers.externals.twilio.models;

public enum PhoneVerifyChannelType {

    SMS_VERIFICATION("sms"),
    CALL_VERIFICATION("call");

    private String channelType;

    PhoneVerifyChannelType(String channelType){
        this.channelType = channelType;
    }

    public String getChannelType(){
        return channelType;
    }
}
