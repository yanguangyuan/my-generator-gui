package com.learn.ygy.utils;

import javafx.scene.control.Alert;

/**
 * @Author yanguangyuan
 * @Description 提醒
 * @createTime 2020年12月25日 17:44:00
 */
public class AlertUtils {

    public static void fail(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }
    public static void success(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }
}
