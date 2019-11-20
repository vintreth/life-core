package ru.skogmark.life.console;

import ru.skogmark.life.core.Game;

public class GameContext {

    private Game game;
    private boolean terminated;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public boolean isTerminated() {
        return terminated;
    }

    public void setTerminated(boolean terminated) {
        this.terminated = terminated;
    }
}
