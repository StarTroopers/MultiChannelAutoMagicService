package com.fanniemae.starapp.controllers.messaging;

import com.fanniemae.starapp.commons.AppHttpHeaders;
import com.fanniemae.starapp.controllers.request.ZapTwitterBean;
import com.fanniemae.starapp.domains.Customer;
import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import com.fanniemae.starapp.repositories.CustomerRepository;
import com.fanniemae.starapp.repositories.MultiChannelAutoMessageRepository;
import com.fanniemae.starapp.services.messaging.sms.MessageChannelType;
import com.fanniemae.starapp.services.messaging.sms.MultiChannelMessageService;
import com.google.gson.Gson;
import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Webhooks for integrating with Zapier with Twitter. Zapier polls the tweets from twitter every 5 minutes. So it takes time
 */
@RestController
@RequestMapping("/twitter/webhook")
public class ZapierTwitterWebhookController {

    private static final Logger LOGGER = LogManager.getLogger(ZapierTwitterWebhookController.class);

    @Value("${starapp.trello.idlist}")
    String idlist;

    @Autowired
    MultiChannelAutoMessageRepository multiChannelAutoMessageRepository;

    @Autowired
    Trello trelloApi;

    @Autowired
    private MultiChannelMessageService multiChannelMessageService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping("/message")
    public ResponseEntity<String> handleTwitterMessage(@RequestBody String message,
                                                       @RequestHeader(name = AppHttpHeaders.APP_SOURCE) String appSource) {
        LOGGER.debug("## Handling twitter message of {}. App source is {}", message, appSource);
        final ZapTwitterBean requestBean = new Gson().fromJson(message, ZapTwitterBean.class);
        LOGGER.debug("## Converted message to Object of {}", requestBean);

        final String id = UUID.randomUUID().toString();

        LOGGER.info("{}", message);
        MultiChannelAutoMessage multiCnlMsg;
        String msgBody = requestBean.getText();

        if (msgBody.contains("#")) {
            int hashIndex = msgBody.indexOf("#");
            Long msgId = Long.parseLong(msgBody.substring(hashIndex + 1, msgBody.indexOf(" ", hashIndex)));
            Optional<MultiChannelAutoMessage> multiChannelAutoMessages = multiChannelAutoMessageRepository.findById(msgId);
            multiCnlMsg = multiChannelAutoMessages.get();
            trelloApi.addCommentToCard(multiCnlMsg.getCardId(), requestBean.getText().replace("#" + msgId, ""));

        } else {
            List<Customer> customers = customerRepository.findByScreenName(getScreenName(requestBean));
            Card card = new Card();
            card.setName(requestBean.getText());
            card.setDesc("Organization: " + customers.get(0).getOrg() + " Name: " + customers.get(0).getLastName()
                    + "," + customers.get(0).getFirstName() + " Contact:" + getScreenName(requestBean) + "\n\n" + requestBean.getText());
            card = trelloApi.createCard(idlist, card);

            multiCnlMsg = new MultiChannelAutoMessage();
            multiCnlMsg.setAccountsSid(requestBean.getUser().getId());
            multiCnlMsg.setCardId(id);
            multiCnlMsg.setChannelType(MessageChannelType.TWITTER.getTypeValue());
            multiCnlMsg.setContact(getScreenName(requestBean));
            multiCnlMsg = this.multiChannelMessageService.createChannelMessage(multiCnlMsg, requestBean.getText());
            try {
                File file  = ResourceUtils.getFile("/var/app/current/"+customers.get(0).getIconPrefix()+"_"+ multiCnlMsg.getChannelType()+"_icon.png");
                LOGGER.debug("Attachment Name: "+ file.getAbsolutePath() + file.length());
                trelloApi.addAttachmentToCard(card.getId(), file);
            } catch(FileNotFoundException e){
                File file  = null;
                try {
                    file = ResourceUtils.getFile("/var/app/current/TWITTER_icon.png");
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                LOGGER.debug("Attachment Name: "+ file.getAbsolutePath() + file.length());
                trelloApi.addAttachmentToCard(card.getId(), file);
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private String getScreenName(ZapTwitterBean bean) {
        return bean.getUser().getScreenName() != null ? bean.getUser().getScreenName() : bean.getUser().getName();
    }


    /**
     * TODO: This needs to be a webhook to poll events generated by the app that needs to be tweeted as response?
     * Need to discuss..
     *
     * @return
     */
    private String getAppTweetEvent() {

        return null;
    }

}
