package calories;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class CalculatorSceneController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCalcAverage;

    @FXML
    private Button btnWeightLoss;

    @FXML
    private MenuItem mnuExit;

    @FXML
    private MenuItem mnuFile;

    @FXML
    private TextField txtActivity;

    @FXML
    private TextField txtAge;

    @FXML
    private TextField txtAvgCalories;

    @FXML
    private TextField txtConsumedCals;

    @FXML
    private TextField txtGender;

    @FXML
    private TextField txtHeight;

    @FXML
    private TextField txtMaxCalories;

    @FXML
    private TextField txtWeight;

    @FXML
    void btnCalcAverageOnAction(ActionEvent event) {
        double RMR = calculateCalories(false);
        txtAvgCalories.setText(String.format("%.2f", Math.abs(RMR)));
    }

    @FXML
    void btnWeightLossOnAction(ActionEvent event) {
        double weightLoss = calculateCalories(true);
        txtMaxCalories.setText(String.format("%.2f", Math.abs(weightLoss)));
    }

    @FXML
    void mnuExitOnAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void mnuFileOnAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose a file to import");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT", "*.txt"));

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                StringBuilder contents = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    contents.append(line).append("\n");
                }

                String[] contentsSplit = contents.toString().split("\n");
                if (contentsSplit.length == 6) {
                    txtGender.setText(contentsSplit[0]);
                    txtHeight.setText(contentsSplit[1]);
                    txtWeight.setText(contentsSplit[2]);
                    txtAge.setText(contentsSplit[3]);
                    txtActivity.setText(contentsSplit[4]);
                    txtConsumedCals.setText(contentsSplit[5]);
                }
            } catch (IOException e) {
                System.exit(0);
            }
        }
    };

    @FXML
    void initialize() {
        assert btnCalcAverage != null : "fx:id=\"btnCalcAverage\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert btnWeightLoss != null : "fx:id=\"btnWeightLoss\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert mnuExit != null : "fx:id=\"mnuExit\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert mnuFile != null : "fx:id=\"mnuFile\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert txtActivity != null : "fx:id=\"txtActivity\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert txtAge != null : "fx:id=\"txtAge\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert txtAvgCalories != null : "fx:id=\"txtAvgCalories\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert txtConsumedCals != null : "fx:id=\"txtConsumedCals\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert txtGender != null : "fx:id=\"txtGender\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert txtHeight != null : "fx:id=\"txtHeight\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert txtMaxCalories != null : "fx:id=\"maxCalories\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
        assert txtWeight != null : "fx:id=\"txtWeight\" was not injected: check your FXML file 'CalculatorScene.fxml'.";
    }

    public double calculateCalories(boolean withPhysicalActivity) {
        int gender = 0;
        if (!txtGender.getText().equals("")) {
            gender = Integer.parseInt(txtGender.getText());
        }

        int height = 0;
        if (!txtHeight.getText().equals("")) {
            height = Integer.parseInt(txtHeight.getText());
        }

        int weight = 0;
        if (!txtWeight.getText().equals("")) {
            weight = Integer.parseInt(txtWeight.getText());
        }

        int age = 0;
        if (!txtAge.getText().equals("")) {
            age = Integer.parseInt(txtAge.getText());
        }

        double BMR = 9.99 * weight + 6.25 * height - 4.92 * age + 166 * gender - 161;

        int activityLevel = 0;
        if (!txtActivity.getText().equals("")) {
            activityLevel = Integer.parseInt(txtActivity.getText());
        }

        double activityRate = switch (activityLevel) {
            case 1 -> 1.2;
            case 2 -> 1.38;
            case 3 -> 1.55;
            case 4 -> 1.73;
            case 5 -> 1.9;
            default -> 0;
        };

        if (withPhysicalActivity) {
            int consumedCalories = 0;
            if (!txtConsumedCals.getText().equals("")) {
                consumedCalories = Integer.parseInt(txtConsumedCals.getText());
            }

            BMR -= consumedCalories;
        }

        return BMR * activityRate;
    }
}