package calories;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CaloriesCalculator extends Application {
    @java.lang.Override
    public void start(Stage stage) throws Exception {
        javafx.scene.Parent root
                = FXMLLoader.load(getClass().getResource("CalculatorScene.fxml"));

        Scene scene = new Scene(root);

        // Set the Window title
        stage.setTitle("Calories Calculator");
        stage.sizeToScene();
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}