package com.example.exceldemo.excel;

import cn.afterturn.easypoi.excel.annotation.Excel;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户
 * @author qzz
 */
@Data
public class UserExcel implements Serializable {

    /**
     * 姓名
     */
    @Excel(name = "姓名")
    private String name;

    /**
     * 年龄
     */
    @Excel(name = "年龄",type = 10)
    private Integer age;

    /**
     * 性别
     */
    @Excel(name = "性别")
    private String sexStr;

    /**
     * 手机号
     */
    @Excel(name = "手机号")
    private String phone;

    /**
     * 备注
     */
    @Excel(name = "备注")
    private String remarks;

    /**
     * 性别 0 男 1 女
     */
    private Integer sex;
}
