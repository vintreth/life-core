package ru.skogmark.life.core;

import org.junit.Test;
import ru.skogmark.life.core.generation.SimpleInitialFrameGenerator;

import java.util.Optional;

import static org.junit.Assert.*;

public class UniverseTest {
    @Test
    public void shouldGenerateInitialFrame() {
        // given
        TestFrameListener frameListener = new TestFrameListener();
        Universe universe = new Universe(frameListener, new SimpleInitialFrameGenerator(20, 20, 5));
        // when
        universe.refresh();
        // then
        Frame frame = frameListener.getNewFrame();
        assertNotNull(frame);
        for (int y = 0; y < frame.getHeight(); y++) {
            for (int x = 0; x < frame.getWidth(); x++) {
                Optional<Cell> cell = frame.getCell(x, y);
                assertTrue(cell.isPresent());
                assertEquals(x, cell.get().getXPos());
                assertEquals(y, cell.get().getYPos());
            }
        }
    }

    @Test
    public void shouldGenerateNextFrame() {
        // given
        TestFrameListener frameListener = new TestFrameListener();
        Universe universe = new Universe(frameListener, new SimpleInitialFrameGenerator(20, 20, 5));
        universe.refresh();
        Frame firstFrame = frameListener.getNewFrame();
        // when
        universe.refresh();
        // then
        Frame secondFrame = frameListener.getNewFrame();
        for (int y = 0; y < secondFrame.getHeight(); y++) {
            for (int x = 0; x < secondFrame.getWidth(); x++) {
                Optional<Cell> cell = secondFrame.getCell(x, y);
                Optional<Cell> prevCell = firstFrame.getCell(x, y);
                assertTrue(cell.isPresent());
                assertTrue(prevCell.isPresent());

                int aliveNeightboursCount = firstFrame.getAliveNeightboursCount(cell.get());
                if (cell.get().isAlive()) {
                    assertTrue(aliveNeightboursCount > Universe.LOWER_LIFE_LIMIT);
                    assertTrue(aliveNeightboursCount < Universe.UPPER_LIFE_LIMIT);

                    if (!prevCell.get().isAlive()) {
                        assertTrue(aliveNeightboursCount == Universe.BORN_CONDITION);
                    }
                }
            }
        }
    }

    private static class TestFrameListener implements FrameListener {
        private Frame newFrame;

        @Override
        public void onFrameRefreshed(Frame newFrame) {
            this.newFrame = newFrame;
        }

        Frame getNewFrame() {
            return newFrame;
        }
    }
}