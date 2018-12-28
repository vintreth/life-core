package ru.skogmark.life.core;

import javax.validation.constraints.NotNull;

public interface FrameListener {
    void onFrameRefreshed(@NotNull Frame newFrame);
}
