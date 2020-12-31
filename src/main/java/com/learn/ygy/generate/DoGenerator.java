package com.learn.ygy.generate;

import com.learn.ygy.entity.TableEntity;
import com.learn.ygy.utils.DateUtils;
import freemarker.template.Template;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author yanguangyuan
 * @Description 生成xxxDO
 * @createTime 2020年12月30日 17:57:00
 */
public class DoGenerator extends AbstractGenerator {

    public DoGenerator(TableEntity tableEntity, String author, String daoPath, String servicePath, String controllerPath) {
        super(tableEntity, author, daoPath, servicePath, controllerPath);
    }

    @Override
    protected String getFilePath() {
        return daoPath + "\\entity";
    }

    @Override
    protected String getClassName() {
        return tableEntity.getTableBeanName() + "DO";
    }


    @Override
    protected Object getTemplateData() {
        Map<String, Object> result = new HashMap<>(20);
        result.put("TableName", tableEntity.getTableName());
        result.put("ClassName", getClassName());
        result.put("PackageName", getPackageByFilePath());
        result.put("Author", author);
        result.put("Remarks", tableEntity.getRemarks());
        result.put("Date", DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        result.put("ColumnEntities", tableEntity.getColumnEntities());
        result.put("PropertyJavaTypeSet", tableEntity.getPropertyJavaTypeSet());
        return result;
    }

    @Override
    protected Template getTemplate() throws IOException {
        return configuration.getTemplate("do.ftl");
    }
}
