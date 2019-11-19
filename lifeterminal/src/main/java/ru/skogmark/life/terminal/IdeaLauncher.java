package ru.skogmark.life.terminal;

import ru.skogmark.life.core.FrameEventListenerComposite;
import ru.skogmark.life.core.Game;
import ru.skogmark.life.core.GameFactory;
import ru.skogmark.life.core.generation.SimpleInitialFrameGenerator;

public class IdeaLauncher {

    private static final int DEFAULT_HEIGHT = 20;
    private static final int DEFAULT_WIDTH = 40;
    private static final int DEFAULT_POPULATION_DENSITY = 5;

    public static void main(String[] args) {
        try {
            GameFactory gameFactory = new GameFactory();
            FrameEventListenerComposite frameListener = new FrameEventListenerComposite();
            frameListener.addListener(new SystemOutPrinterFrameListener());

            Game game = gameFactory.getGame(
                    frameListener,
                    new SimpleInitialFrameGenerator(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POPULATION_DENSITY));
            game.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
