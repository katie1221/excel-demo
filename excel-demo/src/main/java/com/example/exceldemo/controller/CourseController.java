package com.example.exceldemo.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.example.exceldemo.common.Result;
import com.example.exceldemo.dto.CourseRequestJson;
import com.example.exceldemo.dto.CourseVo;
import com.example.exceldemo.entity.Course;
import com.example.exceldemo.excel.CourseExcel;
import com.example.exceldemo.service.CourseService;
import com.example.exceldemo.utils.ExcelUtils;
import com.example.exceldemo.utils.ListUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 课程
 * @author qzz
 */
@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    /**
     * 新增课程
     * @param course
     * @return
     */
    @PostMapping("/add")
    public Result addCourse(@RequestBody Course course){
        courseService.addCourse(course);
        return Result.success();
    }

    /**
     * 编辑课程
     * @param course
     * @return
     */
    @PostMapping("/update")
    public Result updateCourse(@RequestBody Course course){
        courseService.updateCourse(course);
        return Result.success();
    }

    /**
     * 查看课程详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result getCourseDetail(@RequestParam("id") Integer id){
        return Result.success(courseService.getCourseInfoById(id));
    }

    /**
     * 查看课程列表
     * @param name
     * @return
     */
    @GetMapping("/list")
    public Result getCourseList(@RequestParam(name = "name",required = false) String name){
        CourseRequestJson requestJson = new CourseRequestJson();
        if(StringUtils.isNotEmpty(name)){
            requestJson.setName(name);
        }
        return Result.success(courseService.getCourseList(requestJson));
    }

    /**
     * 分页查看课程列表
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result getCourseListByPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,@RequestParam(name = "name",required = false) String name){
        PageHelper.startPage(page,pageSize);
        CourseRequestJson requestJson = new CourseRequestJson();
        if(StringUtils.isNotEmpty(name)){
            requestJson.setName(name);
        }
        List<Course> list =courseService.getCourseList(requestJson);
        return Result.success(new PageInfo<>(list));
    }

    /**
     * 导出(一对多)
     * @param name
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestParam(name = "name",required = false) String name,HttpServletResponse response){
        try {
            CourseRequestJson requestJson = new CourseRequestJson();
            if(StringUtils.isNotEmpty(name)){
                requestJson.setName(name);
            }
            //1.从数据库查询到数据(一对多查询)
            List<CourseVo> list =courseService.getCourseExportList(requestJson);
            //2.导出时的excel名称
            String dateString = String.valueOf(System.currentTimeMillis());
            String fileName = "课程学生信息表" + dateString;
            //3.查看结果集转成导出vo（转成对应的类型;要不然会报错）
            List<CourseExcel> CourseExcelList = new ListUtils<CourseExcel>().copyProperties(list,CourseExcel.class);
            //4.工具类导出excel
            ExcelUtils.exportExcel(CourseExcelList,CourseExcel.class,fileName,new ExportParams("计算机一班","课程学生"),response);
        } catch (IOException e) {
        }
    }
}
