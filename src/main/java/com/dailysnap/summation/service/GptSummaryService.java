package com.dailysnap.summation.service;

import com.dailysnap.summation.dao.ArticleNewsDAO;
import com.dailysnap.summation.dao.StoryNewsDAO;
import com.dailysnap.summation.model.ArticleNewsVO;
import com.dailysnap.summation.model.StoryNewsVO;

import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GptSummaryService {

    private final ArticleNewsDAO articleNewsDAO;
    private final StoryNewsDAO storyNewsDAO;
    private final RestTemplate restTemplate;

    @Value("${openai.api.url}")
    private String apiUrl;

    @Value("${openai.api.key}")
    private String apiKey;

    // 요약 처리
    public void summarizeArticlesAndStories() {
        LocalDate today = LocalDate.now();

        // 1. Article 요약
        List<ArticleNewsVO> articles = articleNewsDAO.findArticlesByDate(today);
        for (ArticleNewsVO article : articles) {
            try {
                String summary = callOpenAiApi(article.getLink());
                article.setSummary(summary);
                articleNewsDAO.updateArticleSummary(article);
                System.out.println("Article 요약 저장 성공: " + article.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Article 요약 실패: " + article.getTitle());
            }
        }

        // 2. Story 요약
        List<StoryNewsVO> stories = storyNewsDAO.findStoriesByDate(today);
        for (StoryNewsVO story : stories) {
            try {
                String summary = callOpenAiApi(story.getLink());
                story.setSummary(summary);
                storyNewsDAO.updateStorySummary(story);
                System.out.println("Story 요약 저장 성공: " + story.getTitle());
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Story 요약 실패: " + story.getTitle());
            }
        }
    }

    // GPT API 호출
    private String callOpenAiApi(String link) {
        try {
            JSONObject request = new JSONObject();
            request.put("model", "gpt-3.5-turbo");
            request.put("messages", new JSONArray()
                    .put(new JSONObject().put("role", "system").put("content", "You are a helpful assistant."))
                    .put(new JSONObject().put("role", "user").put("content", "Summarize the main points of the news article found at this URL: " + link))
            );
            request.put("max_tokens", 150);
            request.put("temperature", 0.7);

            HttpEntity<String> entity = new HttpEntity<>(request.toString(), createHeaders());
            String response = restTemplate.postForObject(apiUrl, entity, String.class);

            JSONObject jsonResponse = new JSONObject(response);
            return jsonResponse.getJSONArray("choices")
                               .getJSONObject(0)
                               .getJSONObject("message")
                               .getString("content")
                               .trim();
        } catch (Exception e) {
            e.printStackTrace();
            return "요약 실패";
        }
    }

    // HTTP 헤더 생성
    private HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + apiKey);
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
