package com.fanniemae.starapp.repositories;

import com.fanniemae.starapp.providers.externals.twilio.models.SMSMessageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SMSMessageBeanRepository  extends JpaRepository<SMSMessageRequest,String> {
}
