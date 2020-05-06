package com.linkly.analytics.Collection;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "urlClicks")
public class UrlClicks {

    @Id
    private String id;
    private String shortURL;
    private String ipAddress;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timeStamp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime terminate_at;

    public UrlClicks(String shortURL , LocalDateTime timeStamp , LocalDateTime terminate_at , String ipAddress) {
        this.shortURL = shortURL;
        this.timeStamp = timeStamp;
        this.terminate_at = terminate_at;
        this.ipAddress = ipAddress;
    }


    public UrlClicks() {}

    public String getId() { return this.id; }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public String getShortURL() {
        return  this.shortURL;
    }

    public LocalDateTime getTerminate_at() { return this.terminate_at; }

    public String getIpAddress() {
        return ipAddress;
    }



    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setShortURL(String url) {
        this.shortURL = url;
    }

    public  void setTerminate_at(LocalDateTime time) {this.terminate_at = time; }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setId(String id) {
        this.id = id;
    }
}
