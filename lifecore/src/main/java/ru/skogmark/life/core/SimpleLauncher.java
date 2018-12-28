package ru.skogmark.life.core;

public class SimpleLauncher {
    public static void main(String[] args) {
        try {
            new GameFactory().getGame().start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
