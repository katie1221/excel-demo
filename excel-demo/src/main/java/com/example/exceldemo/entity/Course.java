package com.example.exceldemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 课程
 * @author qzz
 */
@Data
@Table(name="t_course")
public class Course {

    /**
     * IDENTITY：主键由数据库自动生成（主要是自动增长型，这个用的比较多）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 课程名称
     */
    @Column
    private String name;

    /**
     * 老师id
     */
    @Column(name="teacher_id")
    private Integer teacherId;

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
