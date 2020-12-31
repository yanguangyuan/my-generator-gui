package com.learn.ygy.db;

import com.alibaba.fastjson.JSON;
import com.learn.ygy.entity.DbConfig;
import com.learn.ygy.entity.Table;
import com.learn.ygy.utils.AlertUtils;
import com.learn.ygy.utils.ResourcesUtils;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author yanguangyuan
 * @Description sqlite
 * @createTime 2020年12月25日 18:08:00
 */
@Slf4j
public class Sqlite extends Database {

    public Sqlite(DbConfig dbConfig) {
        super(dbConfig);
    }

    public Sqlite() {
        super(new DbConfig());
    }

    @Override
    protected void loadDriver() throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
    }

    @Override
    protected String getUrl() {
        URL load = ResourcesUtils.load("db/sqlite3.db");
        return "jdbc:sqlite:" + load.getPath();
    }

    @Override
    public List<String> listTables() {
        return null;
    }


    public void saveDbConfig(DbConfig dbConfig) throws Exception {
        DbConfig exist = queryDbConfigByName(dbConfig.getName());
        if (null == exist) {
            insertDbConfig(dbConfig);
        } else {
            if (null != dbConfig.getId()) {
                updateDbConfig(dbConfig);
            } else {
                AlertUtils.fail("已有此名称");
            }

        }
    }

    private void insertDbConfig(DbConfig dbConfig) throws Exception {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("insert into dbs(name,value) values('%s','%s')", dbConfig.getName(), JSON.toJSONString(dbConfig));
            statement.executeUpdate(sql);
        } finally {
            connection.close();
        }
    }

    private void updateDbConfig(DbConfig dbConfig) throws Exception {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            String sql = String.format("update dbs set value='%s',name='%s' where id='%s'", JSON.toJSONString(dbConfig), dbConfig.getName(), dbConfig.getId());
            statement.executeUpdate(sql);
        } finally {
            connection.close();
        }
    }

    public List<DbConfig> queryDbConfigs() throws Exception {
        Connection connection = getConnection();
        List<DbConfig> result = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from dbs");
            while (resultSet.next()) {
                String value = resultSet.getString("value");
                int id = resultSet.getInt("id");
                DbConfig dbConfig = JSON.parseObject(value, DbConfig.class);
                dbConfig.setId(id);
                result.add(dbConfig);
            }
        } finally {
            connection.close();
        }
        return result;
    }

    public DbConfig queryDbConfigByName(String name) throws Exception {
        Connection connection = getConnection();
        DbConfig result;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from dbs where name ='%s'", name));
            if (resultSet.next()) {
                String value = resultSet.getString("value");
                int id = resultSet.getInt("id");
                result = JSON.parseObject(value, DbConfig.class);
                result.setId(id);
            } else {
                return null;
            }
        } finally {
            connection.close();
        }
        return result;
    }

    public DbConfig queryDbConfigById(String id) throws Exception {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("select * from dbs where id ='%s'", id));
            if (resultSet.next()) {
                String value = resultSet.getString("value");
                return JSON.parseObject(value, DbConfig.class);
            } else {
                return null;
            }
        } finally {
            connection.close();
        }
    }

    public void delete(DbConfig dbConfig) throws Exception {
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("delete from dbs where id ='%s'", dbConfig.getId()));
        } finally {
            connection.close();
        }

    }
}
