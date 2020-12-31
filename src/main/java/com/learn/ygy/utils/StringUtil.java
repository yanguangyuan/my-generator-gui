package com.learn.ygy.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author yanguangyuan
 * @Description 字符串工具
 * @createTime 2020年12月30日 17:25:00
 */
public class StringUtil {
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 下划线转大驼峰
     *
     * @param str
     * @return
     */
    public static String underline2BigHump(String str) {
        String s = underline2SmallHump(str);
        char[] cs = s.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
    /**
     * 下划线转小驼峰
     *
     * @param str
     * @return
     */
    public static String underline2SmallHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
