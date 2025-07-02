package it.dani.minimotorways.controller;

import it.dani.minimotorways.model.Car;
import it.dani.minimotorways.model.building.Building;

import java.util.Map;

public class GameState {
    public Map<Integer, Building> buildings;
    public Map<Integer, Car> cars;
    public final int score;
    public State state;
    public final int numOfRoads;

    public GameState(Map<Integer, Building> buildings, Map<Integer, Car> cars, State state, int score, int numOfRoads) {
        this.buildings = buildings;
        this.cars = cars;
        this.state = state;
        this.score = score;
        this.numOfRoads = numOfRoads;
    }
}
