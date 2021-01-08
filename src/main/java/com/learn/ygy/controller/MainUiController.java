package com.learn.ygy.controller;

import com.learn.ygy.db.DbFactory;
import com.learn.ygy.db.Sqlite;
import com.learn.ygy.entity.DbConfig;
import com.learn.ygy.entity.GeneratorConfig;
import com.learn.ygy.enums.VersionEnum;
import com.learn.ygy.generate.GeneratorSubject;
import com.learn.ygy.utils.AlertUtils;
import com.mysql.jdbc.StringUtils;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.util.Callback;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.net.URL;
import java.util.*;

/**
 * @Author yanguangyuan
 * @Description 主界面控制器
 * @createTime 2020年12月25日 11:15:00
 */
@NoArgsConstructor
@Slf4j
public class MainUiController extends BaseUiController {

    @FXML
    private TreeView<String> dbTree;
    @FXML
    private Button newConnectionButton;
    @FXML
    private TextField authorField;
    @FXML
    private TextField daoPathField;
    @FXML
    private TextField servicePathField;
    @FXML
    private TextField controllerPathField;
    @FXML
    private CheckBox mapperCheckBox;
    @FXML
    private CheckBox doCheckBox;
    @FXML
    private CheckBox serviceCheckBox;
    @FXML
    private CheckBox dtoCheckBox;
    @FXML
    private CheckBox controllerCheckBox;
    @FXML
    private CheckBox voCheckBox;
    @FXML
    private ChoiceBox<String> versionChoiceBox;

    private Map<DbConfig, Set<String>> checkTables = new HashMap<>(8);

    private GeneratorSubject generatorSubject = new GeneratorSubject();

    @Override
    protected void close() {
        primaryStage.close();
    }


    @FXML
    public void generator() {
        String version = versionChoiceBox.getValue();
        log.info("选中的版本：{}", version);
        log.info("选中表数据为{}", checkTables);
        if (checkTables.isEmpty()) {
            AlertUtils.fail("请选择表数据");
            return;
        }
        String author = authorField.getText();
        String daoPath = daoPathField.getText();
        String servicePath = servicePathField.getText();
        String controllerPath = controllerPathField.getText();
        if (StringUtils.isNullOrEmpty(author)) {
            AlertUtils.fail("请确定作者");
            return;
        }
        if (StringUtils.isNullOrEmpty(daoPath)) {
            AlertUtils.fail("请确定dao路径");
            return;
        }
        if (StringUtils.isNullOrEmpty(servicePath)) {
            AlertUtils.fail("请确定service路径");
            return;
        }
        if (StringUtils.isNullOrEmpty(controllerPath)) {
            AlertUtils.fail("请确定controller路径");
            return;
        }

        GeneratorConfig config = GeneratorConfig.builder()
                .checkTables(checkTables)
                .author(author)
                .daoPath(daoPath)
                .servicePath(servicePath)
                .controllerPath(controllerPath)
                .mapperCheck(mapperCheckBox.isSelected())
                .doCheck(doCheckBox.isSelected())
                .serviceCheck(serviceCheckBox.isSelected())
                .dtoCheck(dtoCheckBox.isSelected())
                .controllerCheck(controllerCheckBox.isSelected())
                .voCheck(voCheckBox.isSelected())
                .versionEnum(VersionEnum.getByValue(version))
                .build();
        generatorSubject.process(config);

    }

    private File selectedFolder() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File("D:\\workspace\\java\\self-test\\src\\main\\java\\com\\keytop\\superpark\\selftest\\dao\\mysql\\test"));
        return directoryChooser.showDialog(primaryStage);
    }

    @FXML
    public void chooseDaoFolder() {
        File selectedFolder = selectedFolder();
        if (selectedFolder != null) {
            daoPathField.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    public void chooseServiceFolder() {
        File selectedFolder = selectedFolder();
        if (selectedFolder != null) {
            servicePathField.setText(selectedFolder.getAbsolutePath());
        }
    }

    @FXML
    public void chooseControllerFolder() {
        File selectedFolder = selectedFolder();
        if (selectedFolder != null) {
            controllerPathField.setText(selectedFolder.getAbsolutePath());
        }
    }

    /**
     * 加载db树
     */
    public void loadDbTree() throws Exception {
        Sqlite sqlite = new Sqlite();
        List<DbConfig> dbConfigs = sqlite.queryDbConfigs();
        TreeItem<String> rootTreeItem = dbTree.getRoot();
        rootTreeItem.getChildren().clear();
        try {
            for (DbConfig dbConfig : dbConfigs) {
                TreeItem<String> treeItem = new TreeItem<>();
                Label label = new Label();
                label.setUserData(dbConfig);
                treeItem.setGraphic(label);
                treeItem.setValue(dbConfig.getName());
                rootTreeItem.getChildren().add(treeItem);
            }
        } catch (Exception e) {
            log.error("connect db failed, reason: ", e);
            AlertUtils.fail(e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        newConnectionButton.setOnMouseClicked(event -> {
            ConnectionUiController controller = (ConnectionUiController) loadPage("新建数据库连接", PAGE_CONNECTION);
            controller.setMainUiController(this);
            controller.showDialogStage();
        });
        initDbTree();
        initVersionChoiceBox();
    }

    /**
     * 初始化db tree
     *
     * @throws Exception
     */
    private void initDbTree() throws Exception {
        dbTree.setShowRoot(false);
        dbTree.setRoot(new TreeItem<>());
        Callback<TreeView<String>, TreeCell<String>> defaultCellFactory = TextFieldTreeCell.forTreeView();
        dbTree.setCellFactory((TreeView<String> tv) -> {
            TreeCell<String> cell = defaultCellFactory.call(tv);
            cell.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
                int level = dbTree.getTreeItemLevel(cell.getTreeItem());
                TreeCell<String> treeCell = (TreeCell<String>) event.getSource();
                TreeItem<String> treeItem = treeCell.getTreeItem();
                if (level == 1) {
                    final ContextMenu contextMenu = new ContextMenu();
                    MenuItem item1 = new MenuItem("关闭连接");
                    item1.setOnAction(event1 -> treeItem.getChildren().clear());
                    MenuItem item2 = new MenuItem("编辑连接");
                    item2.setOnAction(event1 -> {
                        DbConfig dbConfig = (DbConfig) treeItem.getGraphic().getUserData();
                        ConnectionUiController controller = (ConnectionUiController) loadPage("编辑数据库连接", PAGE_CONNECTION);
                        controller.setMainUiController(this);
                        controller.updateShow(dbConfig);
                        controller.showDialogStage();
                    });
                    MenuItem item3 = new MenuItem("删除连接");
                    item3.setOnAction(event1 -> {
                        DbConfig dbConfig = (DbConfig) treeItem.getGraphic().getUserData();
                        try {
                            Sqlite sqlite = new Sqlite();
                            sqlite.delete(dbConfig);
                            this.loadDbTree();
                        } catch (Exception e) {
                            log.error("删除失败", e);
                            AlertUtils.fail("Delete connection failed! Reason: " + e.getMessage());
                        }
                    });
                    contextMenu.getItems().addAll(item1, item2, item3);
                    cell.setContextMenu(contextMenu);
                }
                if (event.getClickCount() == 2) {
                    if (treeItem == null) {
                        return;
                    }
                    treeItem.setExpanded(true);
                    if (level == 1) {
                        DbConfig dbConfig = (DbConfig) treeItem.getGraphic().getUserData();
                        try {
                            List<String> tables = DbFactory.getDatabase(dbConfig).listTables();
                            if (tables != null && tables.size() > 0) {
                                ObservableList<TreeItem<String>> children = cell.getTreeItem().getChildren();
                                children.clear();
                                for (String tableName : tables) {
                                    TreeItem<String> newTreeItem = new TreeItem<>();
                                    CheckBox checkBox = new CheckBox();
                                    checkBox.setOnAction(event1 -> {
                                        if (event1.getSource() instanceof CheckBox) {
                                            CheckBox c = (CheckBox) event1.getSource();
                                            log.info("{}-{}", tableName, c.isSelected());
                                            dbAndTableCheck(dbConfig, tableName, c.isSelected());
                                        }
                                    });
                                    checkBox.setId(tableName);
                                    newTreeItem.setGraphic(checkBox);
                                    newTreeItem.setValue(tableName);
                                    children.add(newTreeItem);
                                }
                            }
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                            AlertUtils.fail(e.getMessage());
                        }
                    }
                }
            });
            return cell;
        });
        loadDbTree();
    }

    /**
     * 初始化版本下拉
     */
    private void initVersionChoiceBox() {
        versionChoiceBox.getItems().addAll(VersionEnum.getEnumValues());
        versionChoiceBox.setValue(VersionEnum.MYBATIS.getValue());
    }


    /**
     * 处理表选中复选框事件
     *
     * @param dbConfig
     * @param tableName
     * @param isSelect
     */
    private void dbAndTableCheck(DbConfig dbConfig, String tableName, Boolean isSelect) {
        Set<String> tables = checkTables.computeIfAbsent(dbConfig, k -> new HashSet<>(20));
        if (Boolean.TRUE.equals(isSelect)) {
            tables.add(tableName);
        } else {
            tables.remove(tableName);
        }
        if (tables.isEmpty()) {
            checkTables.remove(dbConfig);
        }
    }
}
