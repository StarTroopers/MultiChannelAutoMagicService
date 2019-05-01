package com.fanniemae.starapp.repositories;

import com.fanniemae.starapp.domains.MultiChannelAutoMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MultiChannelAutoMessageRepository extends JpaRepository<MultiChannelAutoMessage, Long> {

    List<MultiChannelAutoMessage> findByCardId(String cardId);
}
