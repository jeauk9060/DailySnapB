package com.dailysnap.summation.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.dailysnap.summation.service.GptSummaryService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GptScheduler {

    private final GptSummaryService gptSummaryService;


    @Scheduled(cron = "0 0 8 * * ?") // 매일 오전 8시에 실행
    public void scheduleSummarizeArticles() {
        gptSummaryService.summarizeArticles();
    }
}
