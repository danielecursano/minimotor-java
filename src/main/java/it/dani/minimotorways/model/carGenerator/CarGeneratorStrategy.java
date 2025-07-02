package it.dani.minimotorways.model.carGenerator;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.GameMap;

public interface CarGeneratorStrategy {
    void startGenerating(GameMap gameMap, Color color, int i);
}