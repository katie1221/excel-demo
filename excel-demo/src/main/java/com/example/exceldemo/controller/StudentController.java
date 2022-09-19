package com.example.exceldemo.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.example.exceldemo.common.Result;
import com.example.exceldemo.dto.StudentRequestJson;
import com.example.exceldemo.entity.CourseStudent;
import com.example.exceldemo.entity.Student;
import com.example.exceldemo.excel.StudentExcel;
import com.example.exceldemo.service.CourseStudentService;
import com.example.exceldemo.service.StudentService;
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
 * 学生
 * @author qzz
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseStudentService courseStudentService;

    /**
     * 新增学生
     * @param student
     * @return
     */
    @PostMapping("/add")
    public Result addStudent(@RequestBody Student student){
        studentService.addStudent(student);
        return Result.success();
    }

    /**
     * 编辑学生
     * @param student
     * @return
     */
    @PostMapping("/update")
    public Result updateStudent(@RequestBody Student student){
        studentService.updateStudent(student);
        return Result.success();
    }

    /**
     * 查看学生详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result getStudentDetail(@RequestParam("id") Integer id){
        return Result.success(studentService.getStudentInfoById(id));
    }

    /**
     * 查看学生列表
     * @param name
     * @return
     */
    @GetMapping("/list")
    public Result getStudentList(@RequestParam(name = "name",required = false) String name){
        StudentRequestJson requestJson = new StudentRequestJson();
        if(StringUtils.isNotEmpty(name)){
            requestJson.setName(name);
        }
        return Result.success(studentService.getStudentList(requestJson));
    }

    /**
     * 分页查看学生列表
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result getStudentListByPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,@RequestParam(name = "name",required = false) String name){
        PageHelper.startPage(page,pageSize);
        StudentRequestJson requestJson = new StudentRequestJson();
        if(StringUtils.isNotEmpty(name)){
            requestJson.setName(name);
        }
        List<Student> list =studentService.getStudentList(requestJson);
        return Result.success(new PageInfo<>(list));
    }

    /**
     * 导出
     * @param name
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestParam(name = "name",required = false) String name,HttpServletResponse response){
        try {
            StudentRequestJson requestJson = new StudentRequestJson();
            if(StringUtils.isNotEmpty(name)){
                requestJson.setName(name);
            }
            //1.从数据库查询到数据
            List<Student> list =studentService.getStudentList(requestJson);
            //2.导出时的excel名称
            String dateString = String.valueOf(System.currentTimeMillis());
            String fileName = "学生信息表" + dateString;
            //3.查看结果集转成导出vo（转成对应的类型;要不然会报错）
            List<StudentExcel> studentExcelList = new ListUtils<StudentExcel>().copyProperties(list,StudentExcel.class);
            //4.工具类导出excel
            ExcelUtils.exportExcel(studentExcelList,StudentExcel.class,fileName,new ExportParams("计算机一班学生","学生"),response);
        } catch (IOException e) {
        }
    }

    /**
     * 新增学生课程关联
     * @param courseStudentList
     * @return
     */
    @PostMapping("/addCourseStudent")
    public Result addCourseStudent(@RequestBody List<CourseStudent> courseStudentList){
        courseStudentService.addBatchCourseStudent(courseStudentList);
        return Result.success();
    }
}
