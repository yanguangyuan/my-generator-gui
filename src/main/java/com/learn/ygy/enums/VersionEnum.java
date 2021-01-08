package com.learn.ygy.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author yanguangyuan
 * @Description 版本枚举
 * @createTime 2021年01月08日 16:02:00
 */
@Getter
@AllArgsConstructor
public enum VersionEnum {
    /**
     * mybatis
     */
    MYBATIS("mybatis"),
    /**
     * mp
     */
    MYBATIS_PLUS("mybatis-plus"),
    /**
     * NamedParameterJdbcTemplate
     */
    NAMED_PARAMETER_JDBC_TEMPLATE("NamedParameterJdbcTemplate");
    private String value;

    public static List<String> getEnumValues() {
        return Arrays.stream(VersionEnum.values()).map(VersionEnum::getValue).collect(Collectors.toList());
    }

    public static VersionEnum getByValue(String value) {
        for (VersionEnum versionEnum : VersionEnum.values()) {
            if (versionEnum.value.equals(value)) {
                return versionEnum;
            }
        }
        return null;
    }
}
