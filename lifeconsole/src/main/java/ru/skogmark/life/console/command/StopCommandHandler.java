package ru.skogmark.life.console.command;

import ru.skogmark.life.console.LauncherContext;

public class StopCommandHandler implements CommandHandler {

    @Override
    public void handle(LauncherContext launcherContext) {
        if (launcherContext.getGame() != null) {
            launcherContext.getGame().stop();
        }

        launcherContext.setTerminated(true);
    }
}
