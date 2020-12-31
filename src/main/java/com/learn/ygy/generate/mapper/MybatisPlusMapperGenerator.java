package com.learn.ygy.generate.mapper;

import com.learn.ygy.generate.GeneratorContext;
import freemarker.template.Template;

import java.io.IOException;

/**
 * @Author yanguangyuan
 * @Description mybatis plus mapper生成
 * @createTime 2020年12月31日 11:46:00
 */
public class MybatisPlusMapperGenerator extends AbstractMapperGenerator {

    public MybatisPlusMapperGenerator(GeneratorContext context) {
        super(context);
    }

    @Override
    protected Template getTemplate() throws IOException {
        return configuration.getTemplate("MybatisPlusMapper.ftl");
    }
}
