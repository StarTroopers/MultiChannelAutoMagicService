package com.fanniemae.starapp.providers.externals.twilio.message;

import com.fanniemae.starapp.providers.externals.twilio.BaseTwilioServiceProvider;
import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.twilio.exception.TwilioException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides the capability to handle messagge received by the Twilio Number and
 * how twilio responds to the SMS message received.
 *
 */
public class TwilioAlertSMSProvider extends BaseTwilioServiceProvider {

    private static final Logger LOGGER = LogManager.getLogger(TwilioAlertSMSProvider.class);

    protected final TwilioRestClient client;

    public TwilioAlertSMSProvider(TwilioRestClient restClient){
        this.client = restClient;
    }


    @Override
    public MessageResponse handleMessage(SMSMessage message, String traceId) {
        LOGGER.debug("Sending Twilio SMS Message! traceId is {}", traceId);

        final MessageCreator msgCreator = new MessageCreator(
                new PhoneNumber(message.getTo()),
                new PhoneNumber(message.getFrom()), message.getBody());


        final MessageResponse<String> response = new MessageResponse<>();
        try {

            final Message msgResponse = msgCreator.create(this.client);

            LOGGER.debug("Response code is {}, Message is {}, traceId is {}",
                    msgResponse.getErrorCode(), msgResponse.getErrorMessage(), traceId);

            if (msgResponse.getErrorCode() == null && msgResponse.getErrorMessage() == null) {
                response.setStatus(true);

            }else{
                LOGGER.debug("Error found sending an alert, traceId is {}", traceId);
                response.setMessage(msgResponse.getErrorMessage());
            }

        } catch (TwilioException ex) {
            LOGGER.error("Error sending twilio message of {}. traceId is {}", ex, traceId);
            response.setStatus(false);
        }

        return response;
    }
}
