package com.fanniemae.starapp.controllers.request;

public class ZapTwitterBean {

    private String contributors;
    private boolean truncated;
    private String text;
    private boolean isQuoteStatus;
    private String favoriteCount;
    private String id;
    private boolean retweeted;
    private boolean favorited;
    private ZapTwitterUserBean user;

    public String getContributors() {
        return contributors;
    }

    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    public boolean isTruncated() {
        return truncated;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isQuoteStatus() {
        return isQuoteStatus;
    }

    public void setQuoteStatus(boolean quoteStatus) {
        isQuoteStatus = quoteStatus;
    }

    public String getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(String favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isRetweeted() {
        return retweeted;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public boolean isFavorited() {
        return favorited;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public ZapTwitterUserBean getUser() {
        return user;
    }

    public void setUser(ZapTwitterUserBean user) {
        this.user = user;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ZapTwitterBean{");
        sb.append("contributors='").append(contributors).append('\'');
        sb.append(", truncated=").append(truncated);
        sb.append(", text='").append(text).append('\'');
        sb.append(", isQuoteStatus=").append(isQuoteStatus);
        sb.append(", favoriteCount='").append(favoriteCount).append('\'');
        sb.append(", id='").append(id).append('\'');
        sb.append(", retweeted=").append(retweeted);
        sb.append(", favorited=").append(favorited);
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }
}
