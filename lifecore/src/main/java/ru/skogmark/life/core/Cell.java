package ru.skogmark.life.core;


public class Cell {

    private final boolean alive;
    private final int xPos;
    private final int yPos;

    private Cell(boolean alive, int xPos, int yPos) {
        this.alive = alive;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public static Cell newDeadCell(int xPos, int yPos) {
        return new Cell(false, xPos, yPos);
    }

    public static Cell newAliveCell(int xPos, int yPos) {
        return new Cell(true, xPos, yPos);
    }

    @Override
    public String toString() {
        return "Cell{" +
                "alive=" + alive +
                ", xPos=" + xPos +
                ", yPos=" + yPos +
                '}';
    }

    public boolean isAlive() {
        return alive;
    }

    int getXPos() {
        return xPos;
    }

    int getYPos() {
        return yPos;
    }
}
