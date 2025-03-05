package com.cydeo.controller;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;


@RestController
@RequestMapping("/api/v1/project")
@AllArgsConstructor
@Tag(name = "Project", description = "Project APIs")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    @RolesAllowed({"Manager", "Admin"})
    @Operation(summary = "Get all users")
    public ResponseEntity<ResponseWrapper> getAllProjects(){
        return ResponseEntity
                .ok(new ResponseWrapper(projectService.listAllProjects(),"Successfully retrieved projects.", HttpStatus.OK ));
    }

    @GetMapping("/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "Get project by project code")
    public ResponseEntity<ResponseWrapper> getProjectByCode(@PathVariable("projectCode") String projectCode){
        return ResponseEntity
                .ok(new ResponseWrapper(projectService.getByProjectCode(projectCode), "Project is retrieved", HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Create a project")
    public ResponseEntity<ResponseWrapper> createProject(@RequestBody ProjectDTO projectDTO){
        projectService.save(projectDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Successfully created the project", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Manager")
    @Operation(summary = "Update a project")
    public ResponseEntity<ResponseWrapper> updateProject(@RequestBody ProjectDTO projectDTO){
        projectService.update(projectDTO);
        return ResponseEntity
                .ok(new ResponseWrapper(projectService.getByProjectCode(projectDTO.getProjectCode()), "Project Successfully updated.", HttpStatus.OK));
    }

    @PutMapping("/complete/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "Complete project")
    public ResponseEntity<ResponseWrapper> completeProject(@PathVariable("projectCode") String projectCode){
        projectService.complete(projectCode);
        return ResponseEntity
                .ok(new ResponseWrapper("Project is marked as completed now.", HttpStatus.OK));
    }

    @DeleteMapping("/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "Delete project")
    public ResponseEntity<ResponseWrapper> deleteByProjectCode(@PathVariable("projectCode") String projectCode){
        projectService.delete(projectCode);
        return ResponseEntity
                .ok(new ResponseWrapper("Project: "+ projectCode + " is deleted now.", HttpStatus.OK));
    }

    @GetMapping("/manager/project-status")
    @RolesAllowed("Manager")
    @Operation(summary = "Get projects by manager")
    public ResponseEntity<ResponseWrapper> getProjectByManager(){
        return ResponseEntity
                .ok(new ResponseWrapper(projectService.listAllProjectDetails(), "Successfully retrieved manager project details.", HttpStatus.OK));
    }

    @PutMapping("/manager/complete/{projectCode}")
    @RolesAllowed("Manager")
    @Operation(summary = "Complete the project - Manager")
    public ResponseEntity<ResponseWrapper> managerCompleteProject(@PathVariable("projectCode") String projectCode){
        projectService.complete(projectCode);
        return ResponseEntity
                .ok((new ResponseWrapper("Successfully completed the project.", HttpStatus.OK)));
    }







}
