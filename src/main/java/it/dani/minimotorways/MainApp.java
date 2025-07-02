package it.dani.minimotorways;

import it.dani.minimotorways.view.FXView;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        FXView view = new FXView();
        view.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}