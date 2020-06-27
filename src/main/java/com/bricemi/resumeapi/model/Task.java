package com.bricemi.resumeapi.model;

import lombok.Data;

@Data
public class Task extends AbstractModel {

    private String title;
    private String description;

    public Task(){}

    public Task(String title, String description) {
        super();
        this.title = title;
        this.description = description;
    }
}
