package com.example.exceldemo.utils;


import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具类
 * @author qzz
 */
public class DateUtils {
    /**
     * Format:yyyy-MM-dd
     */
    public static final String DATESHOWFORMAT = "yyyy-MM-dd";

    /**
     * Format:yyyy-MM-dd HH:mm:ss
     */
    public static final String DATETIMESHOWFORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日期格式化(String转换为Date)
     *
     * @param dateStr
     *            日期字符串
     * @param patten
     *            处理结果日期的显示格式，如："YYYY-MM-DD"
     * @return
     */
    public static Date getDateToString(String dateStr, String patten) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(patten,Locale.CHINA);
        try {
            return formatter.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
