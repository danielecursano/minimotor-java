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
    private Label scoreLabel;
    private Label roadsLabel; // NEW: number of roads label
    private final Map<Integer, Button> cellButtons = new HashMap<>();

    public void start(Stage primaryStage) {
        int rows = GameMap.getRows();
        int cols = GameMap.getCols();

        BorderPane root = new BorderPane();

        // Score and Roads display
        scoreLabel = new Label("Score: 0");
        roadsLabel = new Label("Roads: 0");
        scoreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        roadsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        HBox scoreBox = new HBox(15, scoreLabel, roadsLabel); // Added roadsLabel
        scoreBox.setAlignment(Pos.CENTER_RIGHT);
        scoreBox.setMinHeight(30);

        // Top: column indices + score/roads on right
        HBox topBox = new HBox();
        topBox.setSpacing(10);
        topBox.setAlignment(Pos.CENTER_LEFT);

        HBox colIndices = new HBox(5);
        colIndices.setAlignment(Pos.CENTER_LEFT);
        colIndices.getChildren().add(new Label("   "));
        for (int c = 0; c < cols; c++) {
            Label label = new Label(String.valueOf(c));
            label.setMinWidth(30);
            label.setAlignment(Pos.CENTER);
            colIndices.getChildren().add(label);
        }

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        topBox.getChildren().addAll(colIndices, spacer, scoreBox);
        root.setTop(topBox);

        // Center: grid
        gridPane = new GridPane();
        gridPane.setGridLinesVisible(true);

        for (int r = 0; r < rows; r++) {
            Label rowLabel = new Label(String.valueOf(r));
            rowLabel.setMinWidth(30);
            rowLabel.setAlignment(Pos.CENTER);
            gridPane.add(rowLabel, 0, r + 1);

            for (int c = 0; c < cols; c++) {
                int pos = r * cols + c;
                Button btn = new Button();
                btn.setMinSize(30, 30);
                btn.setFocusTraversable(false);
                updateCellButton(btn, EMPTYCELL);

                int finalPos = pos;
                btn.setOnAction(e -> gameController.addRoad(finalPos));

                cellButtons.put(pos, btn);
                gridPane.add(btn, c + 1, r + 1);
            }
        }

        root.setCenter(gridPane);

        // Bottom status
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

            // Update score and number of roads
            scoreLabel.setText("Score: " + game.score);
            roadsLabel.setText("Roads: " + game.numOfRoads); // Update numOfRoads label
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
                color = (cssColor != null) ? cssColor : "#90ee90";
            } else {
                color = "#90ee90";
            }
        } else if (building instanceof Destination) {
            text = "D";
            color = convertColorToCss(building.getColor());
            if (color == null) color = "#6495ED";
        } else if (building instanceof House) {
            text = "H";
            color = convertColorToCss(building.getColor());
            if (color == null) color = "#FFD700";
        } else if (building instanceof SkyScraper) {
            text = "S";
            color = convertColorToCss(building.getColor());
            if (color == null) color = "#DAA520";
        } else if (building instanceof EmptyCell) {
            text = ".";
            color = "white";
        } else {
            text = "?";
            color = "#f08080";
            System.out.println("Unknown building type: " + building.getClass().getName());
        }

        btn.setText(text);
        btn.setStyle("-fx-background-color: " + color + "; -fx-font-weight: bold;");
    }

    private String convertColorToCss(Color color) {
        if (color == null) return null;
        switch (color) {
            case RED:    return "#FF0000";
            case BLUE:   return "#0000FF";
            case YELLOW: return "#FFFF00";
            case BLACK:  return "#000000";
            default:     return null;
        }
    }
}
