package com.example.exceldemo.service;

import com.example.exceldemo.entity.CourseStudent;
import java.util.List;

/**
 * 业务层
 * @author qzz
 */
public interface CourseStudentService {

    /**
     * 批量新增课程学生关联信息
     * @param courseStudentList
     * @return
     */
    Integer addBatchCourseStudent(List<CourseStudent> courseStudentList);
}
