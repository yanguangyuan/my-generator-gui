package com.learn.ygy.controller;

import com.learn.ygy.db.Database;
import com.learn.ygy.entity.DbConfig;
import com.learn.ygy.db.Mysql;
import com.learn.ygy.db.Sqlite;
import com.learn.ygy.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * @Author yanguangyuan
 * @Description 新建连接
 * @createTime 2020年12月25日 14:36:00
 */
@Slf4j
@NoArgsConstructor
public class ConnectionUiController extends BaseUiController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField hostField;
    @FXML
    private TextField portField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField schemaField;

    private Integer dbConfigId;

    public void updateShow(DbConfig dbConfig) {
        nameField.setText(dbConfig.getName());
        hostField.setText(dbConfig.getHost());
        portField.setText(dbConfig.getPort());
        usernameField.setText(dbConfig.getUsername());
        passwordField.setText(dbConfig.getPassword());
        schemaField.setText(dbConfig.getSchema());
        this.dbConfigId = dbConfig.getId();


    }

    private DbConfig getDbConfig() {
        DbConfig result = new DbConfig();
        result.setId(this.dbConfigId);
        result.setName(nameField.getText());
        result.setHost(hostField.getText());
        result.setPort(portField.getText());
        result.setUsername(usernameField.getText());
        result.setPassword(passwordField.getText());
        result.setSchema(schemaField.getText());
        if (result.getName() == null || result.getName().isEmpty()) {
            AlertUtils.fail("连接名称不能为空");
        }
        return result;
    }

    @Override
    protected void close() {
        if (dialogStage != null) {
            dialogStage.close();
        }
    }

    @FXML
    public void testConnection() throws SQLException {
        Database database = new Mysql(getDbConfig());
        Connection connection = null;
        try {
            connection = database.getConnection();
        } catch (Exception e) {
            log.error("连接失败", e);
            AlertUtils.fail("连接失败");
        } finally {
            if (null != connection) {
                connection.close();
                AlertUtils.success("连接成功");
            }
        }
    }

    @FXML
    public void saveConnection() throws Exception {
        Sqlite sqlite = new Sqlite();
        sqlite.saveDbConfig(getDbConfig());
        mainUiController.loadDbTree();
        close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
