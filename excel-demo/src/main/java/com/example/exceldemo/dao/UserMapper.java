package com.example.exceldemo.dao;

import com.example.exceldemo.entity.User;
import com.example.exceldemo.mybatis.MyMapper;
import org.springframework.stereotype.Repository;

/**
 * 用户
 * @author qzz
 */
@Repository
public interface UserMapper extends MyMapper<User> {

}
