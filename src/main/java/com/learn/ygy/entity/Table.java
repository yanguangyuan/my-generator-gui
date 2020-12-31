package com.learn.ygy.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Author yanguangyuan
 * @Description 表信息
 * @createTime 2020年12月30日 16:51:00
 */
@Getter
@Setter
@ToString
public class Table {
    /**
     * 表名称
     */
    private String tableName;
    /**
     * 表注释
     */
    private String remarks;
    /**
     * 列信息
     */
    private List<Column> columns;
}
