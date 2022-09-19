package com.example.exceldemo.service;

import com.example.exceldemo.entity.Company;
import java.util.List;

/**
 * 业务层
 * @author qzz
 */
public interface CompanyService {


    /**
     * 批量新增公司信息
     * @param companyList
     * @return
     */
    Integer addBatchCompany(List<Company> companyList);

    /**
     * 获取公司列表
     * @return
     */
    List<Company> findAll();
}
