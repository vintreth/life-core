package ru.skogmark.life.console.command;

import java.util.Arrays;
import java.util.Optional;

public enum ConsoleCommand {

    START("start"),
    STOP("stop");

    private final String code;

    ConsoleCommand(String code) {
        this.code = code;
    }

    public static Optional<ConsoleCommand> byCode(String code) {
        return Arrays.stream(values())
                .filter(command -> command.code.equals(code))
                .findFirst();
    }
}
