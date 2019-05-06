package com.fanniemae.starapp.services.messaging.sms;

import com.fanniemae.starapp.commons.MessageConstants;
import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessageRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Locale;

/**
 * Service for handling incoming SMS
 */
@Service
public class SMSMessageHandlerService extends BaseSMService {

    private static final Logger LOGGER = LogManager.getLogger(SMSMessageHandlerService.class);
    
    @Autowired
    MessageSource messageSource;

    @Autowired
    private TwilioSMSService twilioSMSService;

    /**
     * Handle SMS message received
     *
     * @param request
     * @param traceId
     */
    public MessageResponse handleSmsMessage(final SMSMessageRequest request, Long requestId, String traceId) {
        LOGGER.info("Creating an SMS Message log. traceId of {}", traceId);




        //TODO: NEED TO STORE THE REQUEST IN DB. INVOKE DAO HERE
        final boolean result = true; //smsMessageLogDao.createMessageLog(request, traceId);



        if (result) {

            final String message = request.getBody();

            //TODO: Need to invoke message body processing


            final SMSMessage successReply = new SMSMessageRequest();
            MessageFormat mf = new MessageFormat(messageSource.getMessage((message.contains("#"))?"starapp.twillio.acknoledgeupdate":"starapp.twillio.acknoledgement", null, Locale.US));
            successReply.setBody(mf.format(new Object[]{requestId}));
            return twilioSMSService.generateSMSReply(successReply, traceId);


        } else {
            // Will not throw an error but generate an error message as response
            final SMSMessage errorReply = new SMSMessageRequest();
            errorReply.setBody(errorHandler.getErrorMessage(MessageConstants.CODE_SMS_ERROR_REPLY));
            return twilioSMSService.generateSMSReply(errorReply, traceId);
        }

    }


}
