module io.github.filipdostal.towerdefense {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.graphics;


    opens io.github.filipdostal.towerdefense to javafx.fxml;
    exports io.github.filipdostal.towerdefense;
}