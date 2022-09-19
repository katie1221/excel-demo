package com.example.exceldemo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生
 * @author qzz
 */
@Data
public class StudentExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * @Excel 作用在一个filed上面，对列的描述
     * @param name 列名
     * @param orderNum 下标，从0开始。
     */
    @Excel(name = "学生姓名", width = 30)
    private String name;

    /**
     * 学生性别 1:男  2:女
     */
    @Excel(name = "学生性别", replace = {"男_1","女_0"},suffix = "生")
    private Integer sex;

    /**
     * 出生日期
     * 如果数据库是string类型,这个需要设置这个数据库时间格式(databaseFormat)  format：输出时间格式
     */
    @Excel(name = "出生日期", databaseFormat = "yyyyMMddHHmmss",format = "yyyy-MM-dd",width = 20)
    private Date birthday;

    /**
     * 进校日期
     * 字段是Date类型则不需要设置databaseFormat
     */
    @Excel(name = "出生日期", format = "yyyy-MM-dd",width = 20)
    private Date registrationDate;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
}
