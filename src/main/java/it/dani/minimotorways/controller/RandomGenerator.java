package it.dani.minimotorways.controller;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.GameMap;

import java.util.Random;

public class RandomGenerator {
    private static final Random random = new Random();

    public static class Result {
        public final int start, end;
        public final Color color;

        public Result(int start, int end, Color color) {
            this.start = start;
            this.end = end;
            this.color = color;
        }
    }

    public static Result generateRandomPositionAndColor() {
        Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
        Color color = colors[random.nextInt(colors.length)];
        return new Result(random.nextInt(GameMap.getRows()*GameMap.getCols()), random.nextInt(GameMap.getRows()*GameMap.getCols()), color);
    }
}
