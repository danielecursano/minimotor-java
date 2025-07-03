package it.dani.minimotorways.view;

import it.dani.minimotorways.controller.GameState;
import it.dani.minimotorways.controller.State;
import it.dani.minimotorways.model.GameMap;
import it.dani.minimotorways.model.building.Building;
import it.dani.minimotorways.model.building.EmptyCell;
import it.dani.minimotorways.model.building.Road;

import java.util.Scanner;


public class TUIView extends View {
    static Building EMPTYCELL = new EmptyCell();
    static Scanner scanner = new Scanner(System.in);

    public TUIView() {
        super();
        new Thread(() -> {
            while (true) {
                askInput();
            }
        }).start();
    }

    @Override
    public void updateView(GameState game) {
        clearConsole();
        switch (game.state) {
            case PLAY -> renderGame(game);
            case END -> renderEnd(game);
        }
    }

    private void renderGame(GameState game) {
        if (game.state == State.PLAY) {
            System.out.print("    "); // spacing for row index
            for (int col = 0; col < GameMap.getCols(); col++) {
                System.out.printf("%d ", col);
            }
            System.out.println();

            for (int i = 0; i < GameMap.getRows(); i++) {

                System.out.printf("%2d |", i); // row label

                for (int j = 0; j < GameMap.getCols(); j++) {
                    System.out.print(game.buildings.computeIfAbsent(i * GameMap.getRows() + j, key -> EMPTYCELL));
                }
                System.out.print("\n");
            }
        }
    }

    private void renderEnd(GameState game) {
        System.out.println("The game is finished. Final score: " + game.score);
        System.exit(0);
    }

    @Override
    public void askInput() {
        System.out.println("Where do you want to place the road?");
        String[] input = scanner.nextLine().split(" ");
        int row = Integer.parseInt(input[0]);
        int col = Integer.parseInt(input[1]);
        int res = row * GameMap.getCols() + col;
        gameController.addRoad(res);
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
