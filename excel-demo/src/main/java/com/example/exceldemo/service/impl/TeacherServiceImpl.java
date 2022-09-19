package com.example.exceldemo.service.impl;

import com.example.exceldemo.dao.TeacherMapper;
import com.example.exceldemo.dto.TeacherRequestJson;
import com.example.exceldemo.entity.Teacher;
import com.example.exceldemo.service.BaseService;
import com.example.exceldemo.service.TeacherService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

/**
 * @author qzz
 */
@Service("teacherService")
public class TeacherServiceImpl extends BaseService implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;


    /**
     * 新增教师信息
     * @param teacher
     * @return
     */
    @Override
    public Integer addTeacher(Teacher teacher) {
        fillCommonField(teacher);
        //insertSelective 保存一个实体，null的属性不会保存，会使用数据库默认值
        return teacherMapper.insertSelective(teacher);
    }

    /**
     * 编辑教师信息
     * @param teacher
     * @return
     */
    @Override
    public Integer updateTeacher(Teacher teacher) {
        fillCommonField(teacher);
        //根据主键id更新教师信息  updateByPrimaryKeySelective:根据主键更新属性不为null的值
        return teacherMapper.updateByPrimaryKeySelective(teacher);
    }

    /**
     * 查看教师详情
     * @param id
     * @return
     */
    @Override
    public Teacher getTeacherInfoById(Integer id) {
        return teacherMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取教师列表
     * @param teacherRequestJson
     * @return
     */
    @Override
    public List<Teacher> getTeacherList(TeacherRequestJson teacherRequestJson) {
        Example example = new Example(Teacher.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(teacherRequestJson.getName())){
            criteria.andLike("name","%"+teacherRequestJson.getName()+"%");
        }
        return teacherMapper.selectByExample(example);
    }
}
