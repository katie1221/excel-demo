package com.example.exceldemo.service;

import com.example.exceldemo.entity.User;
import com.example.exceldemo.excel.UserExcel;

import java.util.List;

/**
 * 业务层
 * @author qzz
 */
public interface UserService {


    /**
     * 批量新增用户信息
     * @param userList
     * @return
     */
    Integer addBatchUser(List<User> userList);

    /**
     * 获取用户列表
     * @return
     */
    List<UserExcel> findUserList();
}
