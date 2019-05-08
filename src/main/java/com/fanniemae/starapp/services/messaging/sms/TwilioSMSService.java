package com.fanniemae.starapp.services.messaging.sms;

import com.fanniemae.starapp.providers.externals.twilio.TwilioProviderFactory;
import com.fanniemae.starapp.providers.externals.twilio.TwilioServiceProvider;
import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * Concrete for sending SMS using Twilio SDK
 */
@Service
public class TwilioSMSService extends BaseSMService {

    private static final Logger LOGGER = LogManager.getLogger(TwilioSMSService.class);


    /**
     * Handles response sent to a Twilio Number
     * @param message
     * @param traceId
     * @return
     */
    public MessageResponse generateSMSReply(SMSMessage message, String traceId){
         final TwilioServiceProvider provider = twilioFactory
                .getProviderService(TwilioProviderFactory.ProviderType.SMS_RECEIVE);

        final MessageResponse response = provider.handleMessage(message, traceId);
        return response;
    }


    /**
     * Sends a message from Twilio Number to Non-Twilio Number
     * @param message
     * @param traceId
     * @return
     */
    public MessageResponse notifyUser(SMSMessage message, String traceId) {
        LOGGER.info("Notifying user with message of {}! traceId of {} ", message , traceId);

        final TwilioServiceProvider provider = twilioFactory
                .getProviderService(TwilioProviderFactory.ProviderType.SMS_ALERT);

        final MessageResponse response = provider.handleMessage(message, traceId);
        return response;
    }

    /**
     * Initiates user's phone verification
     * @param message
     * @param traceId
     * @return
     */
    public MessageResponse initUserPhoneVerification(SMSMessage message, String traceId) {
        LOGGER.info("Initiating phone verification of user! traceId of {}", traceId);
        LOGGER.info( "Verifying phone number of {}. traceId of {}", message.getTo(), traceId);

        final TwilioServiceProvider provider = twilioFactory
                .getProviderService(TwilioProviderFactory.ProviderType.SMS_VERIFICATION);

        final MessageResponse response = provider.handleMessage(message, traceId);
        return response;
    }

    /**
     * Confirms user's phone number
     * @param message
     * @param traceId
     * @return
     */
    public MessageResponse confirmUserPhoneVerification(SMSMessage message, String traceId) {
        LOGGER.info( "Confirming user's phone number of {}. traceId of {}", message.getTo(), traceId);

        final TwilioServiceProvider provider = twilioFactory
                .getProviderService(TwilioProviderFactory.ProviderType.SMS_VERIFICATION_CHECK);

        final MessageResponse response = provider.handleMessage(message, traceId);
        return response;
    }

    /**
     * Sends a whatsapp message
     * @param message
     * @param traceId
     * @return
     */
    public MessageResponse notifyWhatsappuser(SMSMessage message, String traceId) {
        LOGGER.info("Notifying user with whatsapp message of {}! traceId of {} ", message , traceId);
        final TwilioServiceProvider provider = twilioFactory
                .getProviderService(TwilioProviderFactory.ProviderType.WHATSAPP_ALERT);

        final MessageResponse response = provider.handleMessage(message, traceId);
        return response;
    }


}
