package com.example.exceldemo.entity;

import lombok.Data;
import javax.persistence.*;

/**
 * 课程学生
 * @author qzz
 */
@Data
@Table(name="t_course_student")
public class CourseStudent {

    /**
     * IDENTITY：主键由数据库自动生成（主要是自动增长型，这个用的比较多）
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 课程id
     */
    @Column(name="course_id")
    private Integer courseId;

    /**
     * 学生id
     */
    @Column(name="student_id")
    private Integer studentId;
}
