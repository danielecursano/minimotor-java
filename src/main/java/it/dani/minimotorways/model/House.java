package it.dani.minimotorways.model;

import it.dani.minimotorways.model.carGenerator.CarGeneratorStrategy;
import it.dani.minimotorways.model.carGenerator.DefaultHouseGenerator;

import static java.lang.Thread.sleep;

public class House extends Building {
    private final GameMap gameMap;
    private final CarGeneratorStrategy generatorStrategy;

    public House(Color color, GameMap gameMap, int pos) {
        this(color, gameMap, new DefaultHouseGenerator(), pos);
    }

    public House(Color color, GameMap gameMap, CarGeneratorStrategy strategy, int pos) {
        super(color);
        this.gameMap = gameMap;
        this.generatorStrategy = strategy;
        generatorStrategy.startGenerating(gameMap, getColor(), pos);
    }

    public String render() {
        return "H";
    }

}
