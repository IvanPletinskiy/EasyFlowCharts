package com.handen;

import com.handen.Nodes.MethodNodeGroup;
import com.handen.strategies.DrawTextStrategy;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class DrawFlowChartsApplication extends Application {

    private static String path = "C:\\Projects\\hellofx\\src\\main\\java\\com\\handen\\lab\\controller\\MainController.java";
    private static String savePath = "C:\\Users\\hande\\Desktop\\flowChart";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        clearOutputDirectory();
        primaryStage.setTitle("EasyFlowCharts");
        Group root = new Group();
        ArrayList<String> lines = parseFile(new File(path));
        ArrayList<MethodNodeGroup> methodNodes = new TreeBuilder(lines).getMethodTrees();
        ArrayList<Canvas> canvases = drawMethods(methodNodes);
        root.getChildren().add(canvases.get(0));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private void clearOutputDirectory() {
        File directory = new File(savePath);

        for(File file : directory.listFiles()) {
            file.delete();
        }
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

    private static ArrayList<Canvas> drawMethods(ArrayList<MethodNodeGroup> methodNodes) {
        ArrayList<Canvas> ret = new ArrayList<>();
        for(MethodNodeGroup method : methodNodes) {
            int height = method.measureHeight();
            int side = height + 200;
            Canvas canvas = new Canvas(side, side);
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.BLACK);
            gc.setFill(Color.BLACK);
            gc.setFont(Font.font("Verdana", DrawTextStrategy.FONT_SIZE));
            gc.setLineWidth(2);
            gc.setTextAlign(TextAlignment.CENTER);
            gc.setTextBaseline(VPos.CENTER);

            method.draw(new Context(gc));
            ret.add(canvas);
            saveCanvasToMemory(canvas, method.getText());
        }
        return ret;
    }

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
}