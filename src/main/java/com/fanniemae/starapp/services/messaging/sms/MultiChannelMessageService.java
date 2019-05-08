package com.fanniemae.starapp.services.messaging.sms;

import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import com.fanniemae.starapp.repositories.MultiChannelAutoMessageRepository;
import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Card;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service class for handling message from different channels.
 */
@Service
public class MultiChannelMessageService {

    private static final Logger LOGGER = LogManager.getLogger(MultiChannelMessageService.class);

    @Value("${starapp.trello.idlist}")
    String idlist;

    @Autowired
    Trello trelloApi;

    @Autowired
    MultiChannelAutoMessageRepository multiChannelAutoMessageRepository;


    /**
     * Create a Trello Card with the tweet
     * @param msg
     */
    public MultiChannelAutoMessage createChannelMessage(final MultiChannelAutoMessage msg, String message){
        LOGGER.debug("Creating a trello card from a tweet of {}", msg.getScreenName());

        Card card = new Card();
        card.setName("Tweet from @" + msg.getScreenName());
        card.setDesc(message);
        card = trelloApi.createCard(idlist, card);
        return multiChannelAutoMessageRepository.save(msg);

    }



}
