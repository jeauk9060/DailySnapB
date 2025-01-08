package com.dailysnap.summation.service;

import org.springframework.stereotype.Service;

import com.dailysnap.summation.dao.UserDAO;
import com.dailysnap.summation.dto.UserDTO;
import com.dailysnap.summation.model.UserVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDAO userDAO;

    public void registerUser(UserDTO userDTO) {
        userDAO.insertUser(userDTO);
    }
    

    // 이메일로 사용자 조회
    public UserVO getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }

    // 이메일 중복 검사
    public boolean existsByEmail(String email) {
        return userDAO.existsByEmail(email);
    }

    // 구독 상태 업데이트
    public void updateSubscriptionStatus(String email, int status) {
        userDAO.updateSubscriptionStatus(email, status);
    }
}
