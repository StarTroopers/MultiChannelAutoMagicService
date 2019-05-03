package com.fanniemae.starapp.providers.externals.twilio.models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SMSMessageRequest extends SMSMessage{

    @Id
    private String accountSid;
    private String apiVersion;

    private String fromCity;
    private String fromCountry;
    private String fromState;
    private String fromZip;
    private String messageSid;
    private int numMedia;
    private int numSegments;
    private String smsMessageSid;
    private String smsSid;
    private String smsStatus;
    private String channel;

    private String toCity;
    private String toCountry;
    private String toState;
    private String toZip;

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }


    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getFromCountry() {
        return fromCountry;
    }

    public void setFromCountry(String fromCountry) {
        this.fromCountry = fromCountry;
    }

    public String getFromState() {
        return fromState;
    }

    public void setFromState(String fromState) {
        this.fromState = fromState;
    }

    public String getFromZip() {
        return fromZip;
    }

    public void setFromZip(String fromZip) {
        this.fromZip = fromZip;
    }

    public String getMessageSid() {
        return messageSid;
    }

    public void setMessageSid(String messageSid) {
        this.messageSid = messageSid;
    }

    public int getNumMedia() {
        return numMedia;
    }

    public void setNumMedia(int numMedia) {
        this.numMedia = numMedia;
    }

    public int getNumSegments() {
        return numSegments;
    }

    public void setNumSegments(int numSegments) {
        this.numSegments = numSegments;
    }

    public String getSmsMessageSid() {
        return smsMessageSid;
    }

    public void setSmsMessageSid(String smsMessageSid) {
        this.smsMessageSid = smsMessageSid;
    }

    public String getSmsSid() {
        return smsSid;
    }

    public void setSmsSid(String smsSid) {
        this.smsSid = smsSid;
    }

    public String getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(String smsStatus) {
        this.smsStatus = smsStatus;
    }


    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public String getToCountry() {
        return toCountry;
    }

    public void setToCountry(String toCountry) {
        this.toCountry = toCountry;
    }

    public String getToState() {
        return toState;
    }

    public void setToState(String toState) {
        this.toState = toState;
    }

    public String getToZip() {
        return toZip;
    }

    public void setToZip(String toZip) {
        this.toZip = toZip;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SMSMessageRequest{");
        sb.append("accountSid='").append(accountSid).append('\'');
        sb.append(", apiVersion='").append(apiVersion).append('\'');
        sb.append(", fromCity='").append(fromCity).append('\'');
        sb.append(", fromCountry='").append(fromCountry).append('\'');
        sb.append(", fromState='").append(fromState).append('\'');
        sb.append(", fromZip='").append(fromZip).append('\'');
        sb.append(", messageSid='").append(messageSid).append('\'');
        sb.append(", numMedia=").append(numMedia);
        sb.append(", numSegments=").append(numSegments);
        sb.append(", smsMessageSid='").append(smsMessageSid).append('\'');
        sb.append(", smsSid='").append(smsSid).append('\'');
        sb.append(", smsStatus='").append(smsStatus).append('\'');
        sb.append(", channel='").append(channel).append('\'');
        sb.append(", toCity='").append(toCity).append('\'');
        sb.append(", toCountry='").append(toCountry).append('\'');
        sb.append(", toState='").append(toState).append('\'');
        sb.append(", toZip='").append(toZip).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
