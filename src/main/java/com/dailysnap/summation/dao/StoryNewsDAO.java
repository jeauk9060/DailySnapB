package com.dailysnap.summation.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dailysnap.summation.model.StoryNewsVO;

@Repository
public interface StoryNewsDAO {

    // 스토리 뉴스 삽입
    void insertStory(StoryNewsVO story);

    // 링크로 중복 확인
    int checkDuplicateByLink(@Param("link") String link);
}
