package com.example.exceldemo.dto;

import com.example.exceldemo.excel.StudentExcel;
import com.example.exceldemo.excel.TeacherExcel;
import lombok.Data;
import java.util.List;

/**
 * 课程
 * @author qzz
 */
@Data
public class CourseVo{

    /**
     * 课程id
     */
    private Integer id;

    /**
     * 课程姓名
     */
    private String name;

    /**
     * 老师
     */
    private TeacherExcel teacherExcel;

    /**
     * 学生列表
     */
    private List<StudentExcel> studentExcelList;
}
