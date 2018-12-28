package ru.skogmark.life.core;

import ru.skogmark.life.core.generation.InitialFrameGenerator;

import javax.validation.constraints.NotNull;
import java.util.LinkedList;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;

public class Universe {
    static final int LOWER_LIFE_LIMIT = 1;
    static final int UPPER_LIFE_LIMIT = 4;
    static final int BORN_CONDITION = 3;

    private final FrameListener frameListener;
    private final InitialFrameGenerator initialFrameGenerator;
    private final LinkedList<Frame> frames = new LinkedList<>();

    public Universe(@NotNull FrameListener frameListener, @NotNull InitialFrameGenerator initialFrameGenerator) {
        this.frameListener = requireNonNull(frameListener, "frameListener");
        this.initialFrameGenerator = requireNonNull(initialFrameGenerator, "initialFrameGenerator");
    }

    public void refresh() {
        Frame prevFrame = frames.isEmpty() ? null : frames.getLast();
        Frame nextFrame;
        if (isNull(prevFrame)) {
            nextFrame = initialFrameGenerator.generate();
        } else {
            nextFrame = calculateNextFrame(prevFrame);
        }
        addFrame(nextFrame);
        frameListener.onFrameRefreshed(nextFrame);
    }

    private static Frame calculateNextFrame(Frame prevFrame) {
        Frame nextFrame = new Frame(prevFrame.getWidth(), prevFrame.getHeight());
        prevFrame.getRows()
                .forEach(row -> row.getCells()
                        .forEach(cell -> handleCell(cell, prevFrame, nextFrame)));
        return nextFrame;
    }

    private static void handleCell(Cell cell, Frame prevFrame, Frame nextFrame) {
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
        return LOWER_LIFE_LIMIT < aliveNeightboursCount && aliveNeightboursCount < UPPER_LIFE_LIMIT;
    }

    private static boolean canCellBeBorn(int aliveNeightboursCount) {
        return aliveNeightboursCount == BORN_CONDITION;
    }

    private void addFrame(Frame frame) {
        if (frames.size() >= 10) {
            frames.removeFirst();
        }
        frames.addLast(frame);
    }
}
