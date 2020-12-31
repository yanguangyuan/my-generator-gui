package com.learn.ygy.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @Author yanguangyuan
 * @Description 时间工具
 * @createTime 2020年11月09日 11:36:00
 */
@Slf4j
public class DateUtils {


    public static String format(Date date, String pattern) {
        String result = "";
        if (date == null) {
            return result;
        }
        try {
            result = DateFormatUtils.format(date, pattern);
        } catch (Exception e) {
            log.error("时间format异常,date={},pattern={},exception={}", date, pattern, e.getMessage());
        }
        return result;
    }

    public static Date parse(String str, String pattern) {
        Date result = null;
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        try {
            result = org.apache.commons.lang3.time.DateUtils.parseDate(str, pattern);
        } catch (Exception e) {
            log.error("时间parse异常,str={},pattern={},exception={}", str, pattern, e.getMessage());
        }
        return result;

    }
}
