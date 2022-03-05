package com.airpollution.userservice.controller;

import com.airpollution.userservice.exception.UserAlreadyExistException;
import com.airpollution.userservice.service.UserService;
import com.airpollution.userservice.userdto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;

/**
 * RestController annotation is used to create Restful web services using Spring MVC
 */
@RestController
/**
 * RequestMapping annotation maps HTTP requests to handler methods
 */
@RequestMapping("/userservice/api/v1")
public class UserController {
    @Autowired
    private UserService userService;

    ResponseEntity<?> responseEntity;

    /**
     * To get the property values
     */
    @Value("${app.controller.exception.message1}")
    private String message1;

    @Value("${app.controller.exception.message2}")
    private String message2;

    @Value("${app.controller.exception.message3}")
    private String message3;


    /**
     * API Version: 1.0
     * Method to register a new user by reading the Serialized
     * User object from request body and save the user in database.
     * <p>
     * This handler method should return any one of the status messages basis on
     * different situations:
     * 1. 201(CREATED - In case of successful creation of the user
     * 2. 409(CONFLICT) - In case of duplicate id
     * <p>
     * This handler method should map to the URL "/api/v1/user" using HTTP POST
     * method".
     */
    @PostMapping("/user")
    public ResponseEntity<?> registerUser(@RequestBody UserDto user)
    {
        System.out.println(user.toString());
        try {
            UserDto savedUser = userService.saveUser(user);
            responseEntity = new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (UserAlreadyExistException e) {
            responseEntity = new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

        userService.initiateFavoriteList(user.getId());
        return responseEntity;
    }

    /* API Version: 1.0
     * Method to authenticate a user by reading the Serialized user
     * object from request body containing the id and password. The id and password should be validated
     * before proceeding ahead with JWT token generation. The user credentials will be validated against the database entries.
     * The exception will be thrown if validation is not successful. If credentials are validated successfully, then JWT
     * token will be generated.
     * This handler method should map to the URL "/api/v1/login/user" using HTTP POST
     * method.
     */

    @PostMapping("/login/user")
    public ResponseEntity<?> loginUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getUser")
    public ResponseEntity<?> getUser(){
        System.out.println("I am in get method");
        return new ResponseEntity<>("I am in Get method",HttpStatus.OK);
    }
    @GetMapping("/userExists")
    public ResponseEntity<Boolean> userExists(@RequestParam String email)
    {
        System.out.println(email);
        return new ResponseEntity<Boolean>(userService.userExist(email),HttpStatus.OK);
    }

    @PostMapping("/profilePic/add")
    public ResponseEntity<?> addProfilePic(@RequestAttribute String userEmail, @RequestParam("file") MultipartFile file) {
        System.out.println("adding image for " + userEmail);

        byte[] image = new byte[0];

        try {
            image = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userService.addOrUpdateProfilePic(userEmail, image)) {
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/profilePic/update")
    public ResponseEntity<?> updateProfilePic(@RequestAttribute String userEmail, @RequestParam("file") MultipartFile file) {
        byte[] image = new byte[0];

        try {
            image = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (userService.addOrUpdateProfilePic(userEmail, image)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/profilePic/get")
    public ResponseEntity<?> getProfilePic(@RequestAttribute String userEmail) {
        byte[] image = userService.getProfilePic(userEmail);

        System.out.println("getting the byte array: " + Arrays.toString(image));

        if (image != null) {
            System.out.println("sending image over!");
            return new ResponseEntity<>(image, HttpStatus.OK);
        }
        System.out.println("image was evaluated null");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
