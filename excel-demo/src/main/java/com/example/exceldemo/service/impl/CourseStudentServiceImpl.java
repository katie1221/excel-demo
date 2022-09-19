package com.example.exceldemo.service.impl;

import com.example.exceldemo.dao.CourseStudentMapper;
import com.example.exceldemo.entity.CourseStudent;
import com.example.exceldemo.service.CourseStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qzz
 */
@Service("courseStudentService")
public class CourseStudentServiceImpl implements CourseStudentService {

    @Autowired
    private CourseStudentMapper courseStudentMapper;

    /**
     * 批量新增课程学生关联信息
     * @param courseStudentList
     * @return
     */
    @Override
    public Integer addBatchCourseStudent(List<CourseStudent> courseStudentList) {
        return courseStudentMapper.insertList(courseStudentList);
    }
}
