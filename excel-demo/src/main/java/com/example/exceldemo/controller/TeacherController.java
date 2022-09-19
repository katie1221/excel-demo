package com.example.exceldemo.controller;

import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.example.exceldemo.common.Result;
import com.example.exceldemo.dto.TeacherRequestJson;
import com.example.exceldemo.entity.Teacher;
import com.example.exceldemo.excel.TeacherExcel;
import com.example.exceldemo.service.TeacherService;
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
 * 教师
 * @author qzz
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * 新增教师
     * @param teacher
     * @return
     */
    @PostMapping("/add")
    public Result addTeacher(@RequestBody Teacher teacher){
        teacherService.addTeacher(teacher);
        return Result.success();
    }

    /**
     * 编辑教师
     * @param teacher
     * @return
     */
    @PostMapping("/update")
    public Result updateTeacher(@RequestBody Teacher teacher){
        teacherService.updateTeacher(teacher);
        return Result.success();
    }

    /**
     * 查看教师详情
     * @param id
     * @return
     */
    @GetMapping("/detail")
    public Result getTeacherDetail(@RequestParam("id") Integer id){
        return Result.success(teacherService.getTeacherInfoById(id));
    }

    /**
     * 查看教师列表
     * @param name
     * @return
     */
    @GetMapping("/list")
    public Result getTeacherList(@RequestParam(name = "name",required = false) String name){
        TeacherRequestJson requestJson = new TeacherRequestJson();
        if(StringUtils.isNotEmpty(name)){
            requestJson.setName(name);
        }
        return Result.success(teacherService.getTeacherList(requestJson));
    }

    /**
     * 分页查看教师列表
     * @param name
     * @return
     */
    @GetMapping("/page")
    public Result getTeacherListByPage(@RequestParam("page") Integer page, @RequestParam("pageSize") Integer pageSize,@RequestParam(name = "name",required = false) String name){
        PageHelper.startPage(page,pageSize);
        TeacherRequestJson requestJson = new TeacherRequestJson();
        if(StringUtils.isNotEmpty(name)){
            requestJson.setName(name);
        }
        List<Teacher> list =teacherService.getTeacherList(requestJson);
        return Result.success(new PageInfo<>(list));
    }

    /**
     * 导出
     * @param name
     */
    @RequestMapping("/exportExcel")
    public void exportExcel(@RequestParam(name = "name",required = false) String name,HttpServletResponse response){
        try {
            TeacherRequestJson requestJson = new TeacherRequestJson();
            if(StringUtils.isNotEmpty(name)){
                requestJson.setName(name);
            }
            //1.从数据库查询到数据
            List<Teacher> list =teacherService.getTeacherList(requestJson);
            //2.导出时的excel名称
            String dateString = String.valueOf(System.currentTimeMillis());
            String fileName = "教师信息表" + dateString;
            //3.查看结果集转成导出vo（转成对应的类型;要不然会报错）
            List<TeacherExcel> TeacherExcelList = new ListUtils<TeacherExcel>().copyProperties(list,TeacherExcel.class);
            //4.工具类导出excel
            ExcelUtils.exportExcel(TeacherExcelList,TeacherExcel.class,fileName,new ExportParams("计算机一班教师","教师"),response);
        } catch (IOException e) {
        }
    }
}
