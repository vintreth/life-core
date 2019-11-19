package ru.skogmark.life.core;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class FrameEventListenerComposite implements FrameListener {

    private final List<FrameListener> frameListeners = new ArrayList<>();

    public void addListener(FrameListener frameListener) {
        frameListeners.add(frameListener);
    }

    @Override
    public void onFrameRefreshed(Frame newFrame) {
        requireNonNull(newFrame);
        frameListeners.forEach(frameListener -> frameListener.onFrameRefreshed(newFrame));
    }
}
