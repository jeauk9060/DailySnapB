package com.summation.dailysnap.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.summation.dailysnap.dao.MailDAO;
import com.summation.dailysnap.model.MailVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MailService {
	 private final MailDAO mailDAO;

	    // 전체 사용자 목록 조회
	    public List<MailVO> getMailList() {
	        return mailDAO.selectMailList();
	    }

	    // ID로 사용자 조회
	    public MailVO getMailById(Long id) {
	        return mailDAO.selectMailById(id);
	    }

	    // 사용자 저장
	    public void saveMail(MailVO mailVO) {
	        mailDAO.insertMail(mailVO);
	    }

	    // 사용자 정보 업데이트
	    public int updateMail(MailVO mailVO) {
	        return mailDAO.updateMail(mailVO);
	    }

	    // ID로 사용자 삭제
	    public int deleteMailById(Long id) {
	        return mailDAO.deleteMailById(id);
	    }
}
