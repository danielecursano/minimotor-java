package it.dani.minimotorways.model.visitor;

import it.dani.minimotorways.model.building.*;

public interface Visitor {
    void visit(Road road);
    void visit(House house);
    void visit(SkyScraper skyscraper);
    void visit(Destination destination);
    void visit(EmptyCell emptyCell);
    void visit(Building building); // Fallback if needed
}
