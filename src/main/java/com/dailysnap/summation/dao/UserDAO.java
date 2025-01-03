package com.dailysnap.summation.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.dailysnap.summation.model.UserVO;

@Repository
public interface UserDAO {
    void insertUser(UserVO user);

    UserVO getUserByEmail(@Param("email") String email);

}
