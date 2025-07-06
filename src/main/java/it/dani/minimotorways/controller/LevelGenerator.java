package it.dani.minimotorways.controller;

import it.dani.minimotorways.model.building.HouseType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class LevelGenerator {
    private final GameController gameController;

    public LevelGenerator(GameController gameController) {
        this.gameController = gameController;
    }

    public void start() {
        while (true) {
            try {
                List<HouseType> toBuild = new ArrayList<>();
                int score = gameController.getScore();
                while ( score >= 0 ) {
                    if (score >= 200) {
                        toBuild.add(HouseType.SKYSCRAPER);
                        score -= 200;
                    } else {
                        toBuild.add(HouseType.HOUSE);
                        score -= 100;
                    }
                }
                // every 2 house, build a skyscraper
                new Level(toBuild).generate(gameController.getGameMap());
                gameController.incrementRoads(10);
                gameController.updateGame(State.PLAY);
                sleep(20_000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
