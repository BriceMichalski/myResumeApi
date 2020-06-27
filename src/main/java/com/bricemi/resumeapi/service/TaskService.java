package com.bricemi.resumeapi.service;

import com.bricemi.resumeapi.model.Task;
import com.bricemi.resumeapi.util.Mapper;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.query.QueryResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.couchbase.client.java.query.QueryOptions.queryOptions;

@Service
public class TaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);
    private final Bucket bucket;
    private final Cluster cluster;

    @Autowired
    public TaskService(Bucket bucket,Cluster cluster){
        this.bucket = bucket;
        this.cluster = cluster;
    }

    /**
     * Create single task
     */
    public Task createTask(String title, String desc){

        Task newTask = new Task(title,desc);
        bucket.defaultCollection().insert(newTask.generateIdRef(title),newTask);
        LOGGER.info("Create tasks :" + newTask.toString());

        return newTask;
    }

    /**
     * Get all tasks
     * Need index : CREATE INDEX `typeIdx` ON `myresume-data`(`type`)
     */
    public List<Task> getTasks(){
        QueryResult result = cluster.query("select title,type,description from `myresume-data` where type = $type ",
            queryOptions().parameters(
                JsonObject.create().put("type","TASK")
            ));

        return result.rowsAsObject()
            .stream()
            .map(r -> Mapper.convert(r.toString(),Task.class))
            .collect(Collectors.toList());
    }
}
