package com.learn.ygy.generate.mapper;

import com.learn.ygy.generate.GeneratorContext;
import freemarker.template.Template;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yanguangyuan
 * @Description NamedParameterJdbcTemplate
 * @createTime 2021年01月08日 10:45:00
 */
public class NamedParameterJdbcTemplateDaoGenerator extends AbstractMapperGenerator{

    public NamedParameterJdbcTemplateDaoGenerator(GeneratorContext context) {
        super(context);
    }
    @Override
    protected String getClassSimpleName() {
        return mapperClassSimpleName.replace("Mapper","Dao");
    }
    @Override
    protected Template getTemplate() throws IOException {
        return configuration.getTemplate("NamedParameterJdbcTemplateDao.ftl");
    }
    @Override
    protected Map<String, Object> getTemplateData1() {
        Map<String, Object> result = new HashMap<>(10);
        result.put("TableName", tableEntity.getTableName());
        result.put("ColumnEntities", tableEntity.getColumnEntities());
        return result;
    }
}
