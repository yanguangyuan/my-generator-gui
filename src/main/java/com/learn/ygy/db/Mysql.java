package com.learn.ygy.db;

import com.learn.ygy.entity.Column;
import com.learn.ygy.entity.DbConfig;
import com.learn.ygy.entity.Table;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author yanguangyuan
 * @Description mysql 处理
 * @createTime 2020年12月25日 17:21:00
 */
@Slf4j
public class Mysql extends Database {

    public Mysql(DbConfig dbConfig) {
        super(dbConfig);
    }

    @Override
    protected void loadDriver() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
    }

    @Override
    protected String getUrl() {
        return String.format("jdbc:mysql://%s:%s/%s", dbConfig.getHost(), dbConfig.getPort(), dbConfig.getSchema());
    }

    @Override
    public List<String> listTables() throws Exception {
        List<String> result = new ArrayList<>(100);
        Connection connection = getConnection();
        DatabaseMetaData metaData = connection.getMetaData();
        ResultSet rs = metaData.getTables(dbConfig.getSchema(), null, "%", new String[]{"TABLE", "VIEW"});
        while (rs.next()) {
            result.add(rs.getString(3));
        }
        return result;
    }


}
