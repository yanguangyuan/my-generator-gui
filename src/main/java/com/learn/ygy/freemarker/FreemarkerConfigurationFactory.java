package com.learn.ygy.freemarker;

import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * @Author yanguangyuan
 * @Description Freemarker Configuration 工厂
 * @createTime 2020年12月30日 18:06:00
 */
@Slf4j
public class FreemarkerConfigurationFactory {
    /**
     * 配置
     */
    private static Configuration configuration;

    /**
     * 获取实例
     *
     * @return
     */
    public static Configuration getInstance() {
        if (null == configuration) {
            synchronized (FreemarkerConfigurationFactory.class) {
                if (null == configuration) {
                    configuration = new Configuration(Configuration.VERSION_2_3_23);
                    try {
                        String path = new File(FreemarkerConfigurationFactory.class.getClassLoader().getResource("ftl").getFile()).getPath();
                        configuration.setDirectoryForTemplateLoading(new File(path));
                    } catch (IOException e) {
                        log.error("设置Freemarker默认配置路径异常", e);
                    }
                    configuration.setEncoding(Locale.CHINA, "utf-8");
                }
            }
        }
        return configuration;
    }
}
