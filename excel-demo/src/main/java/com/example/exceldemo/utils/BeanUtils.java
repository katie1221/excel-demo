package com.example.exceldemo.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;


/**
 * <p>
 * Note:反射的Utils函数集合.扩展自Apache Commons BeanUtil
 * <p>
 * Date: 06/15/2015 14:37.
 *
 * @author He Jialin
 */
@SuppressWarnings({"unchecked", "rawtypes", "unused"})
public class BeanUtils<T> {

    private BeanUtils() {
    }

    public static Field getDeclaredField(Object object, String propertyName) throws NoSuchFieldException {
        return getDeclaredField(object.getClass(), propertyName);
    }

    public static Field getDeclaredField(Class clazz, String propertyName) {
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                Field[] s = superClass.getDeclaredFields();
                return superClass.getDeclaredField(propertyName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 直接获取属性值
     *
     * @param object
     * @param propertyName
     * @return
     */
    public static Object getNameProperty(Object object, String propertyName) {
        Field field = null;
        try {
            field = getDeclaredField(object, propertyName);
        } catch (NoSuchFieldException e1) {
        }
        Object result = null;
        if (null != field) {
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            try {
                result = field.get(object);
            } catch (Exception e) {
            }
            field.setAccessible(accessible);
        }
        return result;
    }

    public static final String FORMAT_OBJECT = "F-JSONTO-OBJ";
    public static final String FORMAT_MAP = "F-JSONTO-MAP";
    public static final String FORMAT_LIST = "F-JSONTO-LIST";
    public static final String FORMAT_GET = "F-GET";
    public static final String TO_JSON = "TO-JSON";

    public static Object forceGetProperty(Object object, String propertyName) {
        Object result = null;
        try {
            if (object instanceof Map) {
                result = ((Map) object).get(propertyName);
            } else {
                result = getObjValue(object, propertyName, null);
            }
        } catch (Exception e) {
        }
        return result;
    }

    public static Map<String, Object> toReplaceKeyLow(Map<String, Object> map) {
        Map re_map = new HashMap();
        if (re_map != null) {
            Iterator var2 = map.entrySet().iterator();
            while (var2.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry) var2.next();
                re_map.put(underlineToCamel((String) entry.getKey()), map.get(entry.getKey()));
            }
            map.clear();
        }
        return re_map;
    }

    public static String underlineToCamel(String origin) {
        return stringProcess(
                origin, (prev, c) -> {
                    if (prev == '_' && Character.isLowerCase(c)) {
                        return "" + Character.toUpperCase(c);
                    }
                    if (c == '_') {
                        return "";
                    }
                    return "" + c;
                }
        );
    }

    public static String stringProcess(String origin, BiFunction<Character, Character, String> convertFunc) {
        if (origin == null || "".equals(origin.trim())) {
            return "";
        }
        String newOrigin = "0" + origin;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < newOrigin.length() - 1; i++) {
            char prev = newOrigin.charAt(i);
            char c = newOrigin.charAt(i + 1);
            sb.append(convertFunc.apply(prev, c));
        }
        return sb.toString();
    }

    /**
     * 直接赋属性值
     *
     * @param object
     * @param propertyName
     * @return
     */
    public static void setNameProperty(Object object, String propertyName, Object newValue) throws NoSuchFieldException {

        Field field = getDeclaredField(object, propertyName);
        boolean accessible = field.isAccessible();
        field.setAccessible(true);
        try {
            field.set(object, newValue);
        } catch (Exception e) {
        }
        field.setAccessible(accessible);
    }

