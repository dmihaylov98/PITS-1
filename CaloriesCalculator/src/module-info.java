module calories {
    requires javafx.controls;
    requires javafx.fxml;
    opens calories to javafx.fxml;
    exports calories to javafx.graphics;
}