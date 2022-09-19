package com.example.exceldemo.service;

import com.example.exceldemo.dto.TeacherRequestJson;
import com.example.exceldemo.entity.Teacher;
import java.util.List;

/**
 * 业务层
 * @author qzz
 */
public interface TeacherService {

    /**
     * 新增教师信息
     * @param teacher
     * @return
     */
    Integer addTeacher(Teacher teacher);

    /**
     * 编辑教师信息
     * @param teacher
     * @return
     */
    Integer updateTeacher(Teacher teacher);

    /**
     * 查看教师详情信息
     * @param id
     * @return
     */
    Teacher getTeacherInfoById(Integer id);

    /**
     * 获取教师列表
     * @param TeacherRequestJson
     * @return
     */
    List<Teacher> getTeacherList(TeacherRequestJson TeacherRequestJson);
}
