package com.example.exceldemo.service;

import com.example.exceldemo.dto.StudentRequestJson;
import com.example.exceldemo.entity.Student;

import java.util.List;

/**
 * 业务层
 * @author qzz
 */
public interface StudentService {

    /**
     * 新增学生信息
     * @param student
     * @return
     */
    Integer addStudent(Student student);

    /**
     * 编辑学生信息
     * @param student
     * @return
     */
    Integer updateStudent(Student student);

    /**
     * 查看学生详情信息
     * @param id
     * @return
     */
    Student getStudentInfoById(Integer id);

    /**
     * 获取学生列表
     * @param studentRequestJson
     * @return
     */
    List<Student> getStudentList(StudentRequestJson studentRequestJson);
}
