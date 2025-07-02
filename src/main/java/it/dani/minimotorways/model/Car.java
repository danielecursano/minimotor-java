package it.dani.minimotorways.model;

public class Car {
    private Color color;

    public Car(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return color+"C"+Color.RESET;
    }

}
