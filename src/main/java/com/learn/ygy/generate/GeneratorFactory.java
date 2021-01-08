package com.learn.ygy.generate;

import com.learn.ygy.enums.VersionEnum;
import com.learn.ygy.generate.mapper.MybatisPlusMapperGenerator;
import com.learn.ygy.generate.mapper.NamedParameterJdbcTemplateDaoGenerator;
import com.learn.ygy.generate.mapper.entity.MybatisDoGenerator;
import com.learn.ygy.generate.mapper.entity.MybatisPlusDoGenerator;
import com.learn.ygy.generate.service.MybatisPlusServiceGenerator;
import com.learn.ygy.generate.service.entity.DtoGenerator;

/**
 * @Author yanguangyuan
 * @Description 生成器工厂
 * @createTime 2020年12月31日 11:28:00
 */
public class GeneratorFactory {

    /**
     * 获取do生成器
     *
     * @param context
     * @return
     */
    public static IGenerator doGenerator(GeneratorContext context) {
        if (context.getVersionEnum() == VersionEnum.MYBATIS_PLUS) {
            return new MybatisPlusDoGenerator(context);
        }
        return new MybatisDoGenerator(context);
    }

    /**
     * 获取mapper生成器
     *
     * @param context
     * @return
     */
    public static IGenerator mapperGenerator(GeneratorContext context) {
        if (context.getVersionEnum() == VersionEnum.NAMED_PARAMETER_JDBC_TEMPLATE) {
            return new NamedParameterJdbcTemplateDaoGenerator(context);
        }
        return new MybatisPlusMapperGenerator(context);
    }

    /**
     * service 生成器
     * @param context
     * @return
     */
    public static IGenerator serviceGenerator(GeneratorContext context) {
        return new MybatisPlusServiceGenerator(context);
    }

    public static IGenerator dtoGenerator(GeneratorContext context) {
        return new DtoGenerator(context);
    }
}
