package it.dani.minimotorways.model;

import it.dani.minimotorways.model.building.*;
import it.dani.minimotorways.model.visitor.PathVisitor;
import it.dani.minimotorways.model.visitor.RoadVisitor;

import java.io.IOException;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameMap {
    private static final int ROWS = 20;
    private static final int COLS = 20;
    private static final int[] DIRS = {1, -1, COLS, -COLS};
    private static final EmptyCell EMPTY_CELL = new EmptyCell();

    private final Map<Integer, Building> buildings = new HashMap<>();
    private final Map<Integer, Car> cars = new HashMap<>();
    private int score = 0;

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

    public int getScore() {
        return score;
    }

    public static int getRows() {
        return ROWS;
    }

    public static int getCols() {
        return COLS;
    }

    public Map<Integer, Building> getBuildings() {
        return buildings;
    }

    public Map<Integer, Car> getCars() {
        return cars;
    }

    public static boolean isPositionValid(int pos) {
        return pos >= 0 && pos < ROWS * COLS;
    }

    public static int[] getDirs() {
        return DIRS;
    }

    public Building getBuildingAt(int pos) {
        if (!isPositionValid(pos)) {
            return EMPTY_CELL;
        }
        return buildings.getOrDefault(pos, EMPTY_CELL);
    }

    private boolean canBuild(int pos) {
        return (!buildings.containsKey(pos) || buildings.get(pos) instanceof EmptyCell)&& isPositionValid(pos);
    }

    private boolean canRemoveRoad(int x) {
        Building b = getBuildingAt(x);
        if (b instanceof Road) {
            Road road = (Road) b;
            return road.isFree();
        }
        return false;
    }

    public boolean removeRoad(int x) {
        if (canRemoveRoad(x)) {
            buildings.put(x, EMPTY_CELL);
            return true;
        }
        return false;
    }

    public boolean addBuilding(int i, Building building) {
        if (canBuild(i) || canRemoveRoad(i)) {
            buildings.put(i, building);
            logger.info(String.format("[GAME] add building %s at %d", building.getClass(), i));
            return true;
        }
        logger.warning(String.format("[GAME] failed adding building %s at %d", building.getClass(), i));
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

    public void moveCar() {
        PathVisitor pathVisitor = new PathVisitor();
        for (int i = 0; i < ROWS * COLS; i++) {
            getBuildingAt(i).accept(pathVisitor);
        }

        Iterator<Map.Entry<Integer, Car>> iterator = cars.entrySet().iterator();
        Map<Integer, Car> movedCars = new HashMap<>(); // To store cars that moved to new positions

        while (iterator.hasNext()) {
            Map.Entry<Integer, Car> entry = iterator.next();
            int currentPos = entry.getKey();
            Car car = entry.getValue();

            int nextStep = pathVisitor.getPath(currentPos, car.getColor());
            if (nextStep != -1) {
                Building nextBuilding = getBuildingAt(nextStep);

                if (nextBuilding instanceof Road) {
                    Road nextRoad = (Road) nextBuilding;
                    Road oldRoad = (Road) getBuildingAt(currentPos);

                    // Move car to next road
                    nextRoad.setCar(car);
                    oldRoad.free();

                    // Update matrix in pathVisitor
                    pathVisitor.setOne(currentPos);  // Old road now free
                    pathVisitor.setZero(nextStep);   // New road now occupied

                    // Mark for position update after iteration
                    movedCars.put(nextStep, car);

                    // Remove old entry after iteration, do not remove here to avoid concurrent modification
                    iterator.remove();

                } else if (nextBuilding instanceof Destination) {
                    // Car reached destination
                    score += 1;

                    // Free old road
                    Road oldRoad = (Road) getBuildingAt(currentPos);
                    oldRoad.free();

                    // Update matrix in pathVisitor
                    pathVisitor.setOne(currentPos);

                    // Remove car safely while iterating
                    iterator.remove();
                }
            }
        }

        // Add moved cars to map with updated positions after iteration
        cars.putAll(movedCars);
    }

}
