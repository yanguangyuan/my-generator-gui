package com.learn.ygy.generate;

import com.learn.ygy.db.DbFactory;
import com.learn.ygy.entity.*;
import com.learn.ygy.utils.AlertUtils;
import com.learn.ygy.utils.StringUtil;

import java.math.BigDecimal;
import java.sql.JDBCType;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @Author yanguangyuan
 * @Description 生成文件对外开放
 * @createTime 2020年12月30日 15:46:00
 */
public class GeneratorSubject {

    /**
     * 对外开发处理 通过ui参数生成文件
     *
     * @param config
     */
    public void process(GeneratorConfig config) {
        config.getCheckTables().forEach((db, tableNames) -> tableNames.forEach(tableName -> {
            GeneratorContext context = buildGeneratorContext(config, db, tableName);
            //生成do
            if (Boolean.TRUE.equals(config.getDoCheck())) {
                IGenerator generator = GeneratorFactory.doGenerator(context);
                generator.process();
            }
            //生成mapper
            if (Boolean.TRUE.equals(config.getMapperCheck())) {
                IGenerator generator = GeneratorFactory.mapperGenerator(context);
                generator.process();
            }
            //生成service
            if (Boolean.TRUE.equals(config.getServiceCheck())) {
                IGenerator generator = GeneratorFactory.serviceGenerator(context);
                generator.process();
            }
            //生成dto
            if (Boolean.TRUE.equals(config.getDtoCheck())) {
                IGenerator generator = GeneratorFactory.dtoGenerator(context);
                generator.process();
            }
        }));
        AlertUtils.success("生成完成！");
    }

    /**
     * 构建生成上下文
     *
     * @param config
     * @param db
     * @param tableName
     * @return
     */
    private GeneratorContext buildGeneratorContext(GeneratorConfig config, DbConfig db, String tableName) {
        Table table = DbFactory.getDatabase(db).getTableByName(tableName);
        TableEntity tableEntity = transformTable2Entity(table);
        return GeneratorContext.builder()
                .author(config.getAuthor())
                .tableEntity(tableEntity)
                .daoPath(config.getDaoPath())
                .servicePath(config.getServicePath())
                .controllerPath(config.getControllerPath())
                .build();
    }

    /**
     * 将数据库表转为java属性表
     *
     * @param table
     * @return
     */
    private TableEntity transformTable2Entity(Table table) {
        TableEntity result = new TableEntity();
        result.setTableName(table.getTableName());
        result.setRemarks(table.getRemarks());
        result.setTableBeanName(StringUtil.underline2BigHump(table.getTableName()));
        result.setColumnEntities(table.getColumns().stream().map(this::transformColumn2Entity).collect(Collectors.toList()));
        result.setPropertyJavaTypeSet(result.getColumnEntities().stream().map(ColumnEntity::getJavaTypeName).collect(Collectors.toSet()));
        return result;
    }

    /**
     * 将数据库列转为 java属性列
     *
     * @param column
     * @return
     */
    private ColumnEntity transformColumn2Entity(Column column) {
        ColumnEntity result = new ColumnEntity();
        result.setColumnName(column.getColumnName());
        result.setPropertyName(StringUtil.underline2SmallHump(column.getColumnName()));
        result.setIsPrimaryKey(column.getIsPrimaryKey());
        result.setRemarks(column.getRemarks());
        result.setType(column.getType());
        result.setJavaType(transformColumnType2Class(column.getType()));
        result.setJavaTypeName(result.getJavaType().getName());
        result.setPropertyTypeName(result.getJavaType().getSimpleName());
        return result;
    }

    /**
     * 表字段类型与java类型转化
     *
     * @param type
     * @return
     */
    private Class<?> transformColumnType2Class(JDBCType type) {
        switch (type) {
            case BIT:
                return Boolean.class;
            case TINYINT:
            case SMALLINT:
            case INTEGER:
                return Integer.class;
            case CHAR:
            case VARCHAR:
            case LONGVARCHAR:
                return String.class;
            case DATE:
            case TIMESTAMP:
                return Date.class;
            case BIGINT:
                return Long.class;
            case FLOAT:
                return Float.class;
            case DOUBLE:
                return Double.class;
            case DECIMAL:
                return BigDecimal.class;
            default:
                throw new RuntimeException(String.format("未有此jdbc=%s类型转化", type));
        }
    }

}
