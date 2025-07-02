package it.dani.minimotorways.model;

public class EmptyCell extends Building {
    public EmptyCell() {
        super(Color.BLACK);
    }

    public String render() {
        return " ";
    }
}
