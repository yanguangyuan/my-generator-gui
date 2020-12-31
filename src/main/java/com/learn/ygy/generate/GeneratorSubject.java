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
 * @Description 生成文件处理
 * @createTime 2020年12月30日 15:46:00
 */
public class GeneratorSubject {


    public void process(GeneratorConfig config) {
        config.getCheckTables().forEach((db, tableNames) -> tableNames.forEach(tableName -> {
            Table table = DbFactory.getDatabase(db).getTableByName(tableName);
            TableEntity tableEntity = transformTable2Entity(table);
            if (Boolean.TRUE.equals(config.getDoCheck())){
                IGenerator generator = new DoGenerator(tableEntity,config.getAuthor(),config.getDaoPath(),config.getServicePath(),config.getControllerPath());
                generator.process();
            }
        }));
        AlertUtils.success("生成完成！");
    }

    private TableEntity transformTable2Entity(Table table) {
        TableEntity result = new TableEntity();
        result.setTableName(table.getTableName());
        result.setRemarks(table.getRemarks());
        result.setTableBeanName(StringUtil.underline2BigHump(table.getTableName()));
        result.setColumnEntities(table.getColumns().stream().map(this::transformColumn2Entity).collect(Collectors.toList()));
        result.setPropertyJavaTypeSet(result.getColumnEntities().stream().map(ColumnEntity::getJavaTypeName).collect(Collectors.toSet()));
        return result;
    }

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
     * 类型转化
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
