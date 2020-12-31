package com.learn.ygy.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

/**
 * @Author yanguangyuan
 * @Description 表实体
 * @createTime 2020年12月30日 17:18:00
 */
@Getter
@Setter
@ToString
public class TableEntity {
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表名称对应的java名称 下划线转大驼峰
     */
    private String tableBeanName;
    /**
     * 表注释
     */
    private String remarks;
    /**
     * 列信息
     */
    private List<ColumnEntity> columnEntities;
    /**
     * 属性类型set
     */
    private Set<String> propertyJavaTypeSet;
}
