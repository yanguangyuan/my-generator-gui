package com.learn.ygy.generate.service.entity;

import com.learn.ygy.generate.AbstractGenerator;
import com.learn.ygy.generate.GeneratorContext;
import freemarker.template.Template;

import java.io.IOException;

/**
 * @Author yanguangyuan
 * @Description dto 生成器
 * @createTime 2020年12月31日 13:21:00
 */
public class DtoGenerator extends AbstractGenerator {
    public DtoGenerator(GeneratorContext context) {
        super(context);
    }

    @Override
    protected String getFilePath() {
        return dtoPath;
    }

    @Override
    protected String getClassSimpleName() {
        return dtoClassSimpleName;
    }

    @Override
    protected Object getPackageName() {
        return dtoPackage;
    }

    @Override
    protected Template getTemplate() throws IOException {
        return configuration.getTemplate("MybatisPlusDTO.ftl");

    }
}
