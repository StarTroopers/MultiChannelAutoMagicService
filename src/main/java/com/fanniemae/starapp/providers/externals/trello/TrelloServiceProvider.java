package com.fanniemae.starapp.providers.externals.trello;

import com.julienvey.trello.Trello;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.JDKTrelloHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrelloServiceProvider {

    private static final Logger LOGGER = LogManager.getLogger(TrelloServiceProvider.class);

    @Value("${starapp.trello.key}")
    private String key;

    @Value("${starapp.trello.token}")
    private String accountAuthToken;

    @Bean
    public Trello createTrelloProvider() {
        return new TrelloImpl(key, accountAuthToken, new JDKTrelloHttpClient());
    }
}
