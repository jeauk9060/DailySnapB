package com.summation.dailysnap.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.summation.dailysnap.model.MailVO;

@Repository
public interface MailDAO {
	 // 사용자 리스트 조회
    List<MailVO> selectMailList();

    // ID로 사용자 조회
    MailVO selectMailById(Long id);

    // 사용자 저장
    int insertMail(MailVO mailVO);

    // 사용자 정보 업데이트
    int updateMail(MailVO mailVO);

    // ID로 사용자 삭제
    int deleteMailById(Long id);
}
