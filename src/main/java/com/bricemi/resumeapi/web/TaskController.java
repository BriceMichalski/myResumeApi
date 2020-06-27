package com.bricemi.resumeapi.web;

import com.bricemi.resumeapi.model.Task;
import com.bricemi.resumeapi.service.TaskService;
import com.couchbase.client.java.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService){
        this.taskService=taskService;
    }

    @RequestMapping(method=RequestMethod.POST)
    public ResponseEntity createTask(@RequestBody String json){
        JsonObject jsonData = JsonObject.fromJson(json);
        try{
            Task result = taskService.createTask(jsonData.getString("title"),jsonData.getString("description"));
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(result);
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new Error(e.getMessage()));
        }
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity getTasks(){
        try{
            // List<Task> tasks = taskService.getTasks();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(taskService.getTasks());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new Error(e.getMessage()));
        }
    }

}
