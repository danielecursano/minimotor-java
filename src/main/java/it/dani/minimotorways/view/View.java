package it.dani.minimotorways.view;

import it.dani.minimotorways.controller.GameController;
import it.dani.minimotorways.controller.GameState;

public abstract class View {
    protected final GameController gameController;

    public View() {
        this.gameController = new GameController(this);
    }

    public abstract void updateView(GameState game);
    public abstract void askInput();
}
