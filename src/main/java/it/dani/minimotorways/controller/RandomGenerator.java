package it.dani.minimotorways.controller;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.building.HouseType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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

    public static Result generateRandomPositionAndColor(Set<Integer> freeSpots) {
        Color[] colors = {Color.RED, Color.YELLOW, Color.BLUE};
        Color color = colors[random.nextInt(colors.length)];
        List<Integer> toExtract = new ArrayList<>(freeSpots);
        int index1 = random.nextInt(toExtract.size());
        int index2;
        do {
            index2 = random.nextInt(toExtract.size());
        } while (index2 == index1);
        return new Result(toExtract.get(index1), toExtract.get(index2), color);
    }
}
