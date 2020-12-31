package com.learn.ygy.generate.mapper.entity;

import com.learn.ygy.generate.GeneratorContext;
import freemarker.template.Template;

import java.io.IOException;

/**
 * @Author yanguangyuan
 * @Description mybatis do 生成
 * @createTime 2020年12月31日 13:37:00
 */
public class MybatisDoGenerator extends AbstractDoGenerator {
    public MybatisDoGenerator(GeneratorContext context) {
        super(context);
    }

    @Override
    protected Template getTemplate() throws IOException {
        return configuration.getTemplate("MybatisDO.ftl");
    }
}
