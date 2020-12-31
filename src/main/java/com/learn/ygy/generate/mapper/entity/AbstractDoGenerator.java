package com.learn.ygy.generate.mapper.entity;

import com.learn.ygy.entity.TableEntity;
import com.learn.ygy.generate.AbstractGenerator;
import com.learn.ygy.generate.GeneratorContext;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author yanguangyuan
 * @Description 生成xxxDO
 * @createTime 2020年12月30日 17:57:00
 */
public abstract class AbstractDoGenerator extends AbstractGenerator {

    public AbstractDoGenerator(GeneratorContext context) {
        super(context);
    }

    @Override
    protected String getFilePath() {
        return this.doPath;
    }

    @Override
    protected String getClassSimpleName() {
        return this.doClassSimpleName;
    }

    @Override
    protected Object getPackageName() {
        return this.doPackage;
    }
    @Override
    protected Map<String, Object> getTemplateData1() {
        Map<String, Object> result = new HashMap<>(10);
        result.put("TableName", tableEntity.getTableName());
        result.put("ColumnEntities", tableEntity.getColumnEntities());
        result.put("PropertyJavaTypeSet", tableEntity.getPropertyJavaTypeSet());
        return result;
    }

}
