package it.dani.minimotorways.model;

public class Car {
    private Color color;
    private final long spawnTime;

    public Car(Color color) {
        this.color = color;
        spawnTime = System.currentTimeMillis();
    }

    public Color getColor() {
        return color;
    }

    public boolean isExpired() {
        return System.currentTimeMillis() - spawnTime > 60_000;
    }

    @Override
    public String toString() {
        return color+"C"+Color.RESET;
    }

}
