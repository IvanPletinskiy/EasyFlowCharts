package com.handen.easyFlowCharts.mainScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DrawFlowChartsApplication extends Application {

    public static void main(String[] args) {
        //Запуск приложения JavaFX
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        //Устанавливаем местоположение ресурса с разметкой сцены
        loader.setLocation(DrawFlowChartsApplication.class.getResource("main_layout.fxml"));
        //Загружаем разметку сцены
        Parent parent = loader.load();
        Scene scene = new Scene(parent);
        //Устанавливаем название окна и сцену
        primaryStage.setTitle("EasyFlowCharts Ivan Pletinskiy 951008");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}