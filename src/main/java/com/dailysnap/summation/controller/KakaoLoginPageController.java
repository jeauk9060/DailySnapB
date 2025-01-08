package com.dailysnap.summation.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // JSON 반환을 위해 변경
@RequestMapping("/login")
public class KakaoLoginPageController {

    @Value("${kakao.client-id}")
    private String clientId; // 변수 이름 통일

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    @GetMapping("/kakao")
    public Map<String, String> getKakaoLoginUrl() {
        String location = "https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=" 
                           + clientId + "&redirect_uri=" + redirectUri;
    
        
        Map<String, String> response = new HashMap<>();
        response.put("location", location);
        return response; // JSON 반환
    }
}

