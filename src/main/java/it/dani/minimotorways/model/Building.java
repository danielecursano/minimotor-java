package it.dani.minimotorways.model;

public abstract class Building {
    private final Color color;

    public Building(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public abstract String render();

    @Override
    public String toString() {
        return color + render() + Color.RESET;
    }

}

