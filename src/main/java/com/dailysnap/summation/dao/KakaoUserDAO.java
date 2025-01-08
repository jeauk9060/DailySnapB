package com.dailysnap.summation.dao;

import com.dailysnap.summation.dto.KakaoUserDTO;
import com.dailysnap.summation.model.KakaoUserVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KakaoUserDAO {

    // 사용자 추가
    void insertKakaoUser(KakaoUserDTO kakaoUserDTO);

    // 카카오 ID로 사용자 조회
    KakaoUserVO getKakaoUserById(@Param("kakaoId") Long kakaoId);

    // 이메일 중복 검사
    boolean existsByEmail(@Param("kakaoEmail") String kakaoEmail);

    // 카카오 닉네임 및 메시지 동의 여부 업데이트
    void updateKakaoUserInfo(@Param("kakaoId") Long kakaoId,
                             @Param("kakaoNickname") String kakaoNickname,
                             @Param("talkMessageConsent") Boolean talkMessageConsent);

    // 모든 사용자 조회
    List<KakaoUserVO> getAllKakaoUsers();

    // 사용자 삭제
    void deleteKakaoUser(@Param("kakaoId") Long kakaoId);
}
