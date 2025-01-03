package com.dailysnap.summation.dao;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dailysnap.summation.model.StoryNewsVO;

@Repository
public interface StoryNewsDAO {

    // 스토리 뉴스 삽입
    void insertStory(StoryNewsVO story);

    // 링크로 중복 확인
    int checkDuplicateByLink(@Param("link") String link);
    
    // 날짜별 Story 조회
    List<StoryNewsVO> findStoriesByDate(@Param("date") LocalDate date);

    // Story 요약 업데이트
    void updateStorySummary(StoryNewsVO story);
}
