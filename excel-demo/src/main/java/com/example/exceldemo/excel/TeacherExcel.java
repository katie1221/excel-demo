package com.example.exceldemo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * 老师
 * @author qzz
 */
@Data
public class TeacherExcel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @Excel 作用在一个filed上面，对列的描述
     * @param name 列名
     * @param orderNum 下标，从0开始。
     */
    @Excel(name = "主讲老师_major,代课老师_absent",needMerge = true, width = 20,orderNum = "1")
    private String name;
}
