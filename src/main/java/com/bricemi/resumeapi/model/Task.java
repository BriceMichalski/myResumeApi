package com.bricemi.resumeapi.model;

import lombok.Data;
import com.couchbase.client.java.json.JsonObject;

@Data
public class Task {

    private String title;
    private String description;

    public Task(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public JsonObject toJSON(){
        return JsonObject.create()
                .put("type","task")
                .put("title", title)
                .put("description",description);
    }
}
