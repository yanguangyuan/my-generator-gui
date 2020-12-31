package com.learn.ygy.entity;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.JDBCType;

/**
 * @Author yanguangyuan
 * @Description 数据库列对象
 * @createTime 2020年12月25日 16:52:00
 */
@Getter
@Setter
@ToString
public class Column {
    /**
     * 列名
     */
    private String columnName;
    /**
     * 类型代码
     */
    private JDBCType type;
    /**
     * 列备注
     */
    private String remarks;
    /**
     * 是否主键
     */
    private Boolean isPrimaryKey;

}
