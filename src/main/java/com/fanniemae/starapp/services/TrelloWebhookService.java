package com.fanniemae.starapp.services;

import com.fanniemae.starapp.controllers.request.ContactUsBean;
import com.fanniemae.starapp.domains.Customer;
import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import com.fanniemae.starapp.providers.externals.trello.models.TrelloResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessageRequest;
import com.fanniemae.starapp.repositories.CustomerRepository;
import com.fanniemae.starapp.repositories.MultiChannelAutoMessageRepository;
import com.fanniemae.starapp.services.email.EmailSender;
import com.fanniemae.starapp.services.messaging.sms.MessageChannelType;
import com.fanniemae.starapp.services.messaging.sms.TwilioSMSService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

@Service
public class TrelloWebhookService {

    private static final Logger LOGGER = LogManager.getLogger(TrelloWebhookService.class);

    @Autowired
    MultiChannelAutoMessageRepository multiChannelAutoMessageRepository;
    @Autowired
    MessageSource messageSource;
    @Autowired
    TwilioSMSService twilioSMSService;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private EmailSender emailSender;

    public void handleWebhookMessage(TrelloResponse message) {

        final SMSMessage responseMessage = new SMSMessageRequest();

        //TODO: Grab from property on the Twilio Number
        responseMessage.setFrom("+16505252660");

        MultiChannelAutoMessage multiChnlMsg;
        MessageFormat mf;
        switch (message.getAction().getType()) {
            case "commentCard":
                LOGGER.debug("Comment Added to card: " + message.getAction().getData().getText());
                multiChnlMsg = getMessageByCardId(message.getAction().getData().getCard().getId());
                try {
                    responseMessage.setTo(multiChnlMsg.getContact());
                    mf = new MessageFormat(messageSource.getMessage("starapp.twillio.commentCard", null, Locale.US));
                    responseMessage.setBody(mf.format(new Object[]{message.getAction().getData().getText(), multiChnlMsg.getId()}));
                    messageResponseHandler(responseMessage, multiChnlMsg, "Comment Added to card");
                } catch (Exception e) {
                }
                break;
            case "deleteCard":
                LOGGER.debug("Card deleted: " + message.getAction().getData().getCard().getId());
                try {
                    multiChnlMsg = getMessageByCardId(message.getAction().getData().getCard().getId());
                    responseMessage.setTo(multiChnlMsg.getContact());
                    responseMessage.setTo(multiChnlMsg.getContact());
                    mf = new MessageFormat(messageSource.getMessage("starapp.twillio.deleteCard", null, Locale.US));
                    responseMessage.setBody(mf.format(new Object[]{multiChnlMsg.getId()}));
                    messageResponseHandler(responseMessage, multiChnlMsg, "Card deleted");
                }catch (Exception e) {
                }
                break;
            case "updateComment":
                LOGGER.debug("Card comment updated: " + message.getAction().getData().getAction().getText());
                try {
                    multiChnlMsg = getMessageByCardId(message.getAction().getData().getCard().getId());
                    responseMessage.setTo(multiChnlMsg.getContact());
                    mf = new MessageFormat(messageSource.getMessage("starapp.twillio.updateComment", null, Locale.US));
                    responseMessage.setBody(mf.format(new Object[]{multiChnlMsg.getId(), message.getAction().getData().getAction().getText()}));
                    messageResponseHandler(responseMessage, multiChnlMsg, "Card comment updated");
                }catch (Exception e) {
                }
                break;
            case "updateCard":
                LOGGER.debug("Card moved: " + message.getAction().getData().getListAfter().getName());
                try {
                    multiChnlMsg = getMessageByCardId(message.getAction().getData().getCard().getId());
                    responseMessage.setTo(multiChnlMsg.getContact());
                    mf = new MessageFormat(messageSource.getMessage("starapp.twillio.updateCard", null, Locale.US));
                    responseMessage.setBody(mf.format(new Object[]{multiChnlMsg.getId(), message.getAction().getData().getListAfter().getName(), message.getAction().getData().getListBefore().getName()}));
                    messageResponseHandler(responseMessage, multiChnlMsg, "Card moved");
                }catch (Exception e) {
                }
                break;
        }
    }

    private void messageResponseHandler(SMSMessage responseMessage, MultiChannelAutoMessage multiChnlMsg, String subject) {
        LOGGER.debug(responseMessage.getBody());

        final MessageChannelType type = MessageChannelType.convertType(multiChnlMsg.getChannelType());

        if (MessageChannelType.SMS.equals(type)) {
            List<Customer> customer = customerRepository.findByPhone(multiChnlMsg.getContact());
            responseMessage.setBody("Fannie Mae @ your service: \nDear "+ customer.get(0).getFirstName() + " " + responseMessage.getBody());
            twilioSMSService.notifyUser(responseMessage, null);
        }else if (MessageChannelType.WHATSAPP.equals(type)) {
            List<Customer> customer = customerRepository.findByPhone(multiChnlMsg.getContact());
            responseMessage.setBody("Fannie Mae @ your service: \nDear " + customer.get(0).getFirstName() + " " + responseMessage.getBody());
            twilioSMSService.notifyWhatsappuser(responseMessage, null);
        }else if (MessageChannelType.EMAIL.equals(type)) {
            List<Customer> customer = customerRepository.findByEmail(multiChnlMsg.getContact());
            responseMessage.setBody("Fannie Mae @ your service: \nDear "+ customer.get(0).getFirstName() + " " + responseMessage.getBody());
            emailSender.send(new ContactUsBean(multiChnlMsg.getFirstName(), multiChnlMsg.getLastName(), multiChnlMsg.getContact(), responseMessage.getBody(), subject));
        }
    }

    private MultiChannelAutoMessage getMessageByCardId(String cardId) {
        List<MultiChannelAutoMessage> multiChnlMsgs = multiChannelAutoMessageRepository.findByCardId(cardId);
        LOGGER.debug(cardId+":"+multiChnlMsgs.size());
        if(!multiChnlMsgs.isEmpty())
                return multiChnlMsgs.get(0);
        else
            return new MultiChannelAutoMessage();
    }
}
