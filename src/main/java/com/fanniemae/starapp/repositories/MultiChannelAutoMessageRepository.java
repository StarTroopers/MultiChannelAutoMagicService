package com.fanniemae.starapp.repositories;

import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MultiChannelAutoMessageRepository extends JpaRepository<MultiChannelAutoMessage, Long> {
}
