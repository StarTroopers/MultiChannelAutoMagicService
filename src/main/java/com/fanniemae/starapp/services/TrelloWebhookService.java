package com.fanniemae.starapp.services;

import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import com.fanniemae.starapp.providers.externals.trello.models.TrelloResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessageRequest;
import com.fanniemae.starapp.repositories.MultiChannelAutoMessageRepository;
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
                    messageResponseHandler(responseMessage, multiChnlMsg);
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
                    messageResponseHandler(responseMessage, multiChnlMsg);
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
                    messageResponseHandler(responseMessage, multiChnlMsg);
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
                    messageResponseHandler(responseMessage, multiChnlMsg);
                }catch (Exception e) {
                }
                break;
        }
    }

    private void messageResponseHandler(SMSMessage responseMessage, MultiChannelAutoMessage multiChnlMsg) {
        LOGGER.debug(responseMessage.getBody());
        if ("SMS".equals(multiChnlMsg.getChannelType()))
            twilioSMSService.notifyUser(responseMessage, null);
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
