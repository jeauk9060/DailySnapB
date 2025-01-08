package com.dailysnap.summation.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class KakaoUserDTO {

    private Long kakaoId;                // 카카오 고유 ID
    private String kakaoEmail;           // 카카오 이메일
    private String kakaoNickname;        // 카카오 닉네임
    private Boolean talkMessageConsent;  // 메시지 수신 동의 여부
    private String createdAt;            // 생성 날짜 (ISO 형식 문자열)
    private int userId;                  // 연결된 사용자 ID (외래 키)
}
