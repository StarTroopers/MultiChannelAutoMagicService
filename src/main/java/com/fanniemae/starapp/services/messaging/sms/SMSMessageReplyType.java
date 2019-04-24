package com.fanniemae.starapp.services.messaging.sms;

public enum SMSMessageReplyType {

    RESPONSE_PENDING("P"),
    RESPONSE_COMPLETE("C"),
    RESPONSE_FAILED("E");


    private String type;

    SMSMessageReplyType(String type){
        this.type = type;
    }

    public String getTypeValue(){
        return type;
    }

    public static SMSMessageReplyType convertType(String type){

        if(RESPONSE_PENDING.type.equalsIgnoreCase(type)){
            return RESPONSE_PENDING;
        }else if(RESPONSE_COMPLETE.type.equalsIgnoreCase(type)){
            return RESPONSE_COMPLETE;
        }else{
            return RESPONSE_FAILED;
        }
    }
}
