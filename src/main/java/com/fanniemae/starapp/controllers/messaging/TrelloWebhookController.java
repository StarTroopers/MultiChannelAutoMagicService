package com.fanniemae.starapp.controllers.messaging;

import com.fanniemae.starapp.commons.AppHttpHeaders;
import com.fanniemae.starapp.controllers.BaseAppController;
import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import com.fanniemae.starapp.providers.externals.trello.models.TrelloResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessageRequest;
import com.fanniemae.starapp.repositories.MultiChannelAutoMessageRepository;
import com.fanniemae.starapp.services.TrelloWebhookService;
import com.fanniemae.starapp.services.messaging.sms.TwilioSMSService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/webhooks")
public class TrelloWebhookController extends BaseAppController {

    private static final Logger LOGGER = LogManager.getLogger(TrelloWebhookController.class);

    @Value("${starapp.trello.token}")
    String trelloToken;

    @Value("${starapp.trello.webhook.url}")
    String webhookURL;

    @Autowired
    private TrelloWebhookService trelloWebhookService;
    /**
     * Webhook API for Trello to use when Board is modified by User
     *
     * @param message
     * @return
     */
    @RequestMapping(value= "/message", method = {RequestMethod.HEAD,RequestMethod.POST})
    @ResponseStatus(HttpStatus.OK)
    public String handleWebhookResponse(@RequestBody(required=false) TrelloResponse message,
                                        @RequestHeader(name = AppHttpHeaders.TRELLO_WEBHOOK_HEADER, required = false)
                                                String headerHash) {
        LOGGER.debug("request received");
        if(message != null && message.getAction() != null && !message.getAction().equals(null) ) {
            LOGGER.debug("Message From Trello: " + message.toString());
            LOGGER.debug("Result of Verification: " + verifyTrelloWebhookRequest(message, headerHash));
            trelloWebhookService.handleWebhookMessage(message);
        }
        return "SUCEESS";
    }

    private boolean verifyTrelloWebhookRequest(TrelloResponse message, String headerHash) {
        Mac hasher;
        try {
            hasher = Mac.getInstance("HmacSHA1");
            hasher.init(new SecretKeySpec(trelloToken.getBytes(), "base64"));
            ObjectMapper Obj = new ObjectMapper();

            String hash = Base64.encodeBase64String(hasher.doFinal(Obj.writeValueAsString(message + webhookURL).getBytes()));
            LOGGER.debug("Hash from Header: " + headerHash + " Hash from encode:" + hash);
            return hash.equalsIgnoreCase(headerHash);
        } catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException | JsonProcessingException e) {
            LOGGER.error("Error occured while verifying the Webhook Request.");
            e.printStackTrace();
        }
        return false;
    }

}
