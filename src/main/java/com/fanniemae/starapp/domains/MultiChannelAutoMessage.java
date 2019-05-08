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
    private String channelType;
    private String screenName;
    private String contact;
    private String lastName;
    private String firstName;

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

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.contact = lastName;
    }

    public void setFirstName(String firstName) {
        this.contact = firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MultiChannelAutoMessage{");
        sb.append("id=").append(id);
        sb.append(", customerId='").append(customerId).append('\'');
        sb.append(", cardId='").append(cardId).append('\'');
        sb.append(", channelType='").append(channelType).append('\'');
        sb.append(", screenName='").append(screenName).append('\'');
        sb.append(", contact='").append(contact).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", accountsSid='").append(accountsSid).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

