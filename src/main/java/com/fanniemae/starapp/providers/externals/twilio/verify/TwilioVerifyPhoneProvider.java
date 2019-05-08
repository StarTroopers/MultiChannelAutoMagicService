package com.fanniemae.starapp.providers.externals.twilio.verify;

import com.fanniemae.starapp.providers.externals.twilio.BaseTwilioServiceProvider;
import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.PhoneVerifyChannelType;
import com.fanniemae.starapp.providers.externals.twilio.models.PhoneVerifyResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.twilio.exception.ApiException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.rest.verify.v2.service.Verification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.util.StringUtils;

/**
 * Provider class for sending a verification code on the TO field of the SMSMessage.
 */
public class TwilioVerifyPhoneProvider extends BaseTwilioServiceProvider {

    private static final Logger LOGGER = LogManager.getLogger(TwilioVerifyPhoneProvider.class);

    protected TwilioRestClient client;
    protected String accountVerifySid;

    public TwilioVerifyPhoneProvider(TwilioRestClient client, String accountVerifySid){
        this.client = client;
        this.accountVerifySid = accountVerifySid;
    }

    @Override
    public MessageResponse handleMessage(SMSMessage message, String traceId) {
        LOGGER.debug("Start verifying the phone number of {}. traceId of {}", message.getTo(), traceId);

        final MessageResponse<PhoneVerifyResponse> response = new MessageResponse<>();
        try {
            final Verification verification = Verification.creator(accountVerifySid, message.getTo(), PhoneVerifyChannelType
                    .SMS_VERIFICATION.getChannelType())
                    .setCustomMessage(StringUtils.isEmpty(message.getCustomMessage()) ? null : message.getCustomMessage())
                    .create(client);

            LOGGER.debug("Verification url is {}", verification.getUrl());
            LOGGER.debug("Verification sid is {}", verification.getSid());
            LOGGER.debug("Verification status is {}, valid is {}", verification.getStatus(), verification.getValid());


            if("pending".equals(verification.getStatus()) && !verification.getValid()){

                response.setMessage("Pending verification");
                response.setStatus(true);
                response.setContent(createVerificationResponse(verification));
            }

        }catch(ApiException ex){

            LOGGER.error("Error found handling the twilio API during verification! traceId is {}", traceId);
            LOGGER.error("Error is {}", ex);
            response.setStatus(false);
            response.setMessage(ex.getMessage());
        }
        return response;
    }

    @Override
    protected MessageCreator createMessageCreator(String to, String from, String body) {
        return null;
    }
}
