package com.fanniemae.starapp.providers.externals;

import com.fanniemae.starapp.providers.externals.twilio.TwilioConfigCredential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalProviderConfiguration {

    @Bean
    public TwilioConfigCredential createTwilioConfigCredential(){
        return new TwilioConfigCredential();
    }

}
