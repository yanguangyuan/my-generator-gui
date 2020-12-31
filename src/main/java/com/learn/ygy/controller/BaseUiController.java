package com.learn.ygy.controller;

import com.learn.ygy.utils.AlertUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URL;

/**
 * @Author yanguangyuan
 * @Description 基础
 * @createTime 2020年12月25日 16:01:00
 */
@NoArgsConstructor
@Slf4j
@Setter
public abstract class BaseUiController implements Initializable {

    public static final String PAGE_CONNECTION = "fxml/connectionUi.fxml";
    public static final String PAGE_MAIN="fxml/mainUi.fxml";

    protected Stage primaryStage;
    protected Stage dialogStage;
    protected MainUiController mainUiController;

    /**
     * 关闭
     */
    protected abstract void close();

    protected BaseUiController loadPage(String title, String page) {
        URL skeletonResource = Thread.currentThread().getContextClassLoader().getResource(page);
        FXMLLoader loader = new FXMLLoader(skeletonResource);
        Parent loginNode;
        try {
            loginNode = loader.load();
            BaseUiController controller = loader.getController();
            dialogStage = new Stage();
            dialogStage.setTitle(title);
            dialogStage.initModality(Modality.APPLICATION_MODAL);
            dialogStage.initOwner(primaryStage);
            dialogStage.setScene(new Scene(loginNode));
            dialogStage.setMaximized(false);
            dialogStage.setResizable(false);
            dialogStage.show();
            controller.setDialogStage(dialogStage);
            return controller;
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            AlertUtils.fail(e.getMessage());
        }
        return null;
    }

    protected void showDialogStage() {
        dialogStage.show();
    }
}
