package com.dailysnap.summation.dao;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dailysnap.summation.model.ArticleNewsVO;

@Repository
public interface ArticleNewsDAO {
    // 뉴스 삽입
    void insertArticle(ArticleNewsVO article);

    // 링크로 중복 확인
    int checkDuplicateByLink(@Param("link") String link);
    
    // 현재 날짜 기준 데이터 조회
    List<ArticleNewsVO> findArticlesByDate(@Param("date") LocalDate date);

    // 요약 업데이트
    void updateArticleSummary(ArticleNewsVO article);

}
