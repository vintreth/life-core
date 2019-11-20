package ru.skogmark.life.console.command;

import ru.skogmark.life.console.GameContext;

public class StopCommandHandler implements CommandHandler {

    @Override
    public void handle(GameContext gameContext) {
        if (gameContext.getGame() != null) {
            gameContext.getGame().stop();
        }

        TextPreloader.println("Terminating the game", 5000);
        gameContext.setTerminated(true);
    }
}
