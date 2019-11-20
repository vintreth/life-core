package ru.skogmark.life.core;

import org.junit.Test;
import ru.skogmark.life.core.generation.SimpleInitialFrameGenerator;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class UniverseTest {
    @Test
    public void should_generate_initial_frame() {
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
    public void should_generate_next_frame() {
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
                    assertTrue(aliveNeightboursCount > Universe.LOWER_LIFE_LIMIT_CELLS);
                    assertTrue(aliveNeightboursCount < Universe.UPPER_LIFE_LIMIT_CELLS);

                    if (!prevCell.get().isAlive()) {
                        assertTrue(aliveNeightboursCount == Universe.BORN_CONDITION_CELLS);
                    }
                }
            }
        }
    }

    @Test
    public void should_return_gameOver_when_new_frame_equals_to_one_of_prev() {
        Frame frame0 = new Frame(2, 2);
        frame0.putCell(Cell.newDeadCell(0, 0));
        frame0.putCell(Cell.newDeadCell(1, 0));
        frame0.putCell(Cell.newAliveCell(0, 1));
        frame0.putCell(Cell.newDeadCell(1, 1));

        Frame frame1 = new Frame(2, 2);
        frame1.putCell(Cell.newDeadCell(0, 0));
        frame1.putCell(Cell.newAliveCell(1, 0));
        frame1.putCell(Cell.newAliveCell(0, 1));
        frame1.putCell(Cell.newDeadCell(1, 1));

        Frame newFrame = new Frame(2, 2);
        newFrame.putCell(Cell.newDeadCell(0, 0));
        newFrame.putCell(Cell.newDeadCell(1, 0));
        newFrame.putCell(Cell.newAliveCell(0, 1));
        newFrame.putCell(Cell.newDeadCell(1, 1));

        List<Frame> frames = List.of(frame0, frame1);

        // when
        boolean result = Universe.isGameOver(newFrame, frames);
        // then
        assertTrue(result);
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