package com.example.exceldemo.service;

import com.example.exceldemo.utils.ReflectHelper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author qzz
 */
@Service
@SuppressWarnings("ALL")
public class BaseService {

    /**
     * 公共字段自动填充
     * @param o
     * @param <T>
     */
    public <T> void fillCommonField(T o){
        try {
            Object id = ReflectHelper.getValueByFieldName(o, "id");
            //id为空即为新增
            if(id == null){
                ReflectHelper.setValueByFieldName(o,"createTime",new Date());
                ReflectHelper.setValueByFieldName(o,"createUser",getCurrentUser());
            }else{
                ReflectHelper.setValueByFieldName(o,"updateTime",new Date());
                ReflectHelper.setValueByFieldName(o,"updateUser",getCurrentUser());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取当前用户
     * @return
     */
    private String getCurrentUser() {
        return "admin";
    }
}
