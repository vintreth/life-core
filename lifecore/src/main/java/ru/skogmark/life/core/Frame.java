package ru.skogmark.life.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class Frame {

    private final Cell[][] grid;

    public Frame(int width, int height) {
        this.grid = new Cell[height][width];
        initDeadGrid();
    }

    private void initDeadGrid() {
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                grid[y][x] = Cell.newDeadCell(x, y);
            }
        }
    }

    public void putCell(Cell cell) {
        requireNonNull(cell, "cell");
        checkGrid(cell.getXPos(), cell.getYPos());
        grid[cell.getYPos()][cell.getXPos()] = cell;
    }

    private void checkGrid(int xPos, int yPos) {
        if (!isSizeInBound(xPos, yPos)) {
            throw new RuntimeException("Unable to access cell by its coordinates: xPos=" + xPos + ", yPos=" + yPos
                    + ", grid.sizes=" + grid.length + "x" + grid[0].length);
        }
    }

    Optional<Cell> getCell(int xPos, int yPos) {
        if (isSizeInBound(xPos, yPos)) {
            return Optional.of(grid[yPos][xPos]);
        }
        return Optional.empty();
    }

    private boolean isSizeInBound(int xPos, int yPos) {
        return yPos >= 0
                && yPos < grid.length
                && xPos >= 0
                && xPos < grid[0].length;
    }

    int getWidth() {
        return grid.length > 0 ? grid[0].length : 0;
    }

    int getHeight() {
        return grid.length;
    }

    int getAliveNeightboursCount(Cell cell) {
        Optional[] neightbours = {
                getCell(cell.getXPos() - 1, cell.getYPos() - 1),
                getCell(cell.getXPos(), cell.getYPos() - 1),
                getCell(cell.getXPos() + 1, cell.getYPos() - 1),
                getCell(cell.getXPos() - 1, cell.getYPos()),
                getCell(cell.getXPos() + 1, cell.getYPos()),
                getCell(cell.getXPos() - 1, cell.getYPos() + 1),
                getCell(cell.getXPos(), cell.getYPos() + 1),
                getCell(cell.getXPos() + 1, cell.getYPos() + 1),
        };
        int aliveNeightboursCount = 0;
        for (Optional<Cell> neightbour : neightbours) {
            if (neightbour.isPresent() && neightbour.get().isAlive()) {
                aliveNeightboursCount++;
            }
        }
        return aliveNeightboursCount;
    }

    @Override
    public String toString() {
        return "Frame{" +
                "grid=\n" +
                Arrays.stream(grid)
                        .map(row -> Arrays.stream(row)
                                .map(Cell::toString)
                                .collect(Collectors.joining()))
                        .collect(Collectors.joining("\n")) +
                "\n}";
    }

    public Iterable<Row> getRows() {
        return () -> new Iterator<Row>() {
            private int index;

            @Override
            public boolean hasNext() {
                return index + 1 < grid.length;
            }

            @Override
            public Row next() {
                return new Row(grid[index++]);
            }
        };
    }

    public static class Row {
        private final Cell[] row;

        Row(Cell[] row) {
            this.row = row;
        }

        public Iterable<Cell> getCells() {
            return () -> new Iterator<Cell>() {
                private int index;

                @Override
                public boolean hasNext() {
                    return index + 1 < row.length;
                }

                @Override
                public Cell next() {
                    return row[index++];
                }
            };
        }
    }
}