    /**
     * 通过set方法赋值
     *
     * @param object
     * @param propertyName
     * @param newValue
     */
    public static void forceSetProperty(Object object, String propertyName, Object newValue) {
        if (null == object || StringUtils.isEmpty(propertyName)) {
            return;
        }
        String[] s = propertyName.split("\\.");
        if (null == s) {
            return;
        }
        for (int i = 0; i < s.length - 1; i++) {
            object = forceGetProperty(object, s[i]);
        }
        try {
            if (object instanceof Map) {
                ((Map) object).put(propertyName, newValue);
            } else {
                setObjValue(object, underlineToCamel(propertyName), newValue);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object invokePrivateMethod(Object object, String methodName, Object... params)
            throws NoSuchMethodException {
        Class[] types = new Class[params.length];
        for (int i = 0; i < params.length; i++) {
            types[i] = params[i].getClass();
        }
        Class clazz = object.getClass();
        Method method = null;
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                method = superClass.getDeclaredMethod(methodName, types);
                break;
            } catch (NoSuchMethodException e) {

            }
        }
        if (method == null) {
            throw new NoSuchMethodException("No Such Method:" + clazz.getSimpleName() + methodName);
        }
        boolean accessible = method.isAccessible();
        method.setAccessible(true);
        Object result = null;
        try {
            result = method.invoke(object, params);
        } catch (Exception e) {
        }
        method.setAccessible(accessible);
        return result;
    }

    /**
     * 通过反射动态调用方法
     *
     * @param classpath  类
     * @param methodname 方法名称
     * @param types      [] 方法参数数组
     * @return
     */
    public static Method transferMethoder(String classpath, String methodname, Class types[]) {
        Class clazz = null;
        try {
            clazz = Class.forName(classpath);
        } catch (Exception e) {
        }
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getMethod(methodname, types);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 通过反射动态调用方法
     *
     * @param obj        类
     * @param methodname 方法名称
     * @param types      [] 方法参数数组
     * @return
     */
    public static Method transferMethoder(Object obj, String methodname, Class types[]) {
        Class clazz = obj.getClass();
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getMethod(methodname, types);
            } catch (Exception e) {
            }
        }
        return null;
    }

    /**
     * 获取对象的属性(不包括继承的)
     *
     * @param obj
     * @return Field[]
     */
    public static Field[] getObjProperty(Object obj) {
        Class c = obj.getClass();
        Field[] field = c.getDeclaredFields();
        return field;
    }

    /**
     * 只拷贝超类里的数据
     *
     * @param arg0
     * @param arg1
     * @throws Exception
     */
    public static void copySupperPropertys(Object arg0, Object arg1) throws Exception {
        if (null != arg0 && null != arg1) {
            Object value = null;
            if (arg1 instanceof Map) {
                for (String key : ((Map<String, Object>) arg1).keySet()) {
                    value = BeanUtils.forceGetProperty(arg1, key);
                    BeanUtils.forceSetProperty(arg0, key, value);
                }
            } else {
                Field[] field = getObjSupperProperty(arg1);
                if (null != field) {
                    for (int i = 0; i < field.length; i++) {
                        value = BeanUtils.forceGetProperty(arg1, field[i].getName());
                        BeanUtils.forceSetProperty(arg0, field[i].getName(), value);
                    }
                }
            }
        } else {
            throw new Exception("参数为空");
        }
    }

    /**
     * 拷贝类里所有的数据
     *
     * @param arg0 dest
     * @param arg1
     * @throws Exception
     */
    public static void copyAllPropertys(Object arg0, Object arg1) throws Exception {
        if (null != arg0 && null != arg1) {
            Object value = null;
            if (arg1 instanceof Map) {
                for (String key : ((Map<String, Object>) arg1).keySet()) {
                    value = BeanUtils.forceGetProperty(arg1, key);
                    BeanUtils.forceSetProperty(arg0, key, value);
                }
            } else {
                Field[] field = getObjAllProperty(arg1);
                if (null != field) {
                    for (int i = 0; i < field.length; i++) {
                        if ("serialVersionUID".equals(field[i].getName())) {
                            continue;
                        }
                        value = BeanUtils.forceGetProperty(arg1, field[i].getName());
                        BeanUtils.forceSetProperty(arg0, field[i].getName(), value);
                    }
                }
            }
        } else {
            throw new Exception("参数为空");
        }
    }

    /**
     * 拷贝的数据(不包括继承的)
     *
     * @param arg0
     * @param arg1
     * @throws Exception
     */
    public static void copyImplPropertys(Object arg0, Object arg1) throws Exception {
        if (null != arg0 && null != arg1) {
            Object value = null;
            if (arg1 instanceof Map) {
                for (String key : ((Map<String, Object>) arg1).keySet()) {
                    value = BeanUtils.forceGetProperty(arg1, key);
                    BeanUtils.forceSetProperty(arg0, key, value);
                }
            } else {
                Field[] field = getObjProperty(arg1);
                if (null != field) {
                    for (int i = 0; i < field.length; i++) {
                        value = BeanUtils.forceGetProperty(arg1, field[i].getName());
                        BeanUtils.forceSetProperty(arg0, field[i].getName(), value);
                    }
                }
            }
        } else {
            throw new Exception("参数为空");
        }
    }

