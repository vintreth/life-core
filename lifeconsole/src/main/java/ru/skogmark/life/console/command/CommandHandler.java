package ru.skogmark.life.console.command;

import ru.skogmark.life.console.LauncherContext;

public interface CommandHandler {
    void handle(LauncherContext launcherContext);
}
