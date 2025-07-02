package it.dani.minimotorways.model.building;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.visitor.Visitor;

public abstract class Building {
    private final Color color;
    private final int pos;

    public Building(Color color, int position) {
        this.color = color;
        this.pos = position;
    }

    public Color getColor() {
        return color;
    }

    public int getPos() {
        return pos;
    }

    public abstract String render();

    @Override
    public String toString() {
        return color + render() + Color.RESET;
    }

    public abstract void accept(Visitor visitor);

}

