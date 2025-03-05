package com.cydeo.controller;

import com.cydeo.dto.UserDTO;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    @GetMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUsers(){
        return ResponseEntity
                .ok(new ResponseWrapper(userService.listAllUsers(), "Successfully retrieved users.", HttpStatus.OK));
    }


    @GetMapping("/{username}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> getUser(@PathVariable("username") String username){
        return ResponseEntity
                .ok(new ResponseWrapper(getUser(username), "User successfully retrieved.", HttpStatus.OK));
    }

    @PostMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO userDTO){
        userService.save(userDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("Successfully created a new user.", HttpStatus.CREATED));
    }

    @PutMapping
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO userDTO){
        userService.update(userDTO);
        return ResponseEntity.ok(new ResponseWrapper("Successfully updated the user.", HttpStatus.OK));
    }

    @DeleteMapping("/{username}")
    @RolesAllowed("Admin")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String username){
        userService.deleteByUserName(username);
        return ResponseEntity.ok(new ResponseWrapper("Successfully deleted the user.", HttpStatus.OK));

        //204 - HttpStatus.NO_CONTENT

//        return ResponseEntity
//                .status(HttpStatus.NO_CONTENT).build();
    }






















}
