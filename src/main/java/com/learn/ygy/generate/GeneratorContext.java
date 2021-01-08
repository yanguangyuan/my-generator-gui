package com.learn.ygy.generate;

import com.learn.ygy.entity.TableEntity;
import com.learn.ygy.enums.VersionEnum;
import freemarker.template.Configuration;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @Author yanguangyuan
 * @Description 生成上下文，用于统一封装参数
 * @createTime 2020年12月31日 11:31:00
 */
@Getter
@Setter
@ToString
@Builder
public class GeneratorContext {
    private VersionEnum versionEnum;
    /**
     * 作者
     */
    private String author;
    /**
     * 表数据
     */
    private TableEntity tableEntity;
    private String daoPath;
    private String servicePath;
    private String controllerPath;
}
