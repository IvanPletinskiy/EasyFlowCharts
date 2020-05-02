package com.handen.easyFlowCharts.mainScreen;

import com.handen.easyFlowCharts.Nodes.MethodNodeGroup;
import com.handen.easyFlowCharts.TreeBuilder;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class MainController implements Initializable {

    //private boolean isSaving = true;
    private BooleanProperty isSaving;
    public TextField source_text_area;
    public Label source_error_text;
    public CheckBox save_flowchart_checkbox;
    public TextField save_text_area;
    public Label save_error_text;
    public Button create_button;
    public ProgressBar progress_bar;
    public Label progress_percent_label;
    public Label progress_description_label;
    private String sourcePath = "C:\\Projects\\Lab_6_2\\src\\com\\handen\\Main.java";
    private String savePath = "C:\\Users\\hande\\Desktop\\flowChart";

    private LinkedList<MethodNodeGroup> methods;
    private TreeBuilder treeBuilder;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        methods = new LinkedList<>();
        treeBuilder = new TreeBuilder();
        isSaving = new SimpleBooleanProperty(true);
        isSaving.bindBidirectional(save_flowchart_checkbox.selectedProperty());
        isSaving.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                save_text_area.setDisable(!newValue);
                save_error_text.setDisable(!newValue);
            }
        });
    }

    public void onSaveCheckedChanged(MouseEvent mouseEvent) {

    }

    public void onCrateButtonClicked(ActionEvent actionEvent) {
        createFlowChart();
    }

    public void OnMenuAboutClicked(ActionEvent actionEvent) {
        //TODO
    }

    public void OnMenuHelpClicked(ActionEvent actionEvent) {
        //TODO
    }

    private void createFlowChart() {
        /*
        var methods = buildMethodTrees();
        FlowchartDrawer flowchartDrawer = new FlowchartDrawer(methods);
        while(flowchartDrawer.hasNext()) {
            Canvas canvas = flowchartDrawer.drawPage();
            if(isSaving.get()) {
                saveFlowchartPage(canvas);
            }
        }

         */
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
        /*
        if(file.isDirectory()) {
            for(File subFile : file.listFiles()) {
                iterateFiles(subFile);
            }
        }
        else {
            var fileMethods = treeBuilder.parseFile(file);
            methods.add(fileMethods);
        }

         */
    }
}
