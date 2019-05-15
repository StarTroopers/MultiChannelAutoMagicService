package com.fanniemae.starapp.providers.externals.twilio.message;

import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.twilio.exception.TwilioException;
import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TwilioAlertWhatsappProvider extends TwilioAlertSMSProvider{


    private static final Logger LOGGER = LogManager.getLogger(TwilioAlertWhatsappProvider.class);
    private static final String WHATSAPP = "whatsapp:";

    public TwilioAlertWhatsappProvider(TwilioRestClient client){
        super(client);
    }

    @Override
    public MessageResponse handleMessage(SMSMessage message, String traceId) {
        LOGGER.debug("Sending Twilio Whatsapp Message! traceId is {}", traceId);


        /*
        final MessageCreator msgCreator = createMessageCreator(message.getTo(), message.getFrom(),
                message.getBody());
                */
        //Hard code using the whatsapp number
        final MessageCreator msgCreator = createMessageCreator(message.getTo(), "+14155238886",
                message.getBody());

        final MessageResponse<String> response = new MessageResponse<>();
        try {
            final Message msgResponse = msgCreator.create(this.client);

            LOGGER.debug("Response code is {}, Whatsapp Message is {}, traceId is {}",
                    msgResponse.getErrorCode(), msgResponse.getErrorMessage(), traceId);

            if (msgResponse.getErrorCode() == null && msgResponse.getErrorMessage() == null) {
                response.setStatus(true);

            }else{
                LOGGER.debug("Error found sending a Whatsapp message, traceId is {}", traceId);
                response.setMessage(msgResponse.getErrorMessage());
            }

        } catch (TwilioException ex) {
            LOGGER.error("Error sending Whatsapp message of {}. traceId is {}", ex, traceId);
            response.setStatus(false);
        }

        return response;
    }

    @Override
    protected MessageCreator createMessageCreator(String to, String from, String body) {

        final String whatsappTo = WHATSAPP + to;
        final String whatsappFrom = WHATSAPP + from;

        LOGGER.debug("Sending whatsapp message to {} from {}", whatsappTo, whatsappFrom);
        return new MessageCreator(new PhoneNumber(whatsappTo), new PhoneNumber(whatsappFrom), body);
    }
}
