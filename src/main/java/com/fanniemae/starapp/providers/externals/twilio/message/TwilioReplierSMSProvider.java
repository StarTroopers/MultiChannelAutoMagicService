package com.fanniemae.starapp.providers.externals.twilio.message;

import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.twilio.http.TwilioRestClient;
import com.twilio.twiml.MessagingResponse;
import com.twilio.twiml.messaging.Body;
import com.twilio.twiml.messaging.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Handles the SMS received by the twilio number and provides the necessary response back
 * to the caller.
 */
public class TwilioReplierSMSProvider extends TwilioAlertSMSProvider {

    private static final Logger LOGGER = LogManager.getLogger(TwilioReplierSMSProvider.class);

    public TwilioReplierSMSProvider(TwilioRestClient restClient){
        super(restClient);
    }

    @Override
    public MessageResponse handleMessage(SMSMessage message, String traceId) {
        LOGGER.debug("Creating a message reply! traceId is {}", traceId);
        final MessageResponse<String> response = new MessageResponse<>();

        final Body body = new Body
                .Builder(message.getBody())
                .build();

        final Message sms = new Message
                .Builder()
                .body(body)
                .build();
        final MessagingResponse smsResponse = new MessagingResponse
                .Builder()
                .message(sms)
                .build();

        final String resp = smsResponse.toXml();
        response.setStatus(true);
        response.setContent(resp);
        return response;

    }
}
