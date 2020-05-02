package com.handen.easyFlowCharts.mainScreen;

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

    private static String path = "C:\\Projects\\Lab_6_2\\src\\com\\handen\\Main.java";
    private static String savePath = "C:\\Users\\hande\\Desktop\\flowChart";
    private static int LIST_WIDTH = 2480;
    private static int LIST_HEIGHT = 3508;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(DrawFlowChartsApplication.class.getResource("main_layout.fxml"));
        Scene scene = new Scene(loader.load());
        MainController controller = loader.getController();
        //controller.setStage(stage);
        primaryStage.setTitle("EasyFlowCharts Ivan Pletinskiy 951008");
        primaryStage.setScene(scene);
        primaryStage.show();

      //  clearOutputDirectory();
        /*
        Group root = new Group();
        ArrayList<String> lines = parseFile(new File(path));
        ArrayList<MethodNodeGroup> methodNodes = new TreeBuilder(lines).getMethodTrees();
        Canvas canvas = drawMethods(methodNodes);
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

         */
    }
    /*

    private void clearOutputDirectory() {
        File directory = new File(savePath);

        for(File file : directory.listFiles()) {
            file.delete();
        }
    }

     */

    private static ArrayList<String> parseFile(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String s;
        ArrayList<String> lines = new ArrayList<>();
        while((s = reader.readLine()) != null) {
            lines.add(s);
        }
        return lines;
    }
/*
    private static Canvas drawMethods(ArrayList<MethodNodeGroup> methodNodes) {
        Canvas firstCanvas = null;
        for(MethodNodeGroup method : methodNodes) {
            int height = method.measureHeight();
            int side = height + 200;
            //Canvas canvas = new Canvas(side, side);
            Canvas canvas = new Canvas(LIST_WIDTH, LIST_HEIGHT);

            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("Verdana", DrawTextStrategy.FONT_SIZE));
            gc.setLineWidth(2);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);

            method.draw(new Context(gc));
            saveCanvasToMemory(canvas, method.getText());
            if(firstCanvas == null) {
                firstCanvas = canvas;
            }
            canvas = null;
        }
        return firstCanvas;
    }

 */
/*
    private static void saveCanvasToMemory(Canvas canvas, String fileName) {
        File file = new File(savePath, fileName + ".png");
        WritableImage image = canvas.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", file);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

 */
}