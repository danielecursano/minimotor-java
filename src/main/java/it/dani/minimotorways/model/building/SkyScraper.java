package it.dani.minimotorways.model.building;

import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.carGenerator.FastSkyscraperGenerator;
import it.dani.minimotorways.model.visitor.Visitor;

public class SkyScraper extends House {
    public SkyScraper(Color color, GameMap gameMap, int pos) {
        super(color, gameMap, new FastSkyscraperGenerator(), pos);
    }

    @Override
    public String render() {
        return "S";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
