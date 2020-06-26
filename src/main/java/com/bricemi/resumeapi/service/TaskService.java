package com.bricemi.resumeapi.service;

import com.bricemi.resumeapi.model.Task;
import com.couchbase.client.java.Bucket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    private static final String IDREF = "task::";
    private final Bucket bucket;

    @Autowired
    public TaskService(Bucket bucket){
        this.bucket = bucket;
    }

    /**
     * Create single task
     */
    public Task createTask(String title, String desc){

        Task newTask = new Task(title,desc);
        bucket.defaultCollection().insert(IDREF + title.toUpperCase().replaceAll(" ","_"),newTask);
        LOGGER.info("Create tasks :" + newTask.toString());

        return newTask;
    }

}
