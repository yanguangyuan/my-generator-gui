package com.learn.ygy.generate.mapper.entity;

import com.learn.ygy.generate.GeneratorContext;
import freemarker.template.Template;

import java.io.IOException;

/**
 * @Author yanguangyuan
 * @Description mybatis plus do 生成
 * @createTime 2020年12月31日 11:25:00
 */
public class MybatisPlusDoGenerator extends AbstractDoGenerator {

    public MybatisPlusDoGenerator(GeneratorContext context) {
        super(context);
    }
    @Override
    protected Template getTemplate() throws IOException {
        return configuration.getTemplate("MybatisPlusDO.ftl");
    }
}