    public static String checkImplNull(Object arg0, Map<String, String> notcl) {
        if (null == arg0) {
            return "null";
        }
        Object value = null;
        String msg = "";
        if (arg0 instanceof Map) {
            for (String key : ((Map<String, Object>) arg0).keySet()) {
                if ("serialVersionUID".equals(key) || notcl.containsKey(key)) {
                    continue;
                }
                value = BeanUtils.forceGetProperty(arg0, key);
                if (null == value) {
                    msg += key + " is null ";
                } else if (value instanceof String) {
                    if (StringUtils.isEmpty(value.toString())) {
                        msg += key + " is null ";
                    }
                }
            }
        } else {
            Field[] field = getObjProperty(arg0);
            if (null != field) {
                for (int i = 0; i < field.length; i++) {
                    if ("serialVersionUID".equals(field[i].getName()) || notcl.containsKey(field[i].getName())) {
                        continue;
                    }
                    value = BeanUtils.forceGetProperty(arg0, field[i].getName());
                    if (null == value) {
                        msg += field[i].getName() + " is null ";
                    } else if (value instanceof String) {
                        if (StringUtils.isEmpty(value.toString())) {
                            msg += field[i].getName() + " is null ";
                        }
                    }
                }
            }
        }
        return msg;
    }

    /**
     * 获取对象祖先的属性
     *
     * @param obj
     * @return Field[]
     */
    public static Field[] getObjSupperProperty(Object obj) {
        Class c = obj.getClass();
        Class supper = c.getSuperclass();
        List<Field> list = new ArrayList<Field>();
        if (null != supper) {
            for (Class superClass = supper; superClass != Object.class; superClass = superClass.getSuperclass()) {
                Field[] fieldchild = superClass.getDeclaredFields();
                if (null != fieldchild) {
                    for (Field field2 : fieldchild) {
                        list.add(field2);
                    }
                }
            }
        }
        Field[] field = new Field[list.size()];
        field = list.toArray(field);
        return field;
    }

    /**
     * 获取对象祖先的属性,不包括supperbean
     *
     * @param obj
     * @return Field[]
     */
    public static Field[] getObjOpSupperProperty(Object obj) {
        Class c = obj.getClass();
        Class supper = c.getSuperclass();
        List<Field> list = new ArrayList<Field>();
        if (null != supper) {
            for (Class superClass = supper; superClass != Object.class; superClass = superClass.getSuperclass()) {
                Field[] fieldchild = superClass.getDeclaredFields();
                if (null != fieldchild) {
                    for (Field field2 : fieldchild) {
                        list.add(field2);
                    }
                }
            }
        }
        Field[] field = new Field[list.size()];
        field = list.toArray(field);
        return field;
    }

    /**
     * 获取对象所有的属性(包括继承的)
     *
     * @param obj
     * @return Field[]
     */
    public static Field[] getObjAllProperty(Object obj) {
        List<Field> list = new ArrayList<Field>();
        for (Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fieldchild = superClass.getDeclaredFields();
            if (null != fieldchild) {
                for (Field field2 : fieldchild) {
                    list.add(field2);
                }
            }
        }
        Field[] field = new Field[list.size()];
        field = list.toArray(field);
        return field;
    }

    /**
     * 获取对象所有的属性(包括继承的),不包括supperbean
     *
     * @param obj
     * @return Field[]
     */
    public static Field[] getObjAllOpProperty(Object obj) {
        List<Field> list = new ArrayList<Field>();
        for (Class superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            Field[] fieldchild = superClass.getDeclaredFields();
            if (null != fieldchild) {
                for (Field field2 : fieldchild) {
                    list.add(field2);
                }
            }
        }
        Field[] field = new Field[list.size()];
        field = list.toArray(field);
        return field;
    }

    /**
     * 获取对应的名称的get方法
     *
     * @param proName
     * @return
     */
    public static String getProNameMethod(String proName) {
        String methodName = "";
        if (StringUtils.isNotEmpty(proName)) {
            methodName = "get" + getFirstUpper(proName);
        }
        return methodName;
    }

    /**
     * 获取对应的名称的set方法
     *
     * @param proName
     * @return
     */
    public static String getProSetNameMethod(String proName) {
        String methodName = "";
        if (StringUtils.isNotEmpty(proName)) {
            methodName = "set" + getFirstUpper(proName);
        }
        return methodName;
    }

    /**
     * 获取对象里对应的属性值(通过get方法)
     *
     * @param obj
     * @param name
     * @param defObj 默认值
     * @return
     */
    public static Object getObjValue(Object obj, String name, Object defObj) {
        Object valueObj = null;
        String methodName = getProNameMethod(name);
        Method method = transferMethoder(obj, methodName, new Class[0]);
        if (null != method) {
            try {
                valueObj = method.invoke(obj);
                if (null == valueObj) {
                    valueObj = defObj;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return valueObj;
    }

    /**
     * 赋值对象里对应的属性值(通过set方法)
     *
     * @param obj
     * @param name
     * @param defObj 值
     * @return
     */
    public static void setObjValue(Object obj, String name, Object defObj) {
        String methodName = getProSetNameMethod(name);
        try {
            Field field = getDeclaredField(obj, name);
            if (null == field) {
                return;
            }
            Class fclass = field.getType();
            Object valueobj = getValueByType(fclass, defObj);
            Class[] types = {fclass};
            Method method = transferMethoder(obj, methodName, types);
            if (null != method) {
                method.invoke(obj, valueobj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param cls
     * @param defObj
     * @return
     */
    public static Object getValueByType(Class cls, Object defObj) {
        Object obj = defObj;
        if (cls == null) {
            return obj;
        }
        String className = cls.getName();
        if (null == className) {
            return obj;
        }
        if (className.indexOf("[") == 0) {
            return obj;
        } else if (null != defObj && defObj.getClass().getName().equals(className)) {
            return obj;
        } else {
            if (className.indexOf("String") >= 0) {
                if (null == defObj) {
                    obj = null;
                } else {
                    obj = defObj + "";
                }
            } else if (className.indexOf("int") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    defObj = "0";
                }
                obj = Long.valueOf(String.valueOf(defObj)).intValue();
            } else if (className.indexOf("Long") >= 0 || className.indexOf("long") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    defObj = "0";
                }
                obj = Long.valueOf(String.valueOf(defObj));
            } else if (className.indexOf("Number") >= 0 || className.indexOf("number") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    defObj = "0";
                }
                obj = Long.valueOf(String.valueOf(defObj));
            } else if (className.indexOf("Double") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    defObj = "0";
                }
                obj = Double.valueOf(String.valueOf(defObj));
            } else if (className.indexOf("double") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    defObj = "0";
                }
                obj = Double.valueOf(String.valueOf(defObj));
            } else if (className.indexOf("Date") >= 0) {
                if (null != defObj && StringUtils.isNotEmpty(String.valueOf(defObj))) {
                    if (String.valueOf(defObj).length() > 10) {
                        obj = DateUtils.getDateToString(String.valueOf(defObj), DateUtils.DATETIMESHOWFORMAT);
                    } else {
                        obj = DateUtils.getDateToString(String.valueOf(defObj), DateUtils.DATESHOWFORMAT);
                    }
                    if (obj == null) {
                        obj = defObj;
                    }
                }
            } else if (className.indexOf("Integer") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    defObj = "0";
                }
                obj = Integer.valueOf(String.valueOf(defObj));
            } else if (className.indexOf("boolean") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    defObj = "false";
                }
                if ("true".equals(String.valueOf(defObj))) {
                    obj = true;
                } else {
                    obj = false;
                }
            } else if (className.indexOf("Boolean") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    defObj = "false";
                }
                if ("true".equals(String.valueOf(defObj))) {
                    obj = true;
                } else {
                    obj = false;
                }
            } else if (cls.isEnum()) {
                obj = Enum.valueOf(cls, defObj.toString());
            } else if (className.indexOf("BigDecimal") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    //obj = BigDecimal.ZERO;
                } else {
                    obj = new BigDecimal(String.valueOf(defObj));
                }
            } else if (className.indexOf("Short") >= 0) {
                if (null == defObj || StringUtils.isEmpty(String.valueOf(defObj))) {
                    defObj = "0";
                }
                obj = Short.valueOf(String.valueOf(defObj));
            }
        }
        return obj;
    }

    /**
     * 赋值对象里对应的属性值(通过set方法) defObj固定式string,要转换
     *
     * @param obj
     * @param name
     * @param defObj 值
     * @return
     */
    public static void setObjValue(Object obj, String name, String defObj) {
        String methodName = getProSetNameMethod(name);
        try {
            Field field = getDeclaredField(obj, name);
            Class fclass = field.getType();
            Class[] types = {fclass};
            Method method = transferMethoder(obj, methodName, types);
            if (null != method) {
                method.invoke(obj, getStringToType(fclass, defObj));
            }
        } catch (Exception e) {
        }
    }

    public static Object getObject(Object obj, String name, String defObj) {
        String methodName = getProSetNameMethod(name);
        try {
            Field field = getDeclaredField(obj, name);
            Class fclass = field.getType();
            Class[] types = {fclass};
            return getStringToType(fclass, defObj);
        } catch (Exception e) {
        }
        return null;
    }

    public static String getObjectHql(Object obj, String name, List<Object> paramlist, Object value) {
        String methodName = getProSetNameMethod(name);
        try {
            Field field = getDeclaredField(obj, name);
            Class fclass = field.getType();
            Class[] types = {fclass};
            return getStringToHql(fclass, name, paramlist, value);
        } catch (Exception e) {
        }
        return null;
    }

    /**
     * 把string 转化成对应的类型
     *
     * @param typeClass
     * @param value
     * @return
     */
    public static Object getStringToType(Class typeClass, String value) {
        Object obj = null;
        if (typeClass.equals(String.class)) {
            if (null == value || StringUtils.isEmpty(value)) {
                obj = "";
            } else {
                obj = String.valueOf(value);
            }
        } else if (typeClass.equals(Double.class)) {
            if (null == value || StringUtils.isEmpty(value)) {
                obj = 0D;
            } else {
                obj = Double.valueOf(value);
            }
        } else if (typeClass.equals(Integer.class)) {
            if (null == value || StringUtils.isEmpty(value)) {
                obj = 0;
            } else {
                obj = Integer.valueOf(value);
            }
        } else if (typeClass.equals(Date.class)) {
            if (null == value || StringUtils.isEmpty(value)) {
                obj = null;
            } else {
                obj = DateUtils.getDateToString(value, DateUtils.DATETIMESHOWFORMAT);
            }
        } else if (typeClass.equals(Long.class)) {
            if (null == value || StringUtils.isEmpty(value)) {
                obj = 0L;
            } else {
                obj = Long.valueOf(value);
            }
        } else if (typeClass.equals(BigDecimal.class)) {
            if (null == value || StringUtils.isEmpty(value)) {
                //obj = BigDecimal.ZERO;
            } else {
                obj = new BigDecimal(value);
            }
        } else {
            obj = 0;
        }
        return obj;
    }

    @SuppressWarnings("deprecation")
    public static String getStringToHql(Class typeClass, String name, List<Object> paramlist, Object value) {
        String obj = null;
        if (typeClass.equals(String.class)) {
            obj = "'--'";
            paramlist.add(null == value || "".equals(value) ? "--" : value);
        } else if (typeClass.equals(Double.class)) {
            obj = "0";
            paramlist.add(null == value || "".equals(value) ? 0D : value);
        } else if (typeClass.equals(Integer.class)) {
            obj = "0";
            paramlist.add(null == value || "".equals(value) ? 0 : value);
        } else if (typeClass.equals(Date.class)) {
            obj = "to_date('1991.01.01','yyyy.mm.dd')";
            paramlist.add(null == value || "".equals(value) ? new Date("1991.01.01") : value);
        } else if (typeClass.equals(Long.class)) {
            obj = "0";
            paramlist.add(null == value || "".equals(value) ? 0L : value);
        } else {
            obj = "0";
            paramlist.add(null == value || "".equals(value) ? 0 : value);
        }
        return obj;
    }

    public static <T> T copyAllPropertysNotNull(T arg0, Object arg1) throws Exception {
        if (null != arg0 && null != arg1) {
            Object value = null;
            if (arg1 instanceof Map) {
                for (String key : ((Map<String, Object>) arg1).keySet()) {
                    value = BeanUtils.forceGetProperty(arg1, key);
                    if (value == null) {
                        continue;
                    }
                    BeanUtils.forceSetProperty(arg0, key, value);
                }
            } else {
                Field[] field = getObjAllProperty(arg1);
                if (null != field) {
                    for (int i = 0; i < field.length; i++) {
                        value = BeanUtils.forceGetProperty(arg1, field[i].getName());
                        if (value == null) {
                            continue;
                        }
                        BeanUtils.forceSetProperty(arg0, field[i].getName(), value);
                    }
                }
            }
        } else {
            throw new Exception("参数为空");
        }
        return arg0;
    }

    private static List<Field> getFieldList(Class clazz, List<Field> fieldList) {
        Field[] fields = clazz.getDeclaredFields();
        fieldList.addAll(Arrays.asList(fields));
        Field[] pFields = clazz.getSuperclass().getDeclaredFields();
        if (pFields != null && pFields.length > 0) {
            fieldList.addAll(Arrays.asList(pFields));
            getFieldList(clazz.getSuperclass(), fieldList);
        } else {
            return fieldList;
        }
        return fieldList;
    }

    public static String getFirstUpper(String str) {
        String newStr = "";
        if (str.length() > 0) {
            newStr = str.substring(0, 1).toUpperCase() + str.substring(1, str.length());
        }
        return newStr;
    }
}
