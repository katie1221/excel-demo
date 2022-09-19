package com.example.exceldemo.dao;

import com.example.exceldemo.entity.Company;
import com.example.exceldemo.entity.Course;
import com.example.exceldemo.mybatis.MyMapper;
import org.springframework.stereotype.Repository;

/**
 * 公司
 * @author qzz
 */
@Repository
public interface CompanyMapper extends MyMapper<Company> {

}
