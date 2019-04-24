package com.fanniemae.starapp.controllers.request;

public class PhoneVerifyCheckBean extends PhoneVerifyBean {

    private String verificationCode;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
