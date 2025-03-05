package com.cydeo.controller;

import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.TaskDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/task")
@AllArgsConstructor
@Tag(name = "Task", description = "Task APIs")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Get all tasks")
    public ResponseEntity<ResponseWrapper> getTasks(){
        return ResponseEntity
                .ok(new ResponseWrapper(taskService.listAllTasks(), "Successfully retrieved tasks.", HttpStatus.OK));
    }

    @GetMapping("/{taskId}")
    @RolesAllowed("Manager")
    @Operation(summary = "Get task by Id")
    public ResponseEntity<ResponseWrapper> getTaskById(@PathVariable("taskId") Long taskId){
        return ResponseEntity
                .ok(new ResponseWrapper(taskService.findById(taskId), "Successfully retrieved the task.", HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Create a task")
    public ResponseEntity<ResponseWrapper> createTask(@RequestBody TaskDTO taskDTO){
        taskService.save(taskDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Successfully created the task.", HttpStatus.CREATED));
    }

    @DeleteMapping("/{taskId}")
    @RolesAllowed("Manager")
    @Operation(summary = "Delete task by Id")
    public ResponseEntity<ResponseWrapper> deleteTaskById(@PathVariable("taskId") Long taskId){
        taskService.delete(taskId);
        return ResponseEntity
                .ok(new ResponseWrapper("Deleted the task successfully.", HttpStatus.OK));
    }

    @PutMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Update a task")
    public ResponseEntity<ResponseWrapper> updateTask(@RequestBody TaskDTO taskDTO){
        taskService.update(taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper("Successfully updated the task.", HttpStatus.OK));
    }

    @GetMapping("/employee/pending-tasks")
    @RolesAllowed("Employee")
    @Operation(summary = "Get pending tasks")
    public ResponseEntity<ResponseWrapper> getPendingTasksByEmployee(){
        return ResponseEntity
                .ok(new ResponseWrapper(taskService.listAllTasksByStatusIsNot(Status.COMPLETE), "Successfully retrieved pending tasks.", HttpStatus.OK));
    }

    @PutMapping("/employee/update")
    @RolesAllowed("Employee")
    @Operation(summary = "Update task status")
    public ResponseEntity<ResponseWrapper> updateEmployeeTask(@RequestBody TaskDTO taskDTO){
        taskService.updateStatus(taskDTO);
        return ResponseEntity
                .ok(new ResponseWrapper("Task is updated successfully", HttpStatus.OK));
    }

    @GetMapping("/employee/archive")
    @RolesAllowed("Employee")
    @Operation(summary = "Get tasks in archive")
    public ResponseEntity<ResponseWrapper> getEmployeeArchivedTasks(){
        return ResponseEntity
                .ok(new ResponseWrapper(taskService.listAllTasksByStatus(Status.COMPLETE), "Successfully retrieved archived tasks", HttpStatus.OK));
    }









}
