package com.learn.ygy.db;

import com.learn.ygy.entity.Column;
import com.learn.ygy.entity.DbConfig;
import com.learn.ygy.entity.Table;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Author yanguangyuan
 * @Description 抽象数据库
 * @createTime 2020年12月25日 17:11:00
 */
@AllArgsConstructor
@Slf4j
public abstract class Database {
    protected DbConfig dbConfig;

    /**
     * 加载驱动
     *
     * @throws ClassNotFoundException
     */
    protected abstract void loadDriver() throws ClassNotFoundException;

    /**
     * 获取url
     *
     * @return
     */
    protected abstract String getUrl();

    public Connection getConnection() throws Exception {
        loadDriver();
        return DriverManager.getConnection(getUrl(), dbConfig.getUsername(), dbConfig.getPassword());
    }

    /**
     * 列出数据库表名
     * @return
     * @throws Exception
     */
    public abstract List<String> listTables() throws Exception;

    /**
     * 通过tablename获取列信息
     *
     * @param tableName
     * @return
     */
    public  Table getTableByName(String tableName){
        Table result = new Table();
        result.setTableName(tableName);
        try {
            Connection connection = getConnection();
            result.setRemarks(tableRemarks(connection,tableName));
            result.setColumns(listColumns(connection,tableName));
        } catch (Exception e) {
            log.error("获取表信息异常", e);
        }
        return result;
    }
    private List<Column> listColumns(Connection connection,String tableName) throws Exception{
        DatabaseMetaData metaData = connection.getMetaData();
        Set<String> primaryKeySet = primaryKeyColumns(tableName,metaData);
        List<Column> columns = new ArrayList<>(50);
        ResultSet columnRs = connection.getMetaData().getColumns(null, metaData.getUserName(), tableName.toUpperCase(), "%");
        while (columnRs.next()) {
            Column column = new Column();
            column.setColumnName(columnRs.getString("COLUMN_NAME"));
            column.setType(JDBCType.valueOf(columnRs.getInt("DATA_TYPE")));
            column.setRemarks(columnRs.getString("REMARKS"));
            column.setIsPrimaryKey(primaryKeySet.contains(column.getColumnName()));
            columns.add(column);
        }
        return columns;
    }


    private String tableRemarks(Connection connection,String tableName) throws Exception{
//        ResultSet tablesRs = metaData.getTables(null, metaData.getUserName(), tableName.toUpperCase(), new String[]{"TABLE"});
//        if (tablesRs.next()) {
//            result.setRemarks(tablesRs.getString("REMARKS"));
//        }
        ResultSet resultSet = connection.createStatement().executeQuery("SHOW CREATE TABLE " + tableName);
        if(resultSet.next()){
            String create = resultSet.getString(2);
            return parseCreate2TableRemarks(create);
        }
        return "";
    }

    protected  String parseCreate2TableRemarks(String create){
        int i = create.lastIndexOf("COMMENT=");
        if(i<=0){
            return "";
        }
        return create.substring(i);
    }


    private Set<String> primaryKeyColumns(String tableName,DatabaseMetaData metaData) throws Exception{
        ResultSet keyRs = metaData.getPrimaryKeys(null, metaData.getUserName(), tableName.toLowerCase());
        Set<String> result = new HashSet<>(2);
        while (keyRs.next()) {
            result.add(keyRs.getObject(4).toString());
        }
        return result;
    }

}
