package it.dani.minimotorways.view;

import it.dani.minimotorways.controller.GameState;
import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.Car;
import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.building.*;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FXView extends View {

    private static final Building EMPTYCELL = new EmptyCell();
    private GridPane gridPane;
    private Label statusLabel;
    private final Map<Integer, Button> cellButtons = new HashMap<>();

    public void start(Stage primaryStage) {
        int rows = GameMap.getRows();
        int cols = GameMap.getCols();

        BorderPane root = new BorderPane();

        // Top: column indices
        HBox colIndices = new HBox(5);
        colIndices.setAlignment(Pos.CENTER_LEFT);
        colIndices.getChildren().add(new Label("   ")); // spacer for row labels
        for (int c = 0; c < cols; c++) {
            Label label = new Label(String.valueOf(c));
            label.setMinWidth(30);
            label.setAlignment(Pos.CENTER);
            colIndices.getChildren().add(label);
        }
        root.setTop(colIndices);

        // Center: grid with buttons and row labels
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        for (int r = 0; r < rows; r++) {
            Label rowLabel = new Label(String.valueOf(r));
            rowLabel.setMinWidth(30);
            rowLabel.setAlignment(Pos.CENTER);
            gridPane.add(rowLabel, 0, r + 1); // first col reserved for row labels

            for (int c = 0; c < cols; c++) {
                int pos = r * cols + c;
                Button btn = new Button();
                btn.setMinSize(30, 30);
                btn.setFocusTraversable(false);
                updateCellButton(btn, EMPTYCELL);

                int finalPos = pos;
                btn.setOnAction(e -> gameController.addRoad(finalPos));

                cellButtons.put(pos, btn);
                gridPane.add(btn, c + 1, r + 1); // offset by 1 for labels
            }
        }
        root.setCenter(gridPane);

        // Bottom: status label
        statusLabel = new Label("Click a cell to place a road.");
        root.setBottom(statusLabel);
        BorderPane.setAlignment(statusLabel, Pos.CENTER);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Mini Motorways");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void updateView(GameState game) {
        Platform.runLater(() -> {
            int rows = GameMap.getRows();
            int cols = GameMap.getCols();

            for (int pos = 0; pos < rows * cols; pos++) {
                Building building = game.buildings.getOrDefault(pos, EMPTYCELL);
                Button btn = cellButtons.get(pos);
                if (btn != null) {
                    updateCellButton(btn, building);
                }
            }
        });
    }

    @Override
    public void askInput() {
        Platform.runLater(() -> {
            statusLabel.setText("Click a cell to place the road.");
            cellButtons.values().forEach(btn -> btn.setDisable(false));
        });
    }

    private void updateCellButton(Button btn, Building building) {
        String text = ".";
        String color = "white";

        if (building instanceof Road) {
            Road road = (Road) building;
            text = "R";

            Optional<Car> carOpt = road.getCar();
            if (carOpt.isPresent()) {
                Color carColor = carOpt.get().getColor();
                String cssColor = convertColorToCss(carColor);
                color = (cssColor != null) ? cssColor : "#90ee90"; // fallback light green
            } else {
                color = "#90ee90"; // light green if no car
            }
        } else if (building instanceof Destination) {
            text = "D";
            color = convertColorToCss(building.getColor());
            if (color == null) color = "#6495ED"; // default cornflower blue fallback
        } else if (building instanceof House) {
            text = "H";
            color = convertColorToCss(building.getColor());
            if (color == null) color = "#FFD700"; // gold fallback
        } else if (building instanceof SkyScraper) {
            text = "S";
            color = convertColorToCss(building.getColor());
            if (color == null) color = "#DAA520"; // goldenrod fallback
        } else if (building instanceof EmptyCell) {
            text = ".";
            color = "white";
        } else {
            text = "?";
            color = "#f08080"; // light coral for unknown
            System.out.println("Unknown building type: " + building.getClass().getName());
        }

        btn.setText(text);
        btn.setStyle("-fx-background-color: " + color + "; -fx-font-weight: bold;");
    }

    // Map your Color enum to CSS hex codes here
    private String convertColorToCss(Color color) {
        if (color == null) return null;
        switch (color) {
            case RED:    return "#FF0000";
            case BLUE:   return "#0000FF";
            case YELLOW: return "#FFFF00";
            case BLACK:  return "#000000";
            // Add more colors as defined in your Color enum
            default:     return null;
        }
    }
}
