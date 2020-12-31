package com.learn.ygy.generate.mapper;

import com.learn.ygy.generate.AbstractGenerator;
import com.learn.ygy.generate.GeneratorContext;

/**
 * @Author yanguangyuan
 * @Description 抽象的mapper生成
 * @createTime 2020年12月31日 11:44:00
 */
public abstract class AbstractMapperGenerator extends AbstractGenerator {

    public AbstractMapperGenerator(GeneratorContext context) {
        super(context);
    }

    @Override
    protected String getFilePath() {
        return daoPath;
    }

    @Override
    protected Object getPackageName() {
        return mapperPackage;
    }

    @Override
    protected String getClassSimpleName() {
        return mapperClassSimpleName;
    }
}
