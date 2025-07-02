package it.dani.minimotorways.controller;

import it.dani.minimotorways.model.Car;
import it.dani.minimotorways.model.building.Building;

import java.util.Map;

public class GameState {
    public Map<Integer, Building> buildings;
    public Map<Integer, Car> cars;
    public State state;

    public GameState(Map<Integer, Building> buildings, Map<Integer, Car> cars, State state) {
        this.buildings = buildings;
        this.cars = cars;
        this.state = state;
    }
}
