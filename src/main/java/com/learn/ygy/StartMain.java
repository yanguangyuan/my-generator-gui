package com.learn.ygy;

import com.learn.ygy.controller.BaseUiController;
import com.learn.ygy.controller.MainUiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

/**
 * @Author yanguangyuan
 * @Description 启动类
 * @createTime 2020年12月25日 11:15:00
 */
public class StartMain extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = StartMain.class.getClassLoader().getResource(BaseUiController.PAGE_MAIN);
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        Parent root = fxmlLoader.load();
        primaryStage.setResizable(true);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        MainUiController controller = fxmlLoader.getController();
        controller.setPrimaryStage(primaryStage);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
