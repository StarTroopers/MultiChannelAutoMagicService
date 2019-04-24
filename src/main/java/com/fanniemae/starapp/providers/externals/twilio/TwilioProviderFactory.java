package com.fanniemae.starapp.providers.externals.twilio;

import com.fanniemae.starapp.providers.externals.twilio.message.TwilioAlertSMSProvider;
import com.fanniemae.starapp.providers.externals.twilio.message.TwilioReplierSMSProvider;
import com.fanniemae.starapp.providers.externals.twilio.verify.TwilioVerifyPhoneCheckProvider;
import com.fanniemae.starapp.providers.externals.twilio.verify.TwilioVerifyPhoneProvider;
import com.twilio.http.TwilioRestClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Component
public class TwilioProviderFactory {

    private static final Logger LOGGER = LogManager.getLogger(TwilioProviderFactory.class);

    @Autowired
    private TwilioConfigCredential config;

    private static Map<ProviderType, TwilioServiceProvider> serviceProviders;


    @PostConstruct
    public void initProviders() {
        LOGGER.debug("Initializing the Twilio configuration!");
        serviceProviders = new HashMap<>();

        final TwilioRestClient client = new TwilioRestClient
                .Builder(config.getAccountSid(), config.getAccountAuthToken()).build();

        serviceProviders.put(ProviderType.SMS_ALERT, new TwilioAlertSMSProvider(client));
        serviceProviders.put(ProviderType.SMS_RECEIVE, new TwilioReplierSMSProvider(client));
        serviceProviders.put(ProviderType.SMS_VERIFICATION, new TwilioVerifyPhoneProvider(client,
                config.getAccountVerifyServiceSid()));
        serviceProviders.put(ProviderType.SMS_VERIFICATION_CHECK, new TwilioVerifyPhoneCheckProvider(client,
                config.getAccountVerifyServiceSid()));


    }

    public TwilioServiceProvider getProviderService(ProviderType type){
        LOGGER.debug("Getting provider service for a given type of {}", type);
        return serviceProviders.get(type);
    }


    public enum ProviderType{
        SMS_ALERT,
        SMS_RECEIVE,
        SMS_VERIFICATION,
        SMS_VERIFICATION_CHECK,
        VOICE;
    }

}
