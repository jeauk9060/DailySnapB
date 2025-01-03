package com.dailysnap.summation.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dailysnap.summation.dao.UserDAO;
import com.dailysnap.summation.model.UserVO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDAO userDAO;

    public void registerUser(UserVO user) {
        userDAO.insertUser(user);
    }

    public UserVO getUserByEmail(String email) {
        return userDAO.getUserByEmail(email);
    }
}
