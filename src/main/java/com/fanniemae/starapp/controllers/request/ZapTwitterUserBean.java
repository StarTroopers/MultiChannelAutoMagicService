package com.fanniemae.starapp.controllers.request;

public class ZapTwitterUserBean {

    private String id;
    private boolean verified;
    private String screenName;
    private String followersCount;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(String followersCount) {
        this.followersCount = followersCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("user {");
        sb.append("id='").append(id).append('\'');
        sb.append(", verified=").append(verified);
        sb.append(", screenName='").append(screenName).append('\'');
        sb.append(", followersCount='").append(followersCount).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
