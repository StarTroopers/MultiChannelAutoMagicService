package com.fanniemae.starapp.controllers.messaging;

import com.fanniemae.starapp.commons.AppErrorType;
import com.fanniemae.starapp.commons.AppHttpHeaders;
import com.fanniemae.starapp.commons.MessageConstants;
import com.fanniemae.starapp.controllers.BaseAppController;
import com.fanniemae.starapp.controllers.request.SMSMessageBean;
import com.fanniemae.starapp.domains.Customer;
import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessageRequest;
import com.fanniemae.starapp.repositories.CustomerRepository;
import com.fanniemae.starapp.repositories.MultiChannelAutoMessageRepository;
import com.fanniemae.starapp.repositories.SMSMessageBeanRepository;
import com.fanniemae.starapp.services.messaging.sms.MessageChannelType;
import com.fanniemae.starapp.services.messaging.sms.SMSMessageHandlerService;
import com.fanniemae.starapp.services.messaging.sms.TwilioSMSService;
import com.fanniemae.starapp.swagger.SMSFeatureDoc;
import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sms-alert")
public class SMSPhoneAlertController extends BaseAppController {

    private static final Logger LOGGER = LogManager.getLogger(SMSPhoneAlertController.class);
    private static final String WHATSAPP_PREFIX = "whatsapp:";

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

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Webhook API for Twilio to use when message is capture by the Twilio Number or through Whatsapp number
     *
     * @param message
     * @param traceId
     * @return
     */
    @PostMapping(value = "/message",
            produces = "application/xml")
    @SMSFeatureDoc(value = "Captures message from SMS")
    public String handleSmsNotification(@RequestBody Map<String, String> message,
                                        @RequestHeader(name = AppHttpHeaders.TRACEID_HEADER, required = false)
                                                String traceId) {

        LOGGER.debug("Handling SMS Message from mobile!");

        final SMSMessageRequest smsMessage = new SMSMessageRequest();
        LOGGER.info( "Raw message is {}", message);

        //Whatsapp and SMS provided data:
        smsMessage.setSmsMessageSid(message.get("SmsMessageSid"));
        smsMessage.setNumMedia(Integer.parseInt(message.get("NumMedia")));
        smsMessage.setSmsSid(message.get("SmsSid"));
        smsMessage.setBody(message.get("Body"));
        smsMessage.setNumSegments(Integer.parseInt(message.get("NumSegments")));
        smsMessage.setMessageSid(message.get("MessageSid"));
        smsMessage.setAccountSid(message.get("AccountSid"));
        smsMessage.setApiVersion(message.get("ApiVersion"));
        smsMessage.setFrom(extractNumber(message.get("From")));
        smsMessage.setTo(extractNumber(message.get("To")));


        //Only SMS provided
        smsMessage.setSmsStatus(message.get("received"));
        smsMessage.setFromCity(message.get("FromCity"));
        smsMessage.setFromCountry(message.get("FromCountry"));
        smsMessage.setFromState(message.get("FromState"));
        smsMessage.setFromZip(message.get("FromZip"));
        smsMessage.setToCity(message.get("ToCity"));
        smsMessage.setToCountry(message.get("ToCountry"));
        smsMessage.setToState(message.get("ToState"));
        smsMessage.setToZip(message.get("ToZip"));

        smsMessage.setChannel( (isWhatsAppNumber(message.get("From")) &&  isWhatsAppNumber(message.get("To")))
                ? MessageChannelType.WHATSAPP.getTypeValue() : MessageChannelType.SMS.getTypeValue() );


        smsMessageBeanRepository.save(smsMessage);

        LOGGER.info("{}", smsMessage);
        MultiChannelAutoMessage multiCnlMsg;
        String msgBody = smsMessage.getBody();

        if (msgBody.contains("#")) {
            int hashIndex = msgBody.indexOf("#");
            Long msgId = Long.parseLong(msgBody.substring(hashIndex + 1, msgBody.indexOf(" ", hashIndex)));
            Optional<MultiChannelAutoMessage> multiChannelAutoMessages = multiChannelAutoMessageRepository.findById(msgId);
            multiCnlMsg = multiChannelAutoMessages.get();
            trelloApi.addCommentToCard(multiCnlMsg.getCardId(), smsMessage.getBody().replace("#" + msgId, ""));

        } else {
            List<Customer> customers = customerRepository.findByPhone(smsMessage.getFrom());
            Card card = new Card();
            card.setName(smsMessage.getBody());
            card.setDesc("Organization: " + customers.get(0).getOrg() +" Name: "+  customers.get(0).getLastName()+ "," +customers.get(0).getFirstName()+ " Contact:" +smsMessage.getFrom()+ "\n\n" +  smsMessage.getBody());
            card = trelloApi.createCard(idlist, card);

            multiCnlMsg = new MultiChannelAutoMessage();
            multiCnlMsg.setAccountsSid(smsMessage.getAccountSid());
            multiCnlMsg.setCardId(card.getId());
            multiCnlMsg.setChannelType( (isWhatsAppNumber(message.get("From")) &&  isWhatsAppNumber(message.get("To")))
                    ? MessageChannelType.WHATSAPP.getTypeValue() : MessageChannelType.SMS.getTypeValue() );
            multiCnlMsg.setContact(smsMessage.getFrom());
            multiCnlMsg = multiChannelAutoMessageRepository.save(multiCnlMsg);
            try {
                File file  = ResourceUtils.getFile("classpath:images/"+multiCnlMsg.getChannelType()+"_icon.png");
                LOGGER.debug("Attachment Name: "+ file.getAbsolutePath() + file.length());
                trelloApi.addAttachmentToCard(card.getId(), file);
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        final MessageResponse<String> response = smsMessageHandlerService.handleSmsMessage(smsMessage, multiCnlMsg.getId(), traceId);

        if (response.isStatus()) {
            LOGGER.debug("Successful handling SMS Message from mobile! traceId is {}", traceId);
            return response.getContent();
        }
        return null;

    }


    private boolean isWhatsAppNumber(String sourceNumber){
        return sourceNumber.startsWith(WHATSAPP_PREFIX);
    }

    private String extractNumber(String phoneNumber){
        if(isWhatsAppNumber(phoneNumber)){
            return phoneNumber.substring(WHATSAPP_PREFIX.length());
        }
        return phoneNumber;
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

    @PostMapping(value = "/notify-whatsapp")
    public void sendWhatsappAlertMessage(@RequestBody SMSMessageBean messageRqst,
                                         @RequestHeader(name = AppHttpHeaders.TRACEID_HEADER, required = false) String traceId){

        LOGGER.debug("Sending a whatsapp message! traceId of {}", traceId);

        if(messageRqst == null || messageRqst.getMessage() == null || messageRqst.getToPhoneNumber() == null){
            errorHandler.throwAppException(MessageConstants.CODE_INCOMPLETE_REQUEST_ERROR, AppErrorType.REQUEST_ERROR, null);
        }

        final SMSMessage message = new SMSMessageRequest();
        message.setTo(messageRqst.getToPhoneNumber());
        message.setBody(messageRqst.getMessage());

        //TODO: Grab from property on the Twilio Number
        message.setFrom("+14155238886");

        final MessageResponse response = twilioSMSService.notifyWhatsappuser(message, traceId);
        if(response.isStatus()){
            LOGGER.debug("Successful generating an alert message! traceId is {}", traceId);
        }else {

            LOGGER.error("Error generating an alert message! traceId is {}", traceId);
            errorHandler.throwAppException(MessageConstants.CODE_SMS_SEND_ALERTFAILED, AppErrorType.PROVIDER_ERROR, null);
        }

    }


}
