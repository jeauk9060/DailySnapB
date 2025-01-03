package com.dailysnap.summation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.dailysnap.summation.model.UserVO;
import com.dailysnap.summation.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserController {

    private final UserService userService;

    @PostMapping("/userinfo")
    public String registerUser(@RequestBody UserVO user) {
        userService.registerUser(user);
        return "User registered successfully!";
    }

    @GetMapping("/{email}")
    public UserVO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
    
    
    
}
