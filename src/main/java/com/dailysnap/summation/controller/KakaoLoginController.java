package com.dailysnap.summation.controller;

import com.dailysnap.summation.service.KakaoService;
import com.dailysnap.summation.dto.KakaoUserDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("")
public class KakaoLoginController {

    private final KakaoService kakaoService;

    @GetMapping("/kakao")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        try {
            // 1. 인가 코드로 액세스 토큰 요청
            String accessToken = kakaoService.getAccessTokenFromKakao(code);

            // 2. 액세스 토큰으로 사용자 정보 요청
            Map<String, Object> userInfo = kakaoService.getUserInfo(accessToken);

            // 3. 사용자 정보 추출
            Long kakaoId = Long.valueOf(userInfo.get("id").toString());
            Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
            String email = (String) kakaoAccount.get("email");
            Map<String, Object> properties = (Map<String, Object>) userInfo.get("properties");
            String nickname = (String) properties.get("nickname");

            // 4. 사용자 정보 DTO 생성
            KakaoUserDTO kakaoUserDTO = new KakaoUserDTO();
            kakaoUserDTO.setKakaoId(kakaoId);
            kakaoUserDTO.setKakaoEmail(email);
            kakaoUserDTO.setKakaoNickname(nickname);
            kakaoUserDTO.setTalkMessageConsent(false);
            kakaoUserDTO.setCreatedAt(new Date().toString());
            kakaoUserDTO.setUserId(1); // 임시 값 설정

            // 5. 사용자 정보 DB 저장
            kakaoService.saveUserToDB(kakaoUserDTO);

            // 6. JWT 토큰 생성
            String jwtToken = kakaoService.generateJwtToken(email);

            // 7. 응답 반환
            log.info("JWT 토큰: {}", jwtToken);
            return ResponseEntity.ok(Map.of("token", jwtToken, "userInfo", userInfo));
        } catch (Exception e) {
            log.error("로그인 처리 중 오류 발생", e);
            return ResponseEntity.badRequest().body("로그인 처리 중 오류 발생: " + e.getMessage());
        }
    }
}