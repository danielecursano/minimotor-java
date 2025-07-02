package it.dani.minimotorways.model;

public enum Color {
    RED("\u001B[31m", 2),
    BLUE("\u001B[34m", 3),
    YELLOW("\u001B[33m", 4),
    BLACK("\u001B[30m", 0),
    RESET("\u001B[0m", 0);

    private final String code;
    private final int id;

    Color(String code, int id) {
        this.code = code;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return code;
    }

}
