package com.learn.ygy.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.JDBCType;

/**
 * @Author yanguangyuan
 * @Description 表实体
 * @createTime 2020年12月30日 17:18:00
 */
@Getter
@Setter
@ToString
public class ColumnEntity {

    /**
     * 列名
     */
    private String columnName;
    /**
     * 列对应属性名
     */
    private String propertyName;
    /**
     * 类型代码
     */
    private JDBCType type;
    /**
     * 列属性对应的java类型
     */
    private Class<?> javaType;
    /**
     * javaTypeName
     */
    private String javaTypeName;
    /**
     * 属性类型名称
     */
    private String propertyTypeName;
    /**
     * 列备注
     */
    private String remarks;
    /**
     * 是否主键
     */
    private Boolean isPrimaryKey;
}
