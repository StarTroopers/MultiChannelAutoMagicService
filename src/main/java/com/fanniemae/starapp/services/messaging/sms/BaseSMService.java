package com.fanniemae.starapp.services.messaging.sms;

import com.fanniemae.starapp.exceptions.handler.AppErrorHandler;
import com.fanniemae.starapp.providers.externals.twilio.TwilioProviderFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Base class for SMS
 */
public abstract class BaseSMService {

    @Autowired
    protected TwilioProviderFactory twilioFactory;

    @Autowired
    protected AppErrorHandler errorHandler;


}
