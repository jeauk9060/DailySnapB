package com.dailysnap.summation.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserVO {
    private int userId; // DB 컬럼명과 일치
    private String email;
    private boolean privacyConsent; // boolean 타입 유지
    private String consentDate;    // 문자열로 ISO 포맷 전달
    private int subscriptionStatus;
    private String signupDate;     // ISO 형식 날짜 문자열
    private String lastUpdated;    // ISO 형식 날짜 문자열
	
}
