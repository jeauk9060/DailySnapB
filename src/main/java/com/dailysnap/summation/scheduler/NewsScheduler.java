package com.dailysnap.summation.scheduler;

import java.time.LocalDateTime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dailysnap.summation.service.NewsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class NewsScheduler {

    private final NewsService newsService;



    // 매일 7시50분 실행 (cron: 초 분 시 일 월 요일)
    @Scheduled(cron = "0 50 9 * * ?")
    public void scheduleFetchNews() {
    	System.out.println("스케줄러 실행됨: " + LocalDateTime.now());
        newsService.fetchAndSaveNews();
    }
}
