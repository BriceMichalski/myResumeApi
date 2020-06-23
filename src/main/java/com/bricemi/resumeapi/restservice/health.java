package com.bricemi.resumeapi.restservice;


public class health {

    private final long id;
    private final String content;

    public health(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
