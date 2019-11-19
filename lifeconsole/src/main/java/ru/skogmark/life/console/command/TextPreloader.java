package ru.skogmark.life.console.command;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

class TextPreloader {

    static void println(String text, int millis) {
        System.out.print(text);
        System.out.print(' ');
        showPreloader(millis);
        System.out.println(' ');
    }

    private static void showPreloader(int millis) {
        long currentMillis = System.currentTimeMillis();
        int i = 0;
        while (System.currentTimeMillis() < currentMillis + millis) {
            sleep();
            if (i < 3) {
                System.out.print('.');
                i++;
            } else {
                System.out.print(IntStream.range(0, i)
                        .mapToObj(j -> "\b")
                        .collect(Collectors.joining()));
                i = 0;
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(750);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
