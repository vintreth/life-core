package ru.skogmark.life.core;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class FrameEventService implements FrameListener {
    private final List<FrameListener> frameListeners = new ArrayList<>();

    public void addListener(FrameListener frameListener) {
        frameListeners.add(frameListener);
    }

    @Override
    public void onFrameRefreshed(@NotNull Frame newFrame) {
        requireNonNull(newFrame);
        frameListeners.forEach(frameListener -> frameListener.onFrameRefreshed(newFrame));
    }
}
