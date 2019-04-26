package com.fanniemae.starapp.services.messaging.sms.tags;

import com.fanniemae.starapp.providers.externals.twilio.models.MessageResponse;

public interface TagMessagerHandler {

    MessageResponse handleMessage(String message, String traceId);
}
