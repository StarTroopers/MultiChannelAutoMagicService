package com.fanniemae.starapp;

import com.fanniemae.starapp.providers.externals.ExternalProviderConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

@SpringBootApplication
@Import(ExternalProviderConfiguration.class)
public class StartroopersWsApplication {

    private static final Logger LOGGER = LogManager.getLogger(StartroopersWsApplication.class);
    private static final String MESSAGE_SOURCE = "StarAppMessages";

    public static void main(String[] args) {
        LOGGER.info("####### Starting Star Troopers Application #######");

        String[] orgs = {"CHASE", "CAPONE", "ELLI", "WELLS", "BOFA"};
        String[] channels = new String[]{"EMAIL", "FACEBOOK", "SMS", "TWITTER", "WHATSAPP"};
        for (String org : orgs) {
            for (String channel : channels) {
                try {
                    FileCopyUtils.copy(new ClassPathResource("images/" + org + "_" + channel + "_icon.png").getInputStream(),
                            new FileOutputStream("/var/app/current/" + org + "_" + channel + "_icon.png"));
                } catch (FileNotFoundException e) {
                    LOGGER.debug(org + "_" + channel + "_icon.png not found");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        SpringApplication.run(StartroopersWsApplication.class, args);
    }

    @Bean
    public static MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(MESSAGE_SOURCE);
        return messageSource;
    }


}
