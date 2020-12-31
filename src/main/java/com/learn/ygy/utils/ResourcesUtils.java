package com.learn.ygy.utils;

import java.net.URL;

/**
 * @Author yanguangyuan
 * @Description resources工具
 * @createTime 2020年12月25日 15:44:00
 */
public class ResourcesUtils {

    public static URL load(String name) {
        return ResourcesUtils.class.getClassLoader().getResource(name);
    }

}
