package it.dani.minimotorways.model.visitor;

import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.building.*;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RoadVisitor implements Visitor {
    private final Set<Road> roads;

    public RoadVisitor(int pos, GameMap gameMap) {
        roads = new HashSet<>();
        for (int dir: GameMap.getDirs()) {
            gameMap.getBuildingAt(pos+dir).accept(this);
        }
    }

    public Set<Road> getRoads() {
        return roads.stream().filter(Road::isFree).collect(Collectors.toSet());
    }

    public void visit(Building building) {

    }

    public void visit(Road road) {
        roads.add(road);
    }

    @Override
    public void visit(House house) {

    }

    @Override
    public void visit(SkyScraper skyscraper) {

    }

    @Override
    public void visit(Destination destination) {

    }

    @Override
    public void visit(EmptyCell emptyCell) {

    }

}

