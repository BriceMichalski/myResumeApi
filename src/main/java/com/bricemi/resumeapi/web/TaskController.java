package com.bricemi.resumeapi.web;

import com.bricemi.resumeapi.model.AbstractModel;
import com.bricemi.resumeapi.model.Task;
import com.bricemi.resumeapi.service.TaskService;
import com.bricemi.resumeapi.util.Mapper;
import com.couchbase.client.core.error.DocumentExistsException;
import com.couchbase.client.core.error.DocumentNotFoundException;
import com.couchbase.client.java.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        try{
            Task newTask = Mapper.convert(json,Task.class);
            taskService.saveTask(newTask);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(newTask);
        }
        catch (DocumentExistsException e){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(JsonObject.create().put("info", e.getMessage()).toString());
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
            return ResponseEntity.status(HttpStatus.OK)
                    .body(taskService.getTasks());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500)
                    .body(new Error(e.getMessage()));
        }
    }

    @RequestMapping(path = "/{title}", method = RequestMethod.GET)
    public ResponseEntity getTask(@PathVariable String title){
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(taskService.getTask(title));
        }
        catch( DocumentNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(JsonObject.create().put("info", e.getMessage()).toString());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Error(e.getMessage()));
        }
    }

    @RequestMapping(path = "/{title}", method = RequestMethod.DELETE)
    public ResponseEntity deleteTask(@PathVariable String title){
        try {
            taskService.removeTask(title);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(JsonObject.create().put("info", title + " Task successfully removed").toString());
        }
        catch( DocumentNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(JsonObject.create().put("info", e.getMessage()).toString());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Error(e.getMessage()));
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity updateTask(@RequestBody String json){
        try {
            Task task = Mapper.convert(json,Task.class);
            taskService.updateTask(task);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(task);
        }
        catch( DocumentNotFoundException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(JsonObject.create().put("info", e.getMessage()).toString());
        }
        catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new Error(e.getMessage()));
        }
    }
}
