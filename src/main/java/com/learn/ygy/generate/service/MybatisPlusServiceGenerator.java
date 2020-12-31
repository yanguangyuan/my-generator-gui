package com.learn.ygy.generate.service;

import com.learn.ygy.generate.GeneratorContext;
import freemarker.template.Template;

import java.io.IOException;

/**
 * @Author yanguangyuan
 * @Description mp service
 * @createTime 2020年12月31日 13:05:00
 */
public class MybatisPlusServiceGenerator extends AbstractServiceGenerator {
    public MybatisPlusServiceGenerator(GeneratorContext context) {
        super(context);
    }

    @Override
    protected Template getTemplate() throws IOException {
        return configuration.getTemplate("MybatisPlusService.ftl");
    }
}
