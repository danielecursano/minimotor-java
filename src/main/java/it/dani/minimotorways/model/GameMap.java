package it.dani.minimotorways.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameMap {
    private static final int ROWS = 64;
    private static final int COLS = 64;
    private static final int[] DIRS = {1, -1, COLS, -COLS};
    private static final EmptyCell EMPTY_CELL = new EmptyCell();

    private final Map<Integer, Building> buildings = new HashMap<>();

    private static final Logger logger = Logger.getLogger(GameMap.class.getName());
    static {
        try {
            // Create a timestamp for the filename
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            String logFilename = "logs/GameMap_" + timestamp + ".log";

            // FileHandler with append = false (overwrite/create new file)
            FileHandler fh = new FileHandler(logFilename, false);
            fh.setFormatter(new SimpleFormatter());
            logger.addHandler(fh);
            logger.setUseParentHandlers(false); // Avoid console logging
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GameMap() {
    }

    private static boolean isPositionValid(int pos) {
        return pos >= 0 && pos < ROWS * COLS;
    }

    private boolean canBuild(int pos) {
        return !buildings.containsKey(pos) && isPositionValid(pos);
    }

    public boolean addBuilding(int i, Building building) {
        if (canBuild(i)) {
            buildings.put(i, building);
            logger.info("[GAME] add building at " + i);
            return true;
        }
        logger.warning("[GAME] failed adding building at " + i);
        return false;
    }

    public boolean addHouse(int i, HouseType house, Color color) {
        if (!canBuild(i)) return false;
        switch (house) {
            case HOUSE -> addBuilding(i, new House(color, this, i));
            case SKYSCRAPER -> addBuilding(i, new SkyScraper(color, this, i));
        }
        return true;
    }

    public boolean addCar(int i, Car car) {
        Set<Road> roads = new HashSet<>();
        for (int dir: DIRS) {
            if (isPositionValid(i+dir) && buildings.containsKey(i+dir) && buildings.get(i+dir) instanceof Road) {
                roads.add((Road) buildings.get(i+dir));
            }
        }
        logger.info("[GAME] roads found:" + roads.size());
        for (Road road : roads) {
            if (road.isFree()) {
                road.setCar(car);
                logger.info("[GAME] add car at road " + i);
                return true;
            }
        }
        logger.warning("[GAME] failed adding car at road " + i);
        return false;
    }

    public void renderMap() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(buildings.computeIfAbsent(i*ROWS+j, key -> EMPTY_CELL));
            }
            System.out.print("\n");
        }
    }

}
