package com.dailysnap.summation.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dailysnap.summation.model.ArticleNewsVO;

@Repository
public interface ArticleNewsDAO {
    // 뉴스 삽입
    void insertArticle(ArticleNewsVO article);

    // 링크로 중복 확인
    int checkDuplicateByLink(@Param("link") String link);
}
