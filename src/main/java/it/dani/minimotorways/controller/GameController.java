package it.dani.minimotorways.controller;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.building.*;
import it.dani.minimotorways.view.View;

import static java.lang.Thread.sleep;

public class GameController {
    private GameMap gameMap;
    private View view;

    public GameController(View view) {
        this.gameMap = new GameMap();
        this.view = view;
        new Thread(() -> {
            while (true) {
                try {
                    sleep(1_000);
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
                    updateGame(State.PLAY);
                    sleep(20_000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void addRoad(int i) {
        gameMap.addBuilding(i, new Road(i));
        updateGame(State.PLAY);
    }

    private void addDestination(int i, Color color) {
        gameMap.addBuilding(i, new Destination(color, i));
    }

    private void addHouse(int i, HouseType houseType, Color color) {
        gameMap.addHouse(i, houseType, color);
    }

    public void updateGame(State state) {
        view.updateView(new GameState(gameMap.getBuildings(), gameMap.getCars(), state));
    }

}
