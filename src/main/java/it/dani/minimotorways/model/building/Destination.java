package it.dani.minimotorways.model.building;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.visitor.Visitor;

public class Destination extends Building {

    public Destination(Color color, int position) {
        super(color, position);
    }

    public String render() {
        return "D";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
