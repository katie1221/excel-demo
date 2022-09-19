package com.example.exceldemo.dao;

import com.example.exceldemo.entity.Student;
import com.example.exceldemo.mybatis.MyMapper;
import org.springframework.stereotype.Repository;

/**
 * 学生
 * @author qzz
 */
@Repository
public interface StudentMapper extends MyMapper<Student> {

}
