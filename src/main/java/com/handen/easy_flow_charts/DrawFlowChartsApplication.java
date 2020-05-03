package com.handen.easy_flow_charts;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DrawFlowChartsApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DrawFlowChartsApplication.class.getResource("main_layout.fxml"));

        Scene scene = new Scene(loader.load());
        primaryStage.setTitle("EasyFlowCharts Ivan Pletinskiy 951008");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static ArrayList<String> parseFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s;
        ArrayList<String> lines = new ArrayList<>();
        while((s = reader.readLine()) != null) {
            lines.add(s);
        }
        return lines;
    }
}