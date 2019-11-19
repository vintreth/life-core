package ru.skogmark.life.core;

import ru.skogmark.life.core.generation.InitialFrameGenerator;

public class GameFactory {

    public Game getGame(FrameListener frameListener, InitialFrameGenerator initialFrameGenerator) {
        return new Game(new Universe(frameListener, initialFrameGenerator));
    }
}
