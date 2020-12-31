package com.learn.ygy.generate.service;

import com.learn.ygy.generate.AbstractGenerator;
import com.learn.ygy.generate.GeneratorContext;

/**
 * @Author yanguangyuan
 * @Description 抽象的mapper生成
 * @createTime 2020年12月31日 11:44:00
 */
public abstract class AbstractServiceGenerator extends AbstractGenerator {

    public AbstractServiceGenerator(GeneratorContext context) {
        super(context);
    }

    @Override
    protected String getFilePath() {
        return servicePath;
    }

    @Override
    protected Object getPackageName() {
        return servicePackage;
    }

    @Override
    protected String getClassSimpleName() {
        return serviceClassSimpleName;
    }
}
