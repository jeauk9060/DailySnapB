package com.dailysnap.summation.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ArticleNewsVO {
    private int id;
    private String title;
    private String sourceName;
    private String sourceIcon;
    private String link;
    private String summary;
    private String thumbnail;
    private String thumbnailSmall;
    private LocalDateTime publishedDate;
    private LocalDateTime createdAt;
}
