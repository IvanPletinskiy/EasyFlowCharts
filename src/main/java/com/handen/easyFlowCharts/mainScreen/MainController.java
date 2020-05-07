package com.handen.easyFlowCharts.mainScreen;

import com.handen.easyFlowCharts.Nodes.MethodNodeGroup;
import com.handen.easyFlowCharts.TreeBuilder;
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
    private Stage stage;
    private static final String TITLE_CHOOSE_SOURCE_DIRECTORY = "Choose source files directory";
    private static final String TITLE_SAVE_DIRECTORY = "Choose save directory";
    private static final String ERROR_NOT_DIRECTORY = "Entered path isn't a directory.";
    private static final String ERROR_CANNOT_OPEN = "Error! Сannot open directory.";
    private static final String ERROR_DOESNT_EXISTS = "Error! Directory doesn't exists.";
    private long lastProgressUpdateMillis = -1;
    private FlowchartController flowchartController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        source_text_area.setText("C:\\Projects\\hellofx\\src\\main\\java\\com\\handen\\lab");
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

        flowchartController = new FlowchartController();
    }

    public void onSaveCheckedChanged(MouseEvent mouseEvent) {

    }

    public void onCreateButtonClicked(ActionEvent actionEvent) throws Exception {
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
            Stage stage = new Stage();
            stage.setTitle("Flowchart");

            stage.setScene(new Scene(root, 600, 900));
            stage.show();
        }
    }

    private void createFlowchart() {
        String startPath = source_text_area.getText();
        new Thread(() -> {
            if(isSaving.get()) {
                updateProgressOnUIThread(0, "Clearing output directory");
                cleanOutputDirectory();
            }
            var filesMap = createFilesMethodsPairs(startPath);
            drawFlowchart(filesMap);
        }).start();
    }

    private List<FileMethodsPair> createFilesMethodsPairs(String startPath) {
        updateProgressOnUIThread(0, "Getting all files in directories");

        List<File> fileList = getFilesList(startPath);
        int filesCount = fileList.size();
        List<FileMethodsPair> filesMethodPairs = new LinkedList<>();
        for(int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);

            double progress = i / ((double) filesCount);
            String message = String.format("Parsing file:%s", file.getName());
            updateProgressOnUIThread(progress, message);

            TreeBuilder treeBuilder = new TreeBuilder(file);
            List<MethodNodeGroup> methods = treeBuilder.parseFile();
            FileMethodsPair pair = new FileMethodsPair(file, methods);
            filesMethodPairs.add(pair);
        }

        return filesMethodPairs;
    }

    private void updateProgressOnUIThread(double progress, String message) {
        long currentMillis = new Date().getTime();
        if(currentMillis - lastProgressUpdateMillis > 500) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    progress_bar.setProgress(progress);
                    progress_description_label.setText(message);
                    progress_percent_label.setText(progress + "%");
                    lastProgressUpdateMillis = new Date().getTime();
                }
            });
        }
    }

    private void drawFlowchart(List<FileMethodsPair> fileMethodsPairList) {
        FlowchartDrawer flowchartDrawer = new FlowchartDrawer();
        int filesCount = fileMethodsPairList.size();
        int filesProccesed = 0;
        List<Canvas> canvases = new LinkedList<>();
        for(var pair : fileMethodsPairList) {
            List<Canvas> fileCanvases = flowchartDrawer.drawFile(pair);
            filesProccesed++;
            double progress = (filesProccesed / (double) filesCount);
            String fileName = pair.file.getName();
            String message = String.format("Drawing flowchart for file:%s", fileName);
            updateProgressOnUIThread(progress, message);
            canvases.addAll(fileCanvases);

            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    flowchartController.addCanvases(canvases);
                }
            });
        }

        if(isSaving.get()) {
            saveCanvases(canvases, 0);
        }
    }

    private void saveCanvases(List<Canvas> canvases, int i) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Canvas canvas = canvases.get(i);
                String fileName = canvas.getId();
                var snapshot = takeSnapshot(canvas);
                double progress = i / (double) canvases.size();
                String message = "Saving " + fileName + " to memory";
                updateProgressOnUIThread(progress, message);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        saveFlowchartPage(snapshot, fileName);
                    }
                }).start();
                saveCanvases(canvases, i + 1);
            }
        });
    }

    private BufferedImage takeSnapshot(Canvas canvas) {
        WritableImage snapshot = canvas.snapshot(null, null);
        BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);
        return image;
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

    private boolean validateSavePath() {
        String path = save_text_area.getText();
        return validate(path, save_error_text);
    }

    private boolean validateSourcePath() {
        String path = source_text_area.getText();
        return validate(path, source_error_text);
    }

    private boolean validate(String path, Label errorLabel) {
        File file = new File(path);
        boolean isValid = true;
        if(!file.isDirectory()) {
            errorLabel.setText(ERROR_NOT_DIRECTORY);
            isValid = false;
        }
        if(!file.canRead() && !file.canWrite()) {
            errorLabel.setText(ERROR_CANNOT_OPEN);
            isValid = false;
        }
        if(!file.exists()) {
            errorLabel.setText(ERROR_DOESNT_EXISTS);
            isValid = false;
        }
        errorLabel.setVisible(!isValid);

        return isValid;
    }

    private void saveFlowchartPage(BufferedImage image, String fileName) {
        new Thread(() -> {
            String savePath = save_text_area.getText();
            File file = new File(savePath, fileName + ".png");
            try {
                ImageIO.write(image, "png", file);
            }
            catch(IOException e) {
                e.printStackTrace();
            }
        }).start();
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
