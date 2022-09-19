package com.example.exceldemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 学生
 * @author qzz
 */
@Data
@Table(name="t_student")
public class Student {

    /**
     * IDENTITY：主键由数据库自动生成（主要是自动增长型，这个用的比较多）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 学生姓名
     */
    @Column
    private String name;

    /**
     * 学生性别 1:男  2:女
     */
    @Column
    private Integer sex;

    /**
     * 出生日期
     */
    @Column
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 进校日期
     */
    @Column(name="registration_date")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registrationDate;

    /**
     * 1:正常 0：删除
     */
    @Column
    private Integer status;

    /**
     * @JsonFormat：由后端传递给前端
     * @DateTimeFormat:由前端传递给后端
     */
    @Column(name="create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @Column(name="create_user")
    private String createUser;

    @Column(name="update_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @Column(name="update_user")
    private String updateUser;
}
