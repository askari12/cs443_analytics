package com.linkly.analytics.JsonObj;

public class ShortUrlJSON {

    private String shortURL;

    public ShortUrlJSON(String shortUrl ){
        this.shortURL = shortUrl;
    }

    public ShortUrlJSON() {}

    public String getShorUrl() { return  this.shortURL; }


    public void setShortURL(String shortURL) {this.shortURL = shortURL; }

}
