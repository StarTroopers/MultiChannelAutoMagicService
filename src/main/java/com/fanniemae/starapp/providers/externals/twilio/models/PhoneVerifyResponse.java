package com.fanniemae.starapp.providers.externals.twilio.models;

import java.util.Date;

public class PhoneVerifyResponse {

    private String sid;
    private String serviceSid;
    private String accountSid;
    private String toNumber;
    private PhoneVerifyChannelType channelType;
    private String status;
    private Date createDate;
    private Date updateDate;

    private String carrierName;
    private String carrierErrorCode;
    private String mobileCountryCode;
    private String mobileNetworkCode;
    private String type;


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getServiceSid() {
        return serviceSid;
    }

    public void setServiceSid(String serviceSid) {
        this.serviceSid = serviceSid;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getToNumber() {
        return toNumber;
    }

    public void setToNumber(String toNumber) {
        this.toNumber = toNumber;
    }

    public PhoneVerifyChannelType getChannelType() {
        return channelType;
    }

    public void setChannelType(PhoneVerifyChannelType channelType) {
        this.channelType = channelType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public String getCarrierErrorCode() {
        return carrierErrorCode;
    }

    public void setCarrierErrorCode(String carrierErrorCode) {
        this.carrierErrorCode = carrierErrorCode;
    }

    public String getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(String mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
    }

    public String getMobileNetworkCode() {
        return mobileNetworkCode;
    }

    public void setMobileNetworkCode(String mobileNetworkCode) {
        this.mobileNetworkCode = mobileNetworkCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PhoneVerifyResponse{");
        sb.append("sid='").append(sid).append('\'');
        sb.append(", serviceSid='").append(serviceSid).append('\'');
        sb.append(", accountSid='").append(accountSid).append('\'');
        sb.append(", toNumber='").append(toNumber).append('\'');
        sb.append(", channelType=").append(channelType);
        sb.append(", status='").append(status).append('\'');
        sb.append(", createDate=").append(createDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", carrierName='").append(carrierName).append('\'');
        sb.append(", carrierErrorCode='").append(carrierErrorCode).append('\'');
        sb.append(", mobileCountryCode='").append(mobileCountryCode).append('\'');
        sb.append(", mobileNetworkCode='").append(mobileNetworkCode).append('\'');
        sb.append(", type='").append(type).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
