package ru.skogmark.life.console.command;

import ru.skogmark.life.console.GameContext;
import ru.skogmark.life.console.SystemOutPrinterFrameListener;
import ru.skogmark.life.core.FrameEventListenerComposite;
import ru.skogmark.life.core.Game;
import ru.skogmark.life.core.GameFactory;
import ru.skogmark.life.core.generation.SimpleInitialFrameGenerator;

public class StartCommandHandler implements CommandHandler {

    private static final int DEFAULT_HEIGHT = 20;
    private static final int DEFAULT_WIDTH = 40;
    private static final int DEFAULT_POPULATION_DENSITY = 5;

    @Override
    public void handle(GameContext gameContext) {
        TextPreloader.println("Starting the game", 5000);

        FrameEventListenerComposite frameListener = new FrameEventListenerComposite();
        frameListener.addListener(new SystemOutPrinterFrameListener());

        GameFactory gameFactory = new GameFactory();
        Game game = gameFactory.getGame(
                frameListener,
                new SimpleInitialFrameGenerator(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POPULATION_DENSITY));

        gameContext.setGame(game);

        game.start();
    }
}
