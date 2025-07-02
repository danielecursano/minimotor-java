package it.dani.minimotorways.model;

public enum Color {
    RED("\u001B[31m"),
    BLUE("\u001B[34m"),
    YELLOW("\u001B[33m"),
    BLACK("\u001B[30m"),
    RESET("\u001B[0m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }

}
