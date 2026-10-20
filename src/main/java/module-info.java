module io.github.filipdostal.towerdefense {
    requires javafx.controls;
    requires javafx.fxml;


    opens io.github.filipdostal.towerdefense to javafx.fxml;
    exports io.github.filipdostal.towerdefense;
}