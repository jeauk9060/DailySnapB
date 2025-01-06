package com.dailysnap.summation.controller;

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

    // 사용자 등록
    @PostMapping("/userinfo")
    public String registerUser(@RequestBody UserVO user) {
        userService.registerUser(user);
        return "User registered successfully!";
    }

    // 이메일로 사용자 조회
    @GetMapping("/userinfo/{email}")
    public UserVO getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }

    // 이메일 중복 검사
    @GetMapping("/userinfo/exists/{email}")
    public boolean existsByEmail(@PathVariable String email) {
        return userService.existsByEmail(email);
    }

    // 구독 상태 업데이트
    @PutMapping("/userinfo/{email}/status")
    public String updateSubscriptionStatus(@PathVariable String email, @RequestParam int status) {
        userService.updateSubscriptionStatus(email, status);
        return "Subscription status updated successfully!";
    }
}
