package it.dani.minimotorways.model.carGenerator;

import it.dani.minimotorways.model.Car;
import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.GameMap;

public class DefaultHouseGenerator implements CarGeneratorStrategy {
    @Override
    public void startGenerating(GameMap gameMap, Color color, int i) {
        new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(5_000);
                    gameMap.addCar(i, new Car(color));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
}
