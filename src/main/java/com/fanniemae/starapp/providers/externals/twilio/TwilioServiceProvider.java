package com.fanniemae.starapp.providers.externals.twilio;

import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;
import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessage;

public interface TwilioServiceProvider {

    MessageResponse handleMessage(SMSMessage message, String traceId);
}
