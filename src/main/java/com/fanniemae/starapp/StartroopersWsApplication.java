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

@SpringBootApplication
@Import(ExternalProviderConfiguration.class)
public class StartroopersWsApplication {

	private static final Logger LOGGER = LogManager.getLogger(StartroopersWsApplication.class);
	private static final String MESSAGE_SOURCE = "StarAppMessages";

	public static void main(String[] args) {
		LOGGER.info("####### Starting Star Troopers Application #######");
		SpringApplication.run(StartroopersWsApplication.class, args);

		// TODO: On startup load the database with customer values
	}

	@Bean
	public static MessageSource messageSource(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(MESSAGE_SOURCE);
		return messageSource;
	}


}
