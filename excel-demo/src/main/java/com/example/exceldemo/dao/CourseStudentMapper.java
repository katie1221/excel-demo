package com.example.exceldemo.dao;

import com.example.exceldemo.entity.CourseStudent;
import com.example.exceldemo.mybatis.MyMapper;
import org.springframework.stereotype.Repository;

/**
 * 课程学生
 * @author qzz
 */
@Repository
public interface CourseStudentMapper extends MyMapper<CourseStudent> {

}
