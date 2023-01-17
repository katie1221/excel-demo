package com.example.exceldemo.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.exceldemo.dao.CompanyMapper;
import com.example.exceldemo.dao.UserMapper;
import com.example.exceldemo.entity.Company;
import com.example.exceldemo.entity.User;
import com.example.exceldemo.excel.UserExcel;
import com.example.exceldemo.service.BaseService;
import com.example.exceldemo.service.CompanyService;
import com.example.exceldemo.service.UserService;
import com.example.exceldemo.utils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qzz
 */
@Service("UserService")
public class UserServiceImpl extends BaseService implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 批量新增公司信息
     * @param userList
     * @return
     */
    @Override
    public Integer addBatchUser(List<User> userList) {
        for(User user : userList){
            fillCommonField(user);
        }
        return userMapper.insertList(userList);
    }

    /**
     * 获取用户列表
     * @return
     */
    @Override
    public List<UserExcel> findUserList() {
        List<UserExcel> userExcelList = new ArrayList<>();
        List<User> userList = userMapper.selectAll();
        for(User user:userList){
            UserExcel userExcel = new UserExcel();
            BeanUtil.copyProperties(user,userExcel);
            userExcel.setSexStr(user.getSex()==0?"男":"女");
            userExcelList.add(userExcel);
        }
        return userExcelList;
    }

}
