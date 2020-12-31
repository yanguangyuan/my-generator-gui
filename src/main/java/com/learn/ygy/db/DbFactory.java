package com.learn.ygy.db;

import com.learn.ygy.entity.DbConfig;

/**
 * @Author yanguangyuan
 * @Description 工厂
 * @createTime 2020年12月28日 17:02:00
 */
public class DbFactory {

    public static Database getDatabase(DbConfig config){
        return new Mysql(config);
    }
}
