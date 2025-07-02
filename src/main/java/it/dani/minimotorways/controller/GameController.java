package it.dani.minimotorways.controller;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.building.*;
import it.dani.minimotorways.view.FXView;
import it.dani.minimotorways.view.View;

import static java.lang.Thread.sleep;

public class GameController {
    private GameMap gameMap;
    private View view;
    private int numOfRoads;

    public GameController(View view) {
        this.gameMap = new GameMap();
        this.numOfRoads = 10;
        this.view = view;
        new Thread(() -> {
            while (true) {
                try {
                    sleep(100);
                    gameMap.moveCar();
                    updateGame(State.PLAY);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    RandomGenerator.Result gen = RandomGenerator.generateRandomPositionAndColor();
                    addDestination(gen.start, gen.color);
                    addHouse(gen.end, HouseType.HOUSE, gen.color);
                    this.numOfRoads += 10;
                    updateGame(State.PLAY);
                    sleep(20_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addRoad(int i) {
        if (numOfRoads > 0) {
            gameMap.addBuilding(i, new Road(i));
            updateGame(State.PLAY);
            numOfRoads--;
        }
    }

    private void addDestination(int i, Color color) {
        gameMap.addBuilding(i, new Destination(color, i));
    }

    private void addHouse(int i, HouseType houseType, Color color) {
        gameMap.addHouse(i, houseType, color);
    }

    public void updateGame(State state) {
        if (view instanceof FXView) {
            javafx.application.Platform.runLater(() -> view.updateView(new GameState(gameMap.getBuildings(), gameMap.getCars(), state, gameMap.getScore(), numOfRoads)));
        } else {
            view.updateView(new GameState(gameMap.getBuildings(), gameMap.getCars(), state, gameMap.getScore(), numOfRoads));
        }
    }

}
