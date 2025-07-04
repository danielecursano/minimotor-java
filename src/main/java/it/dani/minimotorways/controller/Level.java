package it.dani.minimotorways.controller;

import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.building.Destination;
import it.dani.minimotorways.model.building.HouseType;

import java.util.List;
import java.util.Set;

public class Level {
    private final List<HouseType> buildings;

    public Level(List<HouseType> buildings) {
        this.buildings = buildings;
    }

    public void generate(GameMap gameMap) {
        for (HouseType building : buildings) {
            Set<Integer> freeSpots = gameMap.freeSpots();
            RandomGenerator.Result result = RandomGenerator.generateRandomPositionAndColor(freeSpots);
            gameMap.addBuilding(result.end, new Destination(result.color, result.end));
            gameMap.addHouse(result.start, building, result.color);
        }
    }
}
