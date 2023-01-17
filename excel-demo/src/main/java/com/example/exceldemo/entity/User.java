package com.example.exceldemo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户
 * @author qzz
 */
@Data
@Table(name="t_user")
public class User {

    /**
     * IDENTITY：主键由数据库自动生成（主要是自动增长型，这个用的比较多）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    @Column
    private String name;

    /**
     * 年龄
     */
    @Column
    private Integer age;

    /**
     * 性别 0 男 1 女
     */
    @Column
    private Integer sex;

    /**
     * 手机号
     */
    @Column
    private String phone;

    /**
     * 备注
     */
    @Column
    private String remarks;

    /**
     * 状态 0启用 1禁用 默认0
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
