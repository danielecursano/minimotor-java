package it.dani.minimotorways.controller;

import it.dani.minimotorways.model.Car;
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
                    for (Car car : gameMap.getCars().values()) {
                        if (car.isExpired()) {
                            updateGame(State.END);
                            return;
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            while (true) {
                try {
                    RandomGenerator.Result gen = RandomGenerator.generateRandomPositionAndColor();
                    boolean response = false;
                    while (!response) {
                        response = addDestination(gen.start, gen.color);
                    }
                    response = false;
                    while (!response) {
                        response = addHouse(gen.end, HouseType.HOUSE, gen.color);
                    }
                    this.numOfRoads += 10;
                    updateGame(State.PLAY);
                    sleep(20_000);
                } catch (Exception e) {
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

    private boolean addDestination(int i, Color color) {
        return gameMap.addBuilding(i, new Destination(color, i));
    }

    private boolean addHouse(int i, HouseType houseType, Color color) {
        return gameMap.addHouse(i, houseType, color);
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public void removeRoad(int i) {
        if (gameMap.removeRoad(i)) {
            updateGame(State.PLAY);
            numOfRoads++;
        }
    }

    public void updateGame(State state) {
        if (view instanceof FXView) {
            javafx.application.Platform.runLater(() -> view.updateView(new GameState(gameMap.getBuildings(), gameMap.getCars(), state, gameMap.getScore(), numOfRoads)));
        } else {
            view.updateView(new GameState(gameMap.getBuildings(), gameMap.getCars(), state, gameMap.getScore(), numOfRoads));
        }
    }

}
