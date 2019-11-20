package ru.skogmark.life.core;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class Game {

    private static final int FPS_RATE = 1;

    private final Universe universe;
    private final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private ScheduledFuture<?> future;

    Game(Universe universe) {
        this.universe = requireNonNull(universe, "universe");
    }

    public void start() {
        future = executor.scheduleWithFixedDelay(() -> {
            UniverseStatus status = universe.refresh();
            if (status == UniverseStatus.GAME_OVER) {
                System.out.println("Game over");
                stop();
            }
        }, 0, FPS_RATE, TimeUnit.SECONDS);
    }

    public void pause() {
        throw new UnsupportedOperationException("Method pause() is still unsupported");
    }

    public void stop() {
        if (!future.isCancelled()) {
            future.cancel(true);
        }

        try {
            if (!executor.awaitTermination(1, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
