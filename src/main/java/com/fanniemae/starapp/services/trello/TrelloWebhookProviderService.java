package com.fanniemae.starapp.services.trello;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Setting up webhook in trello
 */
public class TrelloWebhookProviderService {


    private static final Logger LOGGER = LogManager.getLogger(TrelloWebhookProviderService.class);

    private RestTemplate restTemplate;


    public TrelloWebhookProviderService(){
        final RestTemplateBuilder templateBuilder = new RestTemplateBuilder();
        final List<GsonHttpMessageConverter> converterList = new ArrayList<>();
        restTemplate = templateBuilder.messageConverters(converterList).build();
    }


    public void invokeWebHook(){


        /**
         *  Provide the necessary key and tokens.
         */
        final UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://api.trello.com/1/tokens/<token>/webhooks")
                .queryParam("callbackURL", "http://startroopersws-env.c2erwwmjsp.us-east-2.elasticbeanstalk.com:8082/webhooks/message")
                .queryParam("active", "true")
                .queryParam("description", "Webhook to update status to MultiChannel")
                .queryParam("idModel", "<idModel>")
                .queryParam("key", "<key>");


        final HttpEntity entity = new HttpEntity(null);

        System.out.printf("URL IS %s ", builder.build().toString());
        ResponseEntity resp = this.restTemplate.exchange(builder.build().toUri(), HttpMethod.POST,entity, String.class);

        LOGGER.debug("RESPONSE is {}", resp.getStatusCode());
    }


}
