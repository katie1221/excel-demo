package com.example.exceldemo.utils;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author qzz
 */
public class ReflectHelper {
    public ReflectHelper() {
    }

    public static List<String> getAllField(Object obj) {
        if (obj == null) {
            return null;
        } else {
            Class<?> clz = obj.getClass();
            Field[] fields = clz.getDeclaredFields();
            List<String> fieldNames = new ArrayList();
            Field[] var4 = fields;
            int var5 = fields.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                Field f = var4[var6];
                fieldNames.add(f.getName());
            }

            return fieldNames;
        }
    }

    public static Field getFieldByFieldName(Object obj, String fieldName) {
        Class superClass = obj.getClass();

        while(superClass != Object.class) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException var4) {
                superClass = superClass.getSuperclass();
            }
        }

        return null;
    }

    public static Object getValueByFieldName(Object obj, String fieldName) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getFieldByFieldName(obj, fieldName);
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }

        return value;
    }

    public static void setValueByFieldName(Object obj, String fieldName, Object value) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = obj.getClass().getDeclaredField(fieldName);
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }

    }
}

