package com.example.exceldemo.service.impl;

import com.example.exceldemo.dao.CompanyMapper;
import com.example.exceldemo.entity.Company;
import com.example.exceldemo.service.BaseService;
import com.example.exceldemo.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author qzz
 */
@Service("companyService")
public class CompanyServiceImpl extends BaseService implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    /**
     * 批量新增公司信息
     * @param companyList
     * @return
     */
    @Override
    public Integer addBatchCompany(List<Company> companyList) {
        for(Company company : companyList){
            fillCommonField(company);
        }
        return companyMapper.insertList(companyList);
    }

    /**
     * @return
     */
    @Override
    public List<Company> findAll() {
        return companyMapper.selectAll();
    }

}
