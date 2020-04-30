package com.handen.mainScreen;

import com.handen.Nodes.MethodNodeGroup;
import com.handen.TreeBuilder;
import com.handen.utils.FileTreeIterator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;

public class MainController implements Initializable {

    private boolean isSaving = true;
    private String sourcePath = "C:\\Projects\\Lab_6_2\\src\\com\\handen\\Main.java";
    private String savePath = "C:\\Users\\hande\\Desktop\\flowChart";

    private LinkedList<MethodNodeGroup> methods;
    private TreeBuilder treeBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        methods = new LinkedList<>();
        treeBuilder = new TreeBuilder();
    }

    private void createFlowChart() {
        var methods = buildMethodTrees();
        FlowchartDrawer flowchartDrawer = new FlowchartDrawer(methods);
        while(flowchartDrawer.hasNext()) {
            Canvas canvas = flowchartDrawer.drawPage();
            if(isSaving) {
                saveFlowchartPage(canvas);
            }
        }
    }

    private void saveFlowchartPage(Canvas canvas) {
        String fileName = canvas.getId();
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

    private List<MethodNodeGroup> buildMethodTrees() {
        LinkedList<MethodNodeGroup> methods = new LinkedList<>();
        TreeBuilder treeBuilder = new TreeBuilder();
        File startFile = new File(sourcePath);

        iterateFiles(startFile);

        return methods;
    }

    private void iterateFiles(File file) {
        if(file.isDirectory()) {
            for(File subFile : file.listFiles()) {
                iterateFiles(subFile);
            }
        }
        else {
            var fileMethods = treeBuilder.parseFile(file);
            methods.add(fileMethods);
        }
    }
}
