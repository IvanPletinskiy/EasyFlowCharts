package com.handen.easy_flow_charts.controller;

import com.handen.easy_flow_charts.DrawFlowChartsApplication;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainController implements Initializable {

    public AnchorPane container;
    private Stage stage;

    public Label errorLabel;

    public TextArea search_year;

    public TextArea search_surname;

    public TableView table;

    public TextArea year_text_area;
    public TextArea phone_text_area;
    public TextArea surname_text_area;

    public Button add_button;
    public Button save_button;
    public Button delete_button;
    public MenuItem menu_load;
    public MenuItem menu_save;
    public MenuItem menu_help;
    public MenuItem menu_about;

    private int selectedRow;

    @FXML
    public void OnAddButtonClick(ActionEvent actionEvent) {
        Parent root;
        try {
            FXMLLoader loader = new FXMLLoader(DrawFlowChartsApplication.class.getResource("add_dialog.fxml"));
            root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add record");
            stage.setScene(new Scene(root, 450, 300));
            stage.show();
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }


    private boolean validate() {
        boolean isValid = true;

        short year = getYear();
        if(year < 2000 || year > 2020) {
            isValid = false;
            showError("Year cannot be less than 2000 or grater than 2020");
        }

        if(surname_text_area.textProperty().getValue().equals("")) {
            isValid = false;
            showError("Surname field cannot be empty.");
        }
        if(phone_text_area.textProperty().getValue().equals("")) {
            isValid = false;
            showError("Phone field cannot be empty.");
        }
        if(year_text_area.textProperty().getValue().equals("")) {
            isValid = false;
            showError("Year field cannot be empty.");
        }

        return isValid;
    }

    private short getYear() {
        short year = -1;
        try {
            year = Short.parseShort(year_text_area.textProperty().getValue());
        }
        catch(NumberFormatException e) {
            e.printStackTrace();
        }
        return year;
    }

    private void showError(String errorText) {
        errorLabel.setText(errorText);
        errorLabel.setVisible(true);
    }

    public void OnDeleteButtonClick(ActionEvent actionEvent) {

    }

    public void initialize(URL url, ResourceBundle resourceBundle) {



    }

    private void initializeTable() {

    }



    private void search() {
    }

    private void updatePredicate() {
        String surname = search_surname.textProperty().getValue();
        short year = -1;
        String yearString = search_year.textProperty().getValue();
        if(!yearString.isEmpty()) {
            try {
                year = Short.parseShort(yearString);
            }
            catch(NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }



    public void OnMenuLoadClicked(ActionEvent actionEvent) {

    }

    public void OnMenuSaveClicked(ActionEvent actionEvent) {

    }

    private File chooseFile(String title) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        fileChooser.setInitialDirectory(userDirectory);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        return fileChooser.showOpenDialog(stage);
    }

    private File chooseDirecotory(String title) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle(title);

        String userDirectoryString = System.getProperty("user.home");
        File userDirectory = new File(userDirectoryString);
        if(!userDirectory.canRead()) {
            userDirectory = new File("c:/");
        }
        directoryChooser.setInitialDirectory(userDirectory);
        return directoryChooser.showDialog(stage);
    }

    public void OnMenuHelpClicked(ActionEvent actionEvent) {
        showAlert("Help", helpText);
    }

    public void OnMenuAboutClicked(ActionEvent actionEvent) {
        showAlert("About", aboutText);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    public void focusNextTextArea(KeyEvent event) {
        if (event.getCode() == KeyCode.TAB && !event.isShiftDown() && !event.isControlDown()) {
            event.consume();
            Node node = (Node) event.getSource();
            KeyEvent newEvent
                    = new KeyEvent(event.getSource(),
                    event.getTarget(), event.getEventType(),
                    event.getCharacter(), event.getText(),
                    event.getCode(), event.isShiftDown(),
                    true, event.isAltDown(),
                    event.isMetaDown());

            node.fireEvent(newEvent);
        }
    }

    interface RecordChangeListener {
        void onChanged();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private static final String helpText =
            "This program is used for creating, editing, deleting, saving and loading records. The record consists of: owner's surname, phone number, year.\n" +
            "Use TAB and SHIFT+TAB to navigate between input fields.\n" +
            "Click on table and use arrows UP and DOWN to navigate between records\n" +
            "Save button will appear after you change text in input fields\n" +
            "Add and edit records, then click File -> Save to save them into file.";

    private static final String aboutText =
            "Made by Ivan Pletinski 951008 2020";
}