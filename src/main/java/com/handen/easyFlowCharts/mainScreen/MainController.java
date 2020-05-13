package com.handen.easyFlowCharts.mainScreen;

import com.handen.easyFlowCharts.Nodes.MethodNodeGroup;
import com.handen.easyFlowCharts.GraphBuilder;
import com.handen.easyFlowCharts.flowChartScreen.FlowchartController;
import com.handen.easyFlowCharts.flowchart.FlowchartDrawer;
import com.handen.easyFlowCharts.utils.FileMethodsPair;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {

    public Button source_open_button;
    public Button save_open_button;
    public VBox progress_container;
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
    private static final String TITLE_CHOOSE_SOURCE_DIRECTORY = "Choose source files directory";
    private static final String TITLE_SAVE_DIRECTORY = "Choose save directory";
    private static final String ERROR_NOT_DIRECTORY = "Entered path isn't a directory.";
    private static final String ERROR_CANNOT_OPEN = "Error! Сannot open directory.";
    private static final String ERROR_DOES_NOT_EXISTS = "Error! Directory doesn't exists.";
    private static final String ERROR_NOT_JAVA_FILE = "Entered file is not a .java file.";
    private static final String FIELD_CANNOT_BE_EMPTY = "Input field cannot be empty.";
    private long lastProgressUpdateMillis = -1;
    private FlowchartController flowchartController;
    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        source_text_area.setText("C:\\Projects\\easy_flow_charts\\src\\test\\java\\FlowchartTest.java");
        save_text_area.setText("C:\\Users\\hande\\Desktop\\flowChart");
        isSaving = new SimpleBooleanProperty(true);
        isSaving.bindBidirectional(save_flowchart_checkbox.selectedProperty());
        isSaving.addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                save_text_area.setDisable(!newValue);
                save_error_text.setDisable(!newValue);
                save_open_button.setDisable(!newValue);
            }
        });
    }

    public void onSaveCheckedChanged(MouseEvent mouseEvent) {

    }

    public void onCreateButtonClicked(ActionEvent event) {
        boolean isInputValid = validateInput();
        if(isInputValid) {
            create_button.setVisible(false);
            showProgress();
            startFlowchartScene();
            createFlowchart();
        }
        else {
            create_button.setStyle("-fx-background-color: #f44336");
        }
        source_error_text.setVisible(!isInputValid);
        save_error_text.setVisible(!isInputValid);
    }

    private void startFlowchartScene() {
        FXMLLoader loader = new FXMLLoader(DrawFlowChartsApplication.class.getResource("flowchart_layout.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        if(root != null) {
            flowchartController = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Flowchart");
            flowchartController.setStage(stage);
            stage.setScene(new Scene(root, 600, 900));
            stage.show();
        }
    }

    private void createFlowchart() {
        String startPath = source_text_area.getText();
        new Thread(() -> {
            if(isSaving.get()) {
                updateProgressOnUIThread(0, "Clearing output directory.");
                cleanOutputDirectory();
            }
            var filesMap = createFilesMethodsPairs(startPath);
            drawFlowchart(filesMap);
        }, "Flowchart creator Thread").start();
    }

    private List<FileMethodsPair> createFilesMethodsPairs(String startPath) {
        updateProgressOnUIThread(0, "Getting all files in directories.");

        List<File> fileList = getFilesList(startPath);
        int filesCount = fileList.size();
        List<FileMethodsPair> filesMethodPairs = new LinkedList<>();
        for(int i = 0; i < filesCount; i++) {
            File file = fileList.get(i);

            double progress = i / ((double) filesCount - 1);
            String message = String.format("Parsing file:%s", file.getName());
            updateProgressOnUIThread(progress, message);

            GraphBuilder graphBuilder = new GraphBuilder(file);
            List<MethodNodeGroup> methods = graphBuilder.parseFile();
            FileMethodsPair pair = new FileMethodsPair(file, methods);
            filesMethodPairs.add(pair);
        }
        updateProgressOnUIThread(1.0, "Parsing files Done.");

        return filesMethodPairs;
    }

    private void drawFlowchart(List<FileMethodsPair> fileMethodsPairList) {
        FlowchartDrawer flowchartDrawer = new FlowchartDrawer();
        updateProgressOnUIThread(0, "Drawing flowchart");

        for(var pair : fileMethodsPairList) {
            List<Canvas> fileCanvases = flowchartDrawer.drawFile(pair);
            addCanvases(fileCanvases);

            if(isSaving.get()) {
                saveCanvases(new LinkedList<>(fileCanvases), 0, fileCanvases.size());
            }
        }
    }

    private void addCanvases(List<Canvas> fileCanvases) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                flowchartController.addCanvases(fileCanvases);
                fileCanvases.clear();
            }
        });
    }

    private void saveCanvases(Queue<Canvas> canvases, final int count, final int total) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(!canvases.isEmpty()) {
                    Canvas canvas = canvases.poll();
                    String fileName = canvas.getId();
                    var snapshot = takeSnapshot(canvas);
                    double progress = (count + 1) / (double) total;
                    String message = "Saving " + fileName + " to memory";
                    updateProgressOnUIThread(progress, message);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            saveFlowchartPage(snapshot, fileName);
                        }
                    }).start();

                    saveCanvases(canvases, count + 1, total);
                }
                else {
                    updateProgressOnUIThread(1, "Done");
                }
            }
        });
    }

    private BufferedImage takeSnapshot(Canvas canvas) {
        WritableImage snapshot = canvas.snapshot(null, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);
        return image;
    }

    private void updateProgressOnUIThread(double progress, String message) {
        long currentMillis = new Date().getTime();
        if(currentMillis - lastProgressUpdateMillis > 250) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    progress_bar.setProgress(progress);
                    progress_description_label.setText(message);
                    int percent = (int) (progress * 100);
                    progress_percent_label.setText(percent + "%");
                    lastProgressUpdateMillis = new Date().getTime();
                }
            });
        }
    }

    private void cleanOutputDirectory() {
        String savePath = save_text_area.getText();
        File directory = new File(savePath);
        for(File file : directory.listFiles()) {
            file.delete();
        }
    }

    private void showProgress() {
        progress_container.setVisible(true);
    }

    public void onSourceButtonClicked(ActionEvent actionEvent) {
        File directory = chooseDirectory(TITLE_CHOOSE_SOURCE_DIRECTORY);
        if(directory != null) {
            source_text_area.setText(directory.getPath());
        }
    }

    public void onSaveButtonClicked(ActionEvent actionEvent) {
        File directory = chooseDirectory(TITLE_SAVE_DIRECTORY);
        if(directory != null) {
            save_text_area.setText(directory.getPath());
        }
    }

    public void OnMenuAboutClicked(ActionEvent actionEvent) {
        //TODO
    }

    public void OnMenuHelpClicked(ActionEvent actionEvent) {
        //TODO
    }

    private File chooseDirectory(String title) {
        var directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);
        return directoryChooser.showDialog(stage);
    }

    private boolean validateInput() {
        boolean isSourcePathValid = validateSourcePath();
        boolean isSavePathValid = true;
        if(isSaving.get()) {
            isSavePathValid = validateSavePath();
        }

        boolean isInputValid = isSourcePathValid && isSavePathValid;
        return isInputValid;
    }

    private boolean validateSourcePath() {
        boolean isValid;
        String path = source_text_area.getText();
        boolean isEmpty = path.isEmpty();
        if(isEmpty) {
            source_error_text.setText(FIELD_CANNOT_BE_EMPTY);
            isValid = false;
        }
        else {
            File file = new File(path);
            if(!file.isDirectory() && !file.getName().contains(".java")) {
                source_error_text.setText(ERROR_NOT_JAVA_FILE);
                isValid = false;
            }
            else {
                isValid = validate(file, source_error_text);
            }
        }
        return isValid;
    }

    private boolean validateSavePath() {
        boolean isValid;
        String path = save_text_area.getText();
        boolean isEmpty = path.isEmpty();
        if(isEmpty) {
            save_error_text.setText(FIELD_CANNOT_BE_EMPTY);
            isValid = false;
        }
        else {
            File file = new File(path);
            if(!file.isDirectory()) {
                save_error_text.setText(ERROR_NOT_DIRECTORY);
                isValid = false;
            }
            else {
                isValid = validate(file, save_error_text);
            }
        }

        return isValid;
    }

    private boolean validate(File file, Label errorLabel) {
        boolean isValid = true;
        if(!file.canRead() && !file.canWrite()) {
            errorLabel.setText(ERROR_CANNOT_OPEN);
            isValid = false;
        }
        if(!file.exists()) {
            errorLabel.setText(ERROR_DOES_NOT_EXISTS);
            isValid = false;
        }
        errorLabel.setVisible(!isValid);

        return isValid;
    }

    private void saveFlowchartPage(BufferedImage image, String fileName) {
        String savePath = save_text_area.getText();
        File file = new File(savePath, fileName + ".png");
        try {
            ImageIO.write(image, "png", file);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private List<File> getFilesList(String filePath) {
        Path start = Paths.get(filePath);
        List<File> fileList = Collections.emptyList();
        try(Stream<Path> stream = Files.walk(start, 10)) {
            fileList = stream
                    .filter(MainController::fileFilter)
                    .map(Path::toFile)
                    .collect(Collectors.toList());
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return fileList;
    }

    private static boolean fileFilter(Path path) {
        File file = path.toFile();

        String name = file.getName();
        int dotIndex = name.lastIndexOf('.');
        boolean isValid = dotIndex >= 0;
        if(dotIndex >= 0) {
            String extension = name.substring(dotIndex);
            isValid = file.isFile() && file.canRead() && extension.equals(".java");
        }
        return isValid;
    }
}