package com.fanniemae.starapp.controllers.messaging;

import com.fanniemae.starapp.commons.AppHttpHeaders;
import com.fanniemae.starapp.controllers.request.ZapTwitterBean;
import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import com.fanniemae.starapp.services.messaging.sms.MessageChannelType;
import com.fanniemae.starapp.services.messaging.sms.MultiChannelMessageService;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Webhooks for integrating with Zapier with Twitter. Zapier polls the tweets from twitter every 5 minutes. So it takes time
 */
@RestController
@RequestMapping("/twitter/webhook")
public class ZapierTwitterWebhookController {

    private static final Logger LOGGER = LogManager.getLogger(ZapierTwitterWebhookController.class);

    @Autowired
    private MultiChannelMessageService multiChannelMessageService;

    @PostMapping("/message")
    public ResponseEntity<String> handleTwitterMessage(@RequestBody String message,
                                                       @RequestHeader(name = AppHttpHeaders.APP_SOURCE) String appSource){
        LOGGER.debug("## Handling twitter message of {}. App source is {}", message, appSource);
        final ZapTwitterBean requestBean = new Gson().fromJson(message, ZapTwitterBean.class);
        LOGGER.debug("## Converted message to Object of {}", requestBean);

        final String id = UUID.randomUUID().toString();

        final MultiChannelAutoMessage multiCnlMsg = new MultiChannelAutoMessage();
        multiCnlMsg.setAccountsSid(requestBean.getUser().getId());
        multiCnlMsg.setCardId(id);
        multiCnlMsg.setChannelType(MessageChannelType.TWITTER.getTypeValue());
        multiCnlMsg.setContact(getScreenName(requestBean));

        this.multiChannelMessageService.createChannelMessage(multiCnlMsg, requestBean.getText());

        return new ResponseEntity<>(HttpStatus.OK);
    }


    private String getScreenName(ZapTwitterBean bean){
        return bean.getUser().getScreenName() != null ? bean.getUser().getScreenName() : bean.getUser().getName();
    }


    /**
     * TODO: This needs to be a webhook to poll events generated by the app that needs to be tweeted as response?
     * Need to discuss..
     * @return
     */
    private String getAppTweetEvent(){

        return null;
    }

}