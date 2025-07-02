package it.dani.minimotorways.model;

import it.dani.minimotorways.model.building.*;
import it.dani.minimotorways.model.visitor.RoadVisitor;

import java.io.IOException;
import java.util.*;
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
    private final Map<Integer, Car> cars = new HashMap<>();

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
        new Thread(()-> {
            // move cars
        }).start();
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getCols() {
        return COLS;
    }

    public static boolean isPositionValid(int pos) {
        return pos >= 0 && pos < ROWS * COLS;
    }

    public static int[] getDirs() {
        return DIRS;
    }

    public Optional<Building> getBuildingAt(int pos) {
        if (!isPositionValid(pos)) {
            return Optional.empty();
        }
        return Optional.ofNullable(buildings.get(pos));
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

    public boolean addCar(int i, Color carColor) {
        Set<Road> roads = new RoadVisitor(i, this).getRoads();
        for (Road road : roads) {
            Car newCar = new Car(carColor);
            road.setCar(newCar);
            logger.info("[GAME] add car at road " + i);
            cars.put(road.getPos(), newCar);
            return true;
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

    public void moveCar() {

    }

}
