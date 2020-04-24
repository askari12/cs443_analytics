package com.linkly.analytics.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "urlClicks")
public class UrlClicks {

    private String id;
    private String shortURL;

    private LocalDateTime timeStamp;
    private LocalDateTime terminate_at;

    public UrlClicks(String shortURL , LocalDateTime timeStamp , LocalDateTime terminate_at) {
        this.shortURL = shortURL;
        this.timeStamp = timeStamp;
        this.terminate_at = terminate_at;
    }

    public UrlClicks() {}

    private String getId() { return  this.id; }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public String getShortURL() {
        return  this.shortURL;
    }

    public LocalDateTime getTerminate_at() { return this.terminate_at; }


    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setShortURL(String url) {
        this.shortURL = url;
    }

    public  void setTerminate_at(LocalDateTime time) {this.terminate_at = time; }
}
