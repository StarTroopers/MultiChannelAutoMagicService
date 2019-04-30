package com.fanniemae.starapp.controllers.messaging;

import com.fanniemae.starapp.commons.AppErrorType;
import com.fanniemae.starapp.commons.AppHttpHeaders;
import com.fanniemae.starapp.commons.MessageConstants;
import com.fanniemae.starapp.controllers.BaseAppController;
import com.fanniemae.starapp.controllers.request.SMSMessageBean;
import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessageRequest;
import com.fanniemae.starapp.repositories.MultiChannelAutoMessageRepository;
import com.fanniemae.starapp.repositories.SMSMessageBeanRepository;
import com.fanniemae.starapp.services.messaging.sms.SMSMessageHandlerService;
import com.fanniemae.starapp.services.messaging.sms.TwilioSMSService;
import com.fanniemae.starapp.swagger.SMSFeatureDoc;
import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sms-alert")
public class SMSPhoneAlertController extends BaseAppController {

    private static final Logger LOGGER = LogManager.getLogger(SMSPhoneAlertController.class);

    @Value("${starapp.trello.idlist}")
    String idlist;
    @Autowired
    SMSMessageBeanRepository smsMessageBeanRepository;
    @Autowired
    Trello trelloApi;
    @Autowired
    MultiChannelAutoMessageRepository multiChannelAutoMessageRepository;
    @Autowired
    private TwilioSMSService twilioSMSService;
    @Autowired
    private SMSMessageHandlerService smsMessageHandlerService;

    /**
     * Webhook API for Twilio to use when message is capture by the Twilio Number
     *
     * @param message
     * @param traceId
     * @return
     */
    @PostMapping(value = "/message",
            produces = "application/xml")
    @SMSFeatureDoc(value = "Captures message from SMS")
    public String handleSmsNotification(@RequestParam Map<String, String> message,
                                        @RequestHeader(name = AppHttpHeaders.TRACEID_HEADER, required = false)
                                                String traceId,
                                        @RequestHeader(name="Accept-Language",required = false) Locale locale) {

        LOGGER.debug("Handling SMS Message from mobile!");

        final SMSMessageRequest smsMessage = new SMSMessageRequest();

        smsMessage.setAccountSid(message.get("AccountSid"));
        smsMessage.setApiVersion(message.get("ApiVersion"));
        smsMessage.setBody(message.get("Body"));
        smsMessage.setMessageSid(message.get("MessageSid"));
        smsMessage.setNumMedia(Integer.parseInt(message.get("NumMedia")));
        smsMessage.setNumSegments(Integer.parseInt(message.get("NumSegments")));
        smsMessage.setSmsMessageSid(message.get("SmsMessageSid"));
        smsMessage.setSmsSid(message.get("SmsSid"));
        smsMessage.setSmsStatus(message.get("received"));

        smsMessage.setFrom(message.get("From"));
        smsMessage.setFromCity(message.get("FromCity"));
        smsMessage.setFromCountry(message.get("FromCountry"));
        smsMessage.setFromState(message.get("FromState"));
        smsMessage.setFromZip(message.get("FromZip"));

        smsMessage.setTo(message.get("To"));
        smsMessage.setToCity(message.get("ToCity"));
        smsMessage.setToCountry(message.get("ToCountry"));
        smsMessage.setToState(message.get("ToState"));
        smsMessage.setToZip(message.get("ToZip"));
        smsMessageBeanRepository.save(smsMessage);

        LOGGER.info("{}", smsMessage);
        MultiChannelAutoMessage multiChannelAutoMessage;
        String msgBody = smsMessage.getBody();
        if (msgBody.contains("#")) {
            int hashIndex = msgBody.indexOf("#");
            Long msgId = Long.parseLong(msgBody.substring(hashIndex + 1, msgBody.indexOf(" ", hashIndex)));
            Optional<MultiChannelAutoMessage> multiChannelAutoMessages = multiChannelAutoMessageRepository.findById(msgId);
            multiChannelAutoMessage = multiChannelAutoMessages.get();
            trelloApi.addCommentToCard(multiChannelAutoMessage.getCardId(), smsMessage.getBody().replace("#" + msgId, ""));

        } else {
            Card card = new Card();
            card.setName(smsMessage.getBody());
            card.setDesc(smsMessage.getBody());
            card = trelloApi.createCard(idlist, card);

            multiChannelAutoMessage = new MultiChannelAutoMessage();
            multiChannelAutoMessage.setAccountsSid(smsMessage.getAccountSid());
            multiChannelAutoMessage.setCardId(card.getId());

            multiChannelAutoMessage = multiChannelAutoMessageRepository.save(multiChannelAutoMessage);
        }
        final MessageResponse<String> response = smsMessageHandlerService.handleSmsMessage(smsMessage, multiChannelAutoMessage.getId(), locale, traceId);

        if (response.isStatus()) {
            LOGGER.debug("Successful handling SMS Message from mobile! traceId is {}", traceId);
            return response.getContent();
        }
        return null;

    }


    @PostMapping(value = "/send-alert")
    @SMSFeatureDoc(value = "Sends a message to a given phone number")
    public void sendAlertMessage(@RequestBody SMSMessageBean messageRqst,
                                 @RequestHeader(name = AppHttpHeaders.TRACEID_HEADER, required = false) String traceId) {

        LOGGER.debug("Testing the generation of the alert! traceId of {}", traceId);

        if (messageRqst == null || messageRqst.getMessage() == null || messageRqst.getToPhoneNumber() == null) {
            errorHandler.throwAppException(MessageConstants.CODE_INCOMPLETE_REQUEST_ERROR, AppErrorType.REQUEST_ERROR, null);
        }


        final SMSMessage message = new SMSMessageRequest();
        message.setTo(messageRqst.getToPhoneNumber());
        message.setBody(messageRqst.getMessage());

        //TODO: Grab from property on the Twilio Number
        message.setFrom("+16505252660");


        final MessageResponse response = twilioSMSService.notifyUser(message, traceId);
        if (response.isStatus()) {
            LOGGER.debug("Successful generating an alert message! traceId is {}", traceId);
        } else {

            LOGGER.error("Error generating an alert message! traceId is {}", traceId);
            errorHandler.throwAppException(MessageConstants.CODE_SMS_SEND_ALERTFAILED, AppErrorType.PROVIDER_ERROR, null);
        }

    }


}
