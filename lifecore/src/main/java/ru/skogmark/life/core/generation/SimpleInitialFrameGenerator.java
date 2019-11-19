package ru.skogmark.life.core.generation;

import ru.skogmark.life.core.Cell;
import ru.skogmark.life.core.Frame;

import java.util.Random;

public class SimpleInitialFrameGenerator implements InitialFrameGenerator {

    private final int initialWidth;
    private final int initialHeight;
    private final int initialPopulationDensity;

    public SimpleInitialFrameGenerator(int initialWidth, int initialHeight, int initialPopulationDensity) {
        this.initialWidth = initialWidth;
        this.initialHeight = initialHeight;
        this.initialPopulationDensity = initialPopulationDensity;
    }

    @Override
    public Frame generate() {
        Random random = new Random();
        Frame frame = new Frame(initialWidth, initialHeight);
        for (int y = 0; y < initialHeight; y++) {
            for (int x = 0; x < initialWidth; x++) {
                if (random.nextInt(initialPopulationDensity) > 0) {
                    frame.putCell(Cell.newDeadCell(x, y));
                } else {
                    frame.putCell(Cell.newAliveCell(x, y));
                }
            }
        }
        return frame;
    }
}
