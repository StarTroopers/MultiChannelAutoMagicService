package com.fanniemae.starapp.domains;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MultiChannelAutoMessage {

    @Id
    @GeneratedValue
    private Long id;
    private String customerId;
    private String cardId;

    public Long getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getAccountsSid() {
        return accountsSid;
    }

    public void setAccountsSid(String accountsSid) {
        this.accountsSid = accountsSid;
    }

    private String accountsSid;

    @Override
    public String toString() {
        return "MultiChannelAutoMessage { " +
                "id=" + id +
                ", customerId='" + customerId + '\'' +
                ", cardId='" + cardId + '\'' +
                ", accountsSid='" + accountsSid + '\'' +
                '}';
    }
}

