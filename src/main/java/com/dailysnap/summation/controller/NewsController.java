package com.dailysnap.summation.controller;



import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dailysnap.summation.service.EmailService;
import com.dailysnap.summation.service.NewsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class NewsController {

    private final NewsService newsService;
    private final EmailService emailService;

    // 이메일 전송 API
    @PostMapping("/send-email")
    public String sendEmail(@RequestParam("email") String email) {
        try {
            // EmailService의 메서드 활용
            return emailService.sendTodayNewsEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
            return "오류 발생: " + e.getMessage();
        } 
    }
    
 // 수동 뉴스 저장 테스트
    @PostMapping("/fetch")
    public String fetchNewsManually() {
        try {
            newsService.fetchAndSaveNews();
            return "News fetched and saved successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
    
}
