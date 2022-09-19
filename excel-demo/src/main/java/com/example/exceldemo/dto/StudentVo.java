package com.example.exceldemo.dto;

import lombok.Data;
import java.util.Date;

/**
 * 学生
 * @author qzz
 */
@Data
public class StudentVo {

    /**
     * 学生姓名
     */
    private String name;

    /**
     * 学生性别 1:男  2:女
     */
   
    private Integer sex;

    /**
     * 出生日期
     */
   
    private Date birthday;

    /**
     * 进校日期
     */
    private Date registrationDate;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;
}
