package com.example.exceldemo.utils;

import org.apache.commons.collections.CollectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class ListUtils<K> {
    /**
     * Copy list
     *
     * @param orig
     * @param desClass
     * @return
     */
    public List<K> copyProperties(List<?> orig, Class<K> desClass) {
        if (Objects.isNull(orig)) {
            return null;
        }
        List<K> dest = new ArrayList<K>();
        K des;
        for (Object o : orig) {
            try {
                des = desClass.newInstance();
                des = BeanUtils.copyAllPropertysNotNull(des, o);
                dest.add(des);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dest;
    }

    /**
     * 将List<**Domain>转换为List<**fidld>
     *
     * @param collection 数据源
     * @param clazz      domain名称
     * @param fieldName  字段名称
     * @return
     */
    public static List<Object> getFieldValueList(Collection collection, Class clazz, String fieldName) {
        List<Object> objList = new ArrayList<Object>();
        String methodName = "get" + getFirstUpper(fieldName);
        try {
            Method m = clazz.getMethod(methodName);
            if (CollectionUtils.isNotEmpty(collection)) {
                for (Object object : collection) {
                    objList.add(m.invoke(object));
                }
            }
        } catch (Exception e) {
        }
        return objList;
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }


    public static String getFirstUpper(String str) {
        String newStr = "";
        if (str.length() > 0) {
            newStr = str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
        }
        return newStr;
    }

}
