package ru.skogmark.life.core;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class Game {
    private static final int FPS_RATE = 1;

    private final ScheduledExecutorService executor;
    private final Universe universe;

    private ScheduledFuture<?> future;

    public Game(@NotNull ScheduledExecutorService executor, Universe universe) {
        this.executor = requireNonNull(executor, "executor");
        this.universe = requireNonNull(universe, "universe");
    }

    public void start() {
        future = executor.scheduleWithFixedDelay(universe::refresh, 0, FPS_RATE, TimeUnit.SECONDS);
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
