package it.dani.minimotorways.model.visitor;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.building.*;

import java.util.ArrayList;
import java.util.List;

public class PathVisitor implements Visitor {
    private List<Integer> matrix = new ArrayList<>();
    private final Color color;

    public PathVisitor(Color color) {
        this.color = color;
    }

    public int getNextCell(int start) {
        int next = start;
        matrix.set(start, 2);
        return next;
    }

    @Override
    public void visit(Road road) {
        matrix.add(road.isFree() ? 1 : 0);
    }

    @Override
    public void visit(House house) {
        matrix.add(0);
    }

    @Override
    public void visit(SkyScraper skyscraper) {
        matrix.add(0);
    }

    @Override
    public void visit(Destination destination) {
        if (destination.getColor() == color) {
            matrix.add(3);
        } else {
            matrix.add(0);
        }
    }

    @Override
    public void visit(EmptyCell emptyCell) {
        matrix.add(0);
    }

    @Override
    public void visit(Building building) {

    }
}
