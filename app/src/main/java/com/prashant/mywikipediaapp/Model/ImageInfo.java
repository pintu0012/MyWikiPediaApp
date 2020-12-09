package com.prashant.mywikipediaapp.Model;

public class ImageInfo {

    private String timestamp;
    private String user;
    private String url;
    private String descriptionurl;
    private String descriptionshorturl;

    public ImageInfo(String timestamp, String user, String url, String descriptionurl, String descriptionshorturl) {
        this.timestamp = timestamp;
        this.user = user;
        this.url = url;
        this.descriptionurl = descriptionurl;
        this.descriptionshorturl = descriptionshorturl;
    }

    public ImageInfo() {

    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescriptionurl() {
        return descriptionurl;
    }

    public void setDescriptionurl(String descriptionurl) {
        this.descriptionurl = descriptionurl;
    }

    public String getDescriptionshorturl() {
        return descriptionshorturl;
    }

    public void setDescriptionshorturl(String descriptionshorturl) {
        this.descriptionshorturl = descriptionshorturl;
    }
}
