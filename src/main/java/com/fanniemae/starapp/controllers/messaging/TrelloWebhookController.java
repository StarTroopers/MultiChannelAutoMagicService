package com.fanniemae.starapp.controllers.messaging;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fanniemae.starapp.commons.AppHttpHeaders;
import com.fanniemae.starapp.controllers.BaseAppController;
import com.fanniemae.starapp.providers.externals.trello.models.TrelloResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/webhooks")
public class TrelloWebhookController  extends BaseAppController {

    private static final Logger LOGGER = LogManager.getLogger(TrelloWebhookController.class);
    
    @Value("${starapp.trello.token}")
    String trelloToken;
    
    /**
     * Webhook API for Trello to use when Board is modified by User
     * @param message
     * @param traceId
     * @return
     */
    @PostMapping(value = "/message",
            produces = "application/xml")
    public String handleWebhookResponse(@RequestBody TrelloResponse message,
                                        @RequestHeader(name = AppHttpHeaders.TRELLO_WEBHOOK_HEADER, required = false)
                                        String headerHash){
    	
        LOGGER.debug("Message From Trello: "+message.toString());
        LOGGER.debug("Result of Verification: "+ verifyTrelloWebhookRequest(message, headerHash));
        
        return "SUCEESS";
    }
    
    private boolean verifyTrelloWebhookRequest(TrelloResponse message, String headerHash) {
    	Mac hasher;
		try {
			hasher = Mac.getInstance("HmacSHA1");
			hasher.init(new SecretKeySpec(trelloToken.getBytes(), "base64"));
			ObjectMapper Obj = new ObjectMapper(); 

		    String hash = Base64.encodeBase64String(hasher.doFinal(Obj.writeValueAsString(message).getBytes()));
		    LOGGER.debug("Hash from Header: "+ headerHash + " Hash from encode:"+hash);
		    return hash.equalsIgnoreCase(headerHash);
		} catch (NoSuchAlgorithmException | InvalidKeyException | IllegalStateException | JsonProcessingException e) {
			LOGGER.error("Error occured while verifying the Webhook Request.");
			e.printStackTrace();
		}
		return false;
	    
	    
    }
}
