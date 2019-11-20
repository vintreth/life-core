package ru.skogmark.life.core;

import ru.skogmark.life.core.generation.InitialFrameGenerator;

import java.util.Collection;
import java.util.LinkedList;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static ru.skogmark.life.core.UniverseStatus.ALIVE;
import static ru.skogmark.life.core.UniverseStatus.GAME_OVER;

class Universe {

    static final int LOWER_LIFE_LIMIT_CELLS = 1;
    static final int UPPER_LIFE_LIMIT_CELLS = 4;
    static final int BORN_CONDITION_CELLS = 3;

    private static final int HISTORY_VOLUME_FRAMES = 10;

    private final FrameListener frameListener;
    private final InitialFrameGenerator initialFrameGenerator;
    private final LinkedList<Frame> frames = new LinkedList<>();

    Universe(FrameListener frameListener, InitialFrameGenerator initialFrameGenerator) {
        this.frameListener = requireNonNull(frameListener, "frameListener");
        this.initialFrameGenerator = requireNonNull(initialFrameGenerator, "initialFrameGenerator");
    }

    UniverseStatus refresh() {
        Frame prevFrame = frames.isEmpty() ? null : frames.getLast();
        Frame nextFrame;
        if (isNull(prevFrame)) {
            nextFrame = initialFrameGenerator.generate();
        } else {
            nextFrame = calculateNextFrame(prevFrame);
        }

        if (isGameOver(nextFrame, frames)) {
            return GAME_OVER;
        }

        addFrame(nextFrame);
        frameListener.onFrameRefreshed(nextFrame);
        return ALIVE;
    }

    private static Frame calculateNextFrame(Frame prevFrame) {
        Frame nextFrame = new Frame(prevFrame.getWidth(), prevFrame.getHeight());
        prevFrame.getRows()
                .forEach(row -> row.getCells()
                        .forEach(cell -> calculateCell(cell, prevFrame, nextFrame)));
        return nextFrame;
    }

    private static void calculateCell(Cell cell, Frame prevFrame, Frame nextFrame) {
        int aliveNeightboursCount = prevFrame.getAliveNeightboursCount(cell);
        if (cell.isAlive()) {
            if (canCellStayAlive(aliveNeightboursCount)) {
                nextFrame.putCell(Cell.newAliveCell(cell.getXPos(), cell.getYPos()));
            } else {
                nextFrame.putCell(Cell.newDeadCell(cell.getXPos(), cell.getYPos()));
            }
        } else {
            if (canCellBeBorn(aliveNeightboursCount)) {
                nextFrame.putCell(Cell.newAliveCell(cell.getXPos(), cell.getYPos()));
            } else {
                nextFrame.putCell(Cell.newDeadCell(cell.getXPos(), cell.getYPos()));
            }
        }
    }

    private static boolean canCellStayAlive(int aliveNeightboursCount) {
        return LOWER_LIFE_LIMIT_CELLS < aliveNeightboursCount && aliveNeightboursCount < UPPER_LIFE_LIMIT_CELLS;
    }

    private static boolean canCellBeBorn(int aliveNeightboursCount) {
        return aliveNeightboursCount == BORN_CONDITION_CELLS;
    }

    static boolean isGameOver(Frame nextFrame, Collection<Frame> frames) {
        return frames.stream()
                .anyMatch(frame -> frame.equals(nextFrame));
    }

    private void addFrame(Frame frame) {
        if (frames.size() >= HISTORY_VOLUME_FRAMES) {
            frames.removeFirst();
        }
        frames.addLast(frame);
    }
}
