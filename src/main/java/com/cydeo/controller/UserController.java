package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
@Tag(name = "User", description = "User APIs")
public class UserController {

    private final UserService userService;


    @GetMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Get Users ")
    public ResponseEntity<ResponseWrapper> getUsers(){
        return ResponseEntity
                .ok(new ResponseWrapper(userService.listAllUsers(), "Successfully retrieved users.", HttpStatus.OK));
    }


    @GetMapping("/{username}")
    @RolesAllowed("Admin")
    @Operation(summary = "Get User by username")
    public ResponseEntity<ResponseWrapper> getUser(@PathVariable("username") String username){
        return ResponseEntity
                .ok(new ResponseWrapper(getUser(username), "User successfully retrieved.", HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Create User")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Successfully created a new user.", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Admin")
    @Operation(summary = "Update User")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return ResponseEntity.ok(new ResponseWrapper("Successfully updated the user.", HttpStatus.OK));
    }

    @DeleteMapping("/{username}")
    @RolesAllowed("Admin")
    @Operation(summary = "Delete User")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String username){
        userService.deleteByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("Successfully deleted the user.", HttpStatus.OK));

        //204 - HttpStatus.NO_CONTENT

//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT).build();
    }






















}
