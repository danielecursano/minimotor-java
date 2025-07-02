package it.dani.minimotorways.model;

import it.dani.minimotorways.model.building.Destination;
import it.dani.minimotorways.model.building.HouseType;
import it.dani.minimotorways.model.building.Road;
import org.junit.jupiter.api.Test;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

class GameMapTest {

    @Test
    void renderMap() throws InterruptedException {
        GameMap gameMap = new GameMap();
        assertTrue(gameMap.addHouse(5, HouseType.HOUSE, Color.RED));
        assertTrue(gameMap.addHouse(10, HouseType.SKYSCRAPER, Color.BLUE));
        assertTrue(gameMap.addBuilding(4, new Destination(Color.RED)));
        assertTrue(gameMap.addBuilding(3, new Road()));
        assertTrue(gameMap.addBuilding(11, new Road()));
        gameMap.renderMap();
        sleep(6000);
        gameMap.renderMap();
    }

}