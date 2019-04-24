package com.fanniemae.starapp.providers.externals.twilio.verify;

import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.PhoneVerifyResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.twilio.exception.ApiException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provider for handling verification of phone
 */
public class TwilioVerifyPhoneCheckProvider extends TwilioVerifyPhoneProvider {

    private static final Logger LOGGER = LogManager.getLogger(TwilioVerifyPhoneCheckProvider.class);

    public TwilioVerifyPhoneCheckProvider(TwilioRestClient client, String accountVerifySid) {
        super(client, accountVerifySid);
    }

    @Override
    public MessageResponse handleMessage(SMSMessage message, String traceId) {

        LOGGER.debug("Start verifying phone check of {}! traceId is {}", message.getTo(), traceId);

        final MessageResponse<PhoneVerifyResponse> response = new MessageResponse<>();
        try {

            final VerificationCheck verifyCheck = VerificationCheck.creator(accountVerifySid, message.getVerifyCode())
                    .setTo(message.getTo())
                    .create(client);
            LOGGER.debug("Verify check sid is {}", verifyCheck.getSid());
            LOGGER.debug("Verify check status is {}, valid is {}", verifyCheck.getStatus(), verifyCheck.getValid());

            if("approved".equals(verifyCheck.getStatus()) && verifyCheck.getValid()){

                response.setMessage("Pending verification");
                response.setStatus(true);
                response.setContent(createVerificationCheckResponse(verifyCheck));
            }

        }catch(ApiException ex){

            LOGGER.error("Error found handling the twilio API! traceId is {}", traceId);
            LOGGER.error("Error is {}", ex);
            response.setStatus(false);
            response.setMessage(ex.getMessage());
        }


        return response;
    }
}
