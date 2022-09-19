package com.example.exceldemo.service.impl;

import com.example.exceldemo.dao.StudentMapper;
import com.example.exceldemo.dto.StudentRequestJson;
import com.example.exceldemo.entity.Student;
import com.example.exceldemo.service.BaseService;
import com.example.exceldemo.service.StudentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

/**
 * @author qzz
 */
@Service("StudentService")
public class StudentServiceImpl extends BaseService implements StudentService {

    @Autowired
    private StudentMapper studentMapper;


    /**
     * 新增学生信息
     * @param student
     * @return
     */
    @Override
    public Integer addStudent(Student student) {
        fillCommonField(student);
        //insertSelective 保存一个实体，null的属性不会保存，会使用数据库默认值
        return studentMapper.insertSelective(student);
    }

    /**
     * 编辑学生信息
     * @param student
     * @return
     */
    @Override
    public Integer updateStudent(Student student) {
        fillCommonField(student);
        //根据主键id更新学生信息  updateByPrimaryKeySelective:根据主键更新属性不为null的值
        return studentMapper.updateByPrimaryKeySelective(student);
    }

    /**
     * 查看学生详情
     * @param id
     * @return
     */
    @Override
    public Student getStudentInfoById(Integer id) {
        return studentMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取学生列表
     * @param studentRequestJson
     * @return
     */
    @Override
    public List<Student> getStudentList(StudentRequestJson studentRequestJson) {
        Example example = new Example(Student.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("status",1);
        if(StringUtils.isNotEmpty(studentRequestJson.getName())){
            criteria.andLike("name","%"+studentRequestJson.getName()+"%");
        }
        return studentMapper.selectByExample(example);
    }
}
