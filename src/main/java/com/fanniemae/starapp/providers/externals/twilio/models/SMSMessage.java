package com.fanniemae.starapp.providers.externals.twilio.models;

public abstract class SMSMessage extends SMSVerifyMessage {

    private String body;
    private String from;


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }



}
