package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
@AllArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<ResponseWrapper> getTasks(){
        return ResponseEntity
                .ok(new ResponseWrapper(taskService.listAllTasks(), "Successfully retrieved tasks.", HttpStatus.OK));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long taskId){
        return ResponseEntity
                .ok(new ResponseWrapper(taskService.findById(taskId), "Successfully retrieved the task.", HttpStatus.OK));
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Successfully created the task.", HttpStatus.CREATED));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ResponseWrapper> deleteTaskById(@PathVariable("taskId") Long taskId){
        taskService.delete(taskId);
        return ResponseEntity
                .ok(new ResponseWrapper("Deleted the task successfully.", HttpStatus.OK));
    }

    @PutMapping
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper("Successfully updated the task.", HttpStatus.OK));
    }

    @GetMapping("/employee/pending-tasks")
    public ResponseEntity<ResponseWrapper> getPendingTasksByEmployee(){
        return ResponseEntity
                .ok(new ResponseWrapper(taskService.listAllTasksByStatusIsNot(Status.COMPLETE), "Successfully retrieved pending tasks.", HttpStatus.OK));
    }

    @PutMapping("/employee/update")
    public ResponseEntity<ResponseWrapper> updateEmployeeTask(@RequestBody TaskDTO taskDTO){
        taskService.updateStatus(taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper("Task is updated successfully", HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    public ResponseEntity<ResponseWrapper> getEmployeeArchivedTasks(){
        return ResponseEntity
                .ok(new ResponseWrapper(taskService.listAllTasksByStatus(Status.COMPLETE), "Successfully retrieved archived tasks", HttpStatus.OK));
    }









}
