package ru.skogmark.life.core;

import java.util.stream.IntStream;

public class SimpleSystemOutPrinterFrameListener implements FrameListener {
    private static final char ALIVE_CELL = '\u2593';
    private static final char DEAD_CELL = '\u2591';

    @Override
    public void onFrameRefreshed(Frame newFrame) {
        clearScreen();
        for (Frame.Row row : newFrame.getRows()) {
            for (Cell cell : row.getCells()) {
                print(cell.isAlive() ? ALIVE_CELL : DEAD_CELL);
            }
            newLine();
        }
    }

    private static void clearScreen() {
        IntStream.range(0, 100).forEach(i -> newLine());
    }

    private static void print(char c) {
        System.out.print(c);
    }

    private static void print(String symbols) {
        System.out.print(symbols);
    }

    private static void newLine() {
        System.out.println();
    }
}
