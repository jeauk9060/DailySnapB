package com.dailysnap.summation.controller;

import com.dailysnap.summation.service.OpenAIService;

import lombok.RequiredArgsConstructor;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api")
public class SummarizeController {

    private final OpenAIService openAIService;


    // 전체 요약 실행
    @PostMapping("/all")
    public String summarizeAll() {
        try {
            openAIService.summarizeAndSaveArticles();
            openAIService.summarizeAndSaveStories();
            return "All articles and stories summarized successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to summarize all content.";
        }
    }
}

