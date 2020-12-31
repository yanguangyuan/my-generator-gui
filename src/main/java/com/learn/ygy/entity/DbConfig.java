package com.learn.ygy.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author yanguangyuan
 * @Description 数据库配置对象
 * @createTime 2020年12月25日 16:52:00
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class DbConfig {
    private Integer id;
    private String name;
    private String host;
    private String port;
    private String username;
    private String password;
    private String schema;
}
