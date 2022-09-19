package com.example.exceldemo.dao;

import com.example.exceldemo.dto.CourseRequestJson;
import com.example.exceldemo.dto.CourseVo;
import com.example.exceldemo.entity.Course;
import com.example.exceldemo.excel.StudentExcel;
import com.example.exceldemo.mybatis.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * 课程
 * @author qzz
 */
@Repository
public interface CourseMapper extends MyMapper<Course> {

    /**
     * 获取课程导出列表
     * @param CourseRequestJson
     * @return
     */
    List<CourseVo> getCourseExportList(CourseRequestJson CourseRequestJson);

    /**
     * 根据课程id获取学生列表信息
     * @param courseId
     * @return
     */
    List<StudentExcel> getStudentListById(@Param("id") Integer courseId);
}
