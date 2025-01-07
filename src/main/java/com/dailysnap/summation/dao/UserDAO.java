package com.dailysnap.summation.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dailysnap.summation.model.UserVO;

@Repository
public interface UserDAO {
    // 사용자 추가
    void insertUser(UserVO user);

    // 이메일로 사용자 조회2
    UserVO getUserByEmail(@Param("email") String email);

    // 이메일 중복 검사
    boolean existsByEmail(@Param("email") String email);

    // 구독 상태 업데이트
    void updateSubscriptionStatus(@Param("email") String email, @Param("status") int status);
}
