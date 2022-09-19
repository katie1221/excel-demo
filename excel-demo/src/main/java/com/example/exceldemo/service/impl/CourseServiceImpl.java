package com.example.exceldemo.service.impl;

import com.example.exceldemo.dao.CourseMapper;
import com.example.exceldemo.dto.CourseRequestJson;
import com.example.exceldemo.dto.CourseVo;
import com.example.exceldemo.entity.Course;
import com.example.exceldemo.service.BaseService;
import com.example.exceldemo.service.CourseService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

/**
 * @author qzz
 */
@Service("courseService")
public class CourseServiceImpl extends BaseService implements CourseService {

    @Autowired
    private CourseMapper courseMapper;


    /**
     * 新增课程信息
     * @param course
     * @return
     */
    @Override
    public Integer addCourse(Course course) {
        fillCommonField(course);
        //insertSelective 保存一个实体，null的属性不会保存，会使用数据库默认值
        return courseMapper.insertSelective(course);
    }

    /**
     * 编辑课程信息
     * @param course
     * @return
     */
    @Override
    public Integer updateCourse(Course course) {
        fillCommonField(course);
        //根据主键id更新课程信息  updateByPrimaryKeySelective:根据主键更新属性不为null的值
        return courseMapper.updateByPrimaryKeySelective(course);
    }

    /**
     * 查看课程详情
     * @param id
     * @return
     */
    @Override
    public Course getCourseInfoById(Integer id) {
        return courseMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取课程列表
     * @param courseRequestJson
     * @return
     */
    @Override
    public List<Course> getCourseList(CourseRequestJson courseRequestJson) {
        Example example = new Example(Course.class);
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotEmpty(courseRequestJson.getName())){
            criteria.andLike("name","%"+courseRequestJson.getName()+"%");
        }
        return courseMapper.selectByExample(example);
    }

    /**
     * 获取课程导出列表
     * @param courseRequestJson
     * @return
     */
    @Override
    public List<CourseVo> getCourseExportList(CourseRequestJson courseRequestJson) {
        return courseMapper.getCourseExportList(courseRequestJson);
    }
}
