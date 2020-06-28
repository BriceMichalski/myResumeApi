package com.bricemi.resumeapi.service;

import com.bricemi.resumeapi.model.AbstractModel;
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
     * Save single task
     * @param newTask - String
     */
    public Task saveTask(Task newTask){
        bucket.defaultCollection().insert(newTask.generateIdRef(),newTask);
        return newTask;
    }

    /**
     * Get task
     * @param title - String - title of the task to get
     */
    public Task getTask(String title){
        return bucket.defaultCollection()
                .get(AbstractModel.generateIdRef(Task.class,title))
                .contentAs(Task.class);
    }

    /**
     * Get all tasks
     * Need index : CREATE INDEX `typeIdx` ON `myresume-data`(`type`)
     */
    public List<Task> getTasks(){
        QueryResult result = cluster.query("select title,type,description from `" + bucket.name() +"` where type = $type ",
            queryOptions().parameters(
                JsonObject.create()
                    .put("type","TASK")
        ));

        return result.rowsAsObject()
            .stream()
            .map(r -> Mapper.convert(r.toString(),Task.class))
            .collect(Collectors.toList());
    }

    /**
     * Remove singe task
     * @param title - String
     */
    public void removeTask(String title){
        bucket.defaultCollection().remove(AbstractModel.generateIdRef(Task.class,title));
    }

    /**
     * Update Task
     * @param task - Task
     */
    public void updateTask(Task task){
        bucket.defaultCollection().upsert(task.generateIdRef(),task);
    }
}
