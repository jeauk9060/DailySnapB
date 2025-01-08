package com.dailysnap.summation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.dailysnap.summation.dao.KakaoUserDAO;
import com.dailysnap.summation.dto.KakaoUserDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class KakaoService {

    @Value("${kakao.client-id}") // env.yml에서 값을 가져옴
    private String clientId;

    @Value("${kakao.redirect-uri}") // env.yml에서 값을 가져옴
    private String redirectUri;

    private final String tokenUri = "https://kauth.kakao.com/oauth/token"; // 토큰 요청 URL
    private final String userInfoUri = "https://kapi.kakao.com/v2/user/me"; // 사용자 정보 요청 URL

    @Autowired
    private KakaoUserDAO kakaoUserDAO; // DAO 주입

    // JWT 생성 관련 설정
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS512); // JWT 키 생성
    private final long expiration = 1000 * 60 * 60 * 24; // 24시간 유효 기간

    // 액세스 토큰 요청
    public String getAccessTokenFromKakao(String code) {
        RestTemplate restTemplate = new RestTemplate();

        log.info("client_id: {}", clientId);
        log.info("redirect_uri: {}", redirectUri);

        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        // HTTP 요청 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 카카오에 POST 요청 전송
        ResponseEntity<Map> response = restTemplate.exchange(
            tokenUri,
            HttpMethod.POST,
            request,
            Map.class
        );

        // 응답 처리
        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("access_token")) {
                return responseBody.get("access_token").toString();
            } else {
                throw new RuntimeException("응답에서 액세스 토큰을 찾을 수 없습니다.");
            }
        } else {
            throw new RuntimeException("카카오 토큰 요청 실패: " + response.getBody());
        }
    }

    // 사용자 정보 요청
    public Map<String, Object> getUserInfo(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        log.info("Access Token: {}", accessToken);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Void> request = new HttpEntity<>(headers);

        ResponseEntity<Map> response = restTemplate.exchange(
            userInfoUri, HttpMethod.GET, request, Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> userInfo = response.getBody();
            log.info("사용자 정보: {}", userInfo);
            return userInfo;
        } else {
            throw new RuntimeException("카카오 사용자 정보 요청 실패: " + response.getBody());
        }
    }

    // DB 저장
    public void saveUserToDB(KakaoUserDTO userDTO) {
        log.info("DB 저장 요청: {}", userDTO);
        kakaoUserDAO.insertKakaoUser(userDTO);
        log.info("DB 저장 완료: 사용자 ID = {}", userDTO.getKakaoId());
    }

    // JWT 토큰 생성
    public String generateJwtToken(String email) {
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }
}
