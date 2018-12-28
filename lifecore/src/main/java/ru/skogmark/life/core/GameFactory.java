package ru.skogmark.life.core;

import ru.skogmark.life.core.generation.SimpleInitialFrameGenerator;

import java.util.concurrent.Executors;

class GameFactory {
    private static final int DEFAULT_HEIGHT = 50;
    private static final int DEFAULT_WIDTH = 100;
    private static final int DEFAULT_POPULATION_DENSITY = 5;

    Game getGame() {
        return new Game(Executors.newSingleThreadScheduledExecutor(), getUniverse());
    }

    private static Universe getUniverse() {
        return new Universe(getFrameEventService(), getSimpleInitialFrameGenerator());
    }

    private static FrameEventService getFrameEventService() {
        FrameEventService frameEventService = new FrameEventService();
        frameEventService.addListener(new SimpleSystemOutPrinterFrameListener());
        return frameEventService;
    }

    private static SimpleInitialFrameGenerator getSimpleInitialFrameGenerator() {
        return new SimpleInitialFrameGenerator(DEFAULT_WIDTH, DEFAULT_HEIGHT, DEFAULT_POPULATION_DENSITY);
    }
}
