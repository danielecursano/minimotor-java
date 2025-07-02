module it.dani.minimotorways {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.logging;

    opens it.dani.minimotorways to javafx.fxml;
    exports it.dani.minimotorways;
}