package com.handen.easyFlowCharts.mainScreen;

import com.handen.easyFlowCharts.Nodes.MethodNodeGroup;
import com.handen.easyFlowCharts.TreeBuilder;
import com.handen.easyFlowCharts.flowchart.FlowchartDrawer;
import com.handen.easyFlowCharts.utils.FileMethodsPair;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {

    public Button source_open_button;
    public Button save_open_button;
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
    private ExecutorService executor;

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
        executor = Executors.newSingleThreadExecutor();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void onSaveCheckedChanged(MouseEvent mouseEvent) {

    }

    public void onCreateButtonClicked(ActionEvent actionEvent) {
        boolean isInputValid = validateInput();
        if(isInputValid) {
            hideButton();
            showProgress();
            if(isSaving.get()) {
                cleanOutputDirectory();
            }
            String startPath = source_text_area.getText();
            var filesMap = createFilesMethodsPairs(startPath);
            createFlowChart(filesMap);
        }
        else {
            create_button.setStyle("-fx-background-color: #f44336");
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
        progress_bar.setVisible(true);
        progress_percent_label.setVisible(true);
        progress_description_label.setVisible(true);
    }

    private void hideButton() {
        create_button.setVisible(false);
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

    private void createFlowChart(List<FileMethodsPair> fileMethodsPairList) {
        FlowchartDrawer flowchartDrawer = new FlowchartDrawer(fileMethodsPairList);
        int filesCount = fileMethodsPairList.size();
        int pagesDrawn = 0;
        while(flowchartDrawer.hasNext()) {
            String pageName = flowchartDrawer.getCurrentPageName();
            String fileName = flowchartDrawer.getCurrentFileName();
            int remainFilesCount = flowchartDrawer.getRemainFilesCount();
            int filesProcessed = filesCount - remainFilesCount;
            int progress = (filesProcessed / filesCount) * 100;
            String message = String.format("Drawing flowchart for file:%s", fileName);
            updateProgress(progress, message);

            Canvas canvas = flowchartDrawer.drawPage();
            pagesDrawn++;
            if(isSaving.get()) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        saveFlowchartPage(canvas, pageName);
                    }
                });
            }
        }
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

    private void saveFlowchartPage(Canvas canvas, String fileName) {
        String savePath = save_text_area.getText();
        File file = new File(savePath, fileName + ".png");
        WritableImage image = canvas.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(image, null);
        RenderedImage renderedImage = SwingFXUtils.fromFXImage(image, null);
        try {
            ImageIO.write(bImage, "png", file);
            //ImageIO.write(renderedImage, "png", file);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }

    private List<FileMethodsPair> createFilesMethodsPairs(String startPath) {
        updateProgress(0, "Getting all files in directories");
        List<File> fileList = getFilesStream(startPath);
        List<FileMethodsPair> filesMethodPairs = new LinkedList<>();
        for(int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            int progress = (int) (i / ((double) fileList.size()) * 100);
            String message = String.format("Parsing file:%s", file.getName());
            updateProgress(progress, message);
            TreeBuilder treeBuilder = new TreeBuilder(file);
            List<MethodNodeGroup> methods = treeBuilder.parseFile();
            FileMethodsPair pair = new FileMethodsPair(file, methods);
            filesMethodPairs.add(pair);
        }

        return filesMethodPairs;
    }

    private void updateProgress(int progress, String message) {
        progress_bar.setProgress(progress / 100);
        progress_description_label.setText(message);
        progress_percent_label.setText(progress + "%");
    }

    private List<File> getFilesStream(String filePath) {
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
