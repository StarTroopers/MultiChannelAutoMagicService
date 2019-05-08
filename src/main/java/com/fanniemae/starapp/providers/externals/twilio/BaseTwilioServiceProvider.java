package com.fanniemae.starapp.providers.externals.twilio;

import com.fanniemae.starapp.providers.externals.twilio.models.PhoneVerifyResponse;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

public abstract class BaseTwilioServiceProvider implements TwilioServiceProvider {


    protected PhoneVerifyResponse createVerificationResponse(Verification verification){
        final PhoneVerifyResponse resp = new PhoneVerifyResponse();
        resp.setAccountSid(verification.getAccountSid());
        resp.setServiceSid(verification.getServiceSid());
        resp.setToNumber(verification.getTo().toString());
        resp.setStatus(verification.getStatus());
        return resp;
    }


    protected PhoneVerifyResponse createVerificationCheckResponse(VerificationCheck verification){
        final PhoneVerifyResponse resp = new PhoneVerifyResponse();
        resp.setAccountSid(verification.getAccountSid());
        resp.setServiceSid(verification.getServiceSid());
        resp.setToNumber(verification.getTo());
        resp.setStatus(verification.getStatus());
        return resp;
    }

    protected abstract MessageCreator createMessageCreator(String to, String from, String body);
}
