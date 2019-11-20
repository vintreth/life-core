package ru.skogmark.life.console.command;

import ru.skogmark.life.console.GameContext;

public interface CommandHandler {
    void handle(GameContext gameContext);
}
