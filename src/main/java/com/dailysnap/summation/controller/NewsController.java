package com.dailysnap.summation.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dailysnap.summation.model.ArticleNewsVO;
import com.dailysnap.summation.model.StoryNewsVO;
import com.dailysnap.summation.service.NewsService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class NewsController {

    private final NewsService newsService;
    

    
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
