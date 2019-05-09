package com.fanniemae.starapp;

import com.fanniemae.starapp.providers.externals.ExternalProviderConfiguration;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

@SpringBootApplication
@Import(ExternalProviderConfiguration.class)
public class StartroopersWsApplication {

	private static final Logger LOGGER = LogManager.getLogger(StartroopersWsApplication.class);
	private static final String MESSAGE_SOURCE = "StarAppMessages";

	public static void main(String[] args) {
		LOGGER.info("####### Starting Star Troopers Application #######");
		try {
			FileCopyUtils.copy(new ClassPathResource("images/EMAIL_icon.png").getInputStream(),
					new FileOutputStream("/var/app/current/EMAIL_icon.png"));
			FileCopyUtils.copy(new ClassPathResource("images/FACEBOOK_icon.png").getInputStream(),
					new FileOutputStream("/var/app/current/FACEBOOK_icon.png"));
			FileCopyUtils.copy(new ClassPathResource("images/SMS_icon.png").getInputStream(),
					new FileOutputStream("/var/app/current/SMS_icon.png"));
			FileCopyUtils.copy(new ClassPathResource("images/TWITTER_icon.png").getInputStream(),
					new FileOutputStream("/var/app/current/TWITTER_icon.png"));
			FileCopyUtils.copy(new ClassPathResource("images/WHATSAPP_icon.png").getInputStream(),
					new FileOutputStream("/var/app/current/WHATSAPP_icon.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		SpringApplication.run(StartroopersWsApplication.class, args);
	}

	@Bean
	public static MessageSource messageSource(){
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename(MESSAGE_SOURCE);
		return messageSource;
	}


}
