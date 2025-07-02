package it.dani.minimotorways.model;

import it.dani.minimotorways.model.carGenerator.FastSkyscraperGenerator;

public class SkyScraper extends House {
    public SkyScraper(Color color, GameMap gameMap, int pos) {
        super(color, gameMap, new FastSkyscraperGenerator(), pos);
    }

    @Override
    public String render() {
        return "S";
    }
}
