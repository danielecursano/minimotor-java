package it.dani.minimotorways.model.building;

import it.dani.minimotorways.model.Car;
import it.dani.minimotorways.model.Color;
import it.dani.minimotorways.model.visitor.Visitor;

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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
