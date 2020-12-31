package com.learn.ygy.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

/**
 * @Author yanguangyuan
 * @Description 生成配置
 * @createTime 2020年12月30日 14:56:00
 */
@Getter
@Setter
@Builder
public class GeneratorConfig {
    private Map<DbConfig, Set<String>> checkTables;
    private String author;
    private String daoPath;
    private String servicePath;
    private String controllerPath;
    private Boolean mapperCheck;
    private Boolean doCheck;
    private Boolean serviceCheck;
    private Boolean dtoCheck;
    private Boolean controllerCheck;
    private Boolean voCheck;
}
