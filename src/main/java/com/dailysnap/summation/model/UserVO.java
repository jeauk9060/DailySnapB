package com.dailysnap.summation.model;

import lombok.Data;

@Data
public class UserVO {
	private int userId; // 사용자 ID
	private String email; // 이메일
	private boolean privacyConsent; // 개인정보 동의 여부
	private String consentDate; // 동의 날짜
	private int subscriptionStatus; // 구독 상태
	private String signupDate; // 가입 일자
	private String lastUpdated; // 최근 업데이트 시간
}
