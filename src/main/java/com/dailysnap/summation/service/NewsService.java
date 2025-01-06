package com.dailysnap.summation.service;

import com.dailysnap.summation.dao.ArticleNewsDAO;
import com.dailysnap.summation.dao.StoryNewsDAO;
import com.dailysnap.summation.model.ArticleNewsVO;
import com.dailysnap.summation.model.StoryNewsVO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class NewsService {

    private final ArticleNewsDAO articleNewsDAO;
    private final StoryNewsDAO storyNewsDAO;
    private final RestTemplate restTemplate;

    private static final String API_URL = "https://serpapi.com/search.json?engine=google_news&gl=kr&hl=ko&api_key=7b7bf15f9af20581bde40adf0b1407d9dba49dfe0e9db00eaf71fc28099f153d";

    public NewsService(ArticleNewsDAO articleNewsDAO, StoryNewsDAO storyNewsDAO, RestTemplate restTemplate) {
        this.articleNewsDAO = articleNewsDAO;
        this.storyNewsDAO = storyNewsDAO;
        this.restTemplate = restTemplate;
    }

    // API 호출 및 데이터 저장
    public void fetchAndSaveNews() {
        // 1. API 호출
        String jsonResponse = restTemplate.getForObject(API_URL, String.class);

        // 2. JSON 파싱
        JSONObject jsonObject = new JSONObject(jsonResponse);
        JSONArray newsResults = jsonObject.getJSONArray("news_results");

        for (int i = 0; i < newsResults.length(); i++) {
            JSONObject news = newsResults.getJSONObject(i);

            // **Article 저장**
            ArticleNewsVO article = new ArticleNewsVO();

            if (news.has("highlight")) {
                JSONObject highlight = news.getJSONObject("highlight");
                article.setTitle(highlight.getString("title"));
                article.setSourceName(highlight.getJSONObject("source").getString("name"));
                article.setSourceIcon(highlight.getJSONObject("source").getString("icon"));
                article.setLink(highlight.getString("link"));
                article.setThumbnail(highlight.getString("thumbnail"));
                article.setThumbnailSmall(highlight.getString("thumbnail_small"));

                // 날짜 변환
                String dateStr = highlight.getString("date");
                article.setPublishedDate(parseDateTime(dateStr));
            } else {
                article.setTitle(news.getString("title"));
                article.setSourceName(news.getJSONObject("source").getString("name"));
                article.setSourceIcon(news.getJSONObject("source").getString("icon"));
                article.setLink(news.getString("link"));
                article.setThumbnail(news.getString("thumbnail"));
                article.setThumbnailSmall(news.getString("thumbnail_small"));

                // 날짜 변환
                String dateStr = news.getString("date");
                article.setPublishedDate(parseDateTime(dateStr));
            }

            article.setCreatedAt(LocalDateTime.now());

            // 중복 체크 후 저장
            try {
                int isDuplicate = articleNewsDAO.checkDuplicateByLink(article.getLink());
                if (isDuplicate == 0) {
                    articleNewsDAO.insertArticle(article);
                } else {
                    System.out.println("중복 데이터: " + article.getLink());
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("데이터 저장 실패: " + article.getLink());
            }

            // **Story 저장**
            if (news.has("stories")) {
                JSONArray storiesArray = news.getJSONArray("stories");
                saveStories(storiesArray, article.getId());
            }
        }
    }

    // Story 저장
    private void saveStories(JSONArray storiesArray, int articleId) {
        for (int i = 0; i < storiesArray.length(); i++) {
            JSONObject story = storiesArray.getJSONObject(i);

            StoryNewsVO storyVO = new StoryNewsVO();
            storyVO.setArticleId(articleId);
            storyVO.setTitle(story.getString("title"));
            storyVO.setSourceName(story.getJSONObject("source").getString("name"));
            storyVO.setSourceIcon(story.getJSONObject("source").getString("icon"));
            storyVO.setLink(story.getString("link"));
            storyVO.setThumbnail(story.getString("thumbnail"));
            storyVO.setThumbnailSmall(story.getString("thumbnail_small"));

            // 날짜 변환
            String dateStr = story.getString("date");
            storyVO.setPublishedDate(parseDateTime(dateStr));
            storyVO.setCreatedAt(LocalDateTime.now());

            // 중복 체크 후 저장
            if (storyNewsDAO.checkDuplicateByLink(storyVO.getLink()) == 0) {
                storyNewsDAO.insertStory(storyVO);
            }
        }
    }

    // 날짜 변환 메서드
    private LocalDateTime parseDateTime(String dateStr) {
        try {
            // 날짜 부분만 추출
            String cleanedDateStr = dateStr.substring(0, 10);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate date = LocalDate.parse(cleanedDateStr, formatter);
            return date.atStartOfDay();
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            throw new RuntimeException("날짜 변환 실패: " + dateStr);
        }
    }
    
    public List<ArticleNewsVO> getArticlesByDate(String date) {
        return articleNewsDAO.findArticlesByDate(LocalDate.parse(date));
    }
}