package com.dailysnap.summation.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.dailysnap.summation.dto.UserDTO;
import com.dailysnap.summation.model.UserVO;
import com.dailysnap.summation.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin
@Slf4j
public class UserController {

    private final UserService userService;
    
    @PostMapping("/userinfo")
    public String registerUser(@RequestBody UserDTO userDTO) {
        try {
            userService.registerUser(userDTO);
            return "사용자 등록 성공!";
        } catch (Exception e) {
            return "사용자 등록 실패: " + e.getMessage();
        }
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
