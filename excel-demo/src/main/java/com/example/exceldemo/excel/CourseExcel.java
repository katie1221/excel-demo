package com.example.exceldemo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelCollection;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 课程
 * @author qzz
 */
@Data
@ExcelTarget("courseExcel")
public class CourseExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @Excel 作用在一个filed上面，对列的描述
     * @param name 列名
     * @param orderNum 下标，从0开始。
     */
    @Excel(name = "课程姓名", width = 30,needMerge = true,orderNum = "1")
    private String name;

    /**
     * 老师主键
     */
    @ExcelEntity(id="absent")
    private TeacherExcel teacherExcel;

    /**
     * 学生列表
     */
    @ExcelCollection(name="学生", orderNum = "3")
    private List<StudentExcel> studentExcelList;
}
