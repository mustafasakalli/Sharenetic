package com.example.sharenetic;

import java.sql.Timestamp;

public class Post {

    private String sClass;
    private String post;
    private String user_id;
    private Timestamp timestamp;

    public Post(String sClass, String post, String user_id, Timestamp timestamp) {
        this.sClass = sClass;
        this.post = post;
        this.user_id = user_id;
        this.timestamp = timestamp;
    }

    public Post() {}

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
