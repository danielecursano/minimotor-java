package it.dani.minimotorways.model.building;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.visitor.Visitor;

public class EmptyCell extends Building {
    public EmptyCell() {
        super(Color.BLACK, -1);
    }

    public String render() {
        return " ";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
