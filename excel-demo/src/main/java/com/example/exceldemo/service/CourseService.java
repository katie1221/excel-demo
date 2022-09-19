package com.example.exceldemo.service;

import com.example.exceldemo.dto.CourseRequestJson;
import com.example.exceldemo.dto.CourseVo;
import com.example.exceldemo.entity.Course;
import java.util.List;

/**
 * 业务层
 * @author qzz
 */
public interface CourseService {

    /**
     * 新增课程信息
     * @param course
     * @return
     */
    Integer addCourse(Course course);

    /**
     * 编辑课程信息
     * @param course
     * @return
     */
    Integer updateCourse(Course course);

    /**
     * 查看课程详情信息
     * @param id
     * @return
     */
    Course getCourseInfoById(Integer id);

    /**
     * 获取课程列表
     * @param CourseRequestJson
     * @return
     */
    List<Course> getCourseList(CourseRequestJson CourseRequestJson);

    /**
     * 获取课程导出列表
     * @param CourseRequestJson
     * @return
     */
    List<CourseVo> getCourseExportList(CourseRequestJson CourseRequestJson);
}
