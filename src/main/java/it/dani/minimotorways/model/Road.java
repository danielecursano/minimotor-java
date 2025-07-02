package it.dani.minimotorways.model;

import java.util.Optional;

public class Road extends Building {
    private Optional<Car> car = Optional.empty();

    public Road() {
        super(Color.BLACK);
    }

    public boolean isFree() {
        return car.isEmpty();
    }

    public void setCar(Car car) {
        this.car = Optional.of(car);
    }

    public String render() {
        return car.map(value -> Color.RESET.toString() + value.getColor() + "R").orElse("R");
    }
}
