package com.fanniemae.starapp.providers.externals.twilio;

import org.springframework.beans.factory.annotation.Value;

public class TwilioConfigCredential {


    @Value("${starapp.twilio.account.sid}")
    private String accountSid;

    @Value("${starapp.twilio.account.auth.token}")
    private String accountAuthToken;

    @Value("${starapp.twilio.accoubt.verify.sid}")
    private String accountVerifyServiceSid;


    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public void setAccountAuthToken(String accountAuthToken) {
        this.accountAuthToken = accountAuthToken;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public String getAccountAuthToken() {
        return accountAuthToken;
    }

    public String getAccountVerifyServiceSid() {
        return accountVerifyServiceSid;
    }

    public void setAccountVerifyServiceSid(String accountVerifyServiceSid) {
        this.accountVerifyServiceSid = accountVerifyServiceSid;
    }
}
