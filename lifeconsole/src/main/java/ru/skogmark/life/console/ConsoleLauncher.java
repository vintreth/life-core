package ru.skogmark.life.console;

import ru.skogmark.life.console.command.CommandHandler;
import ru.skogmark.life.console.command.ConsoleCommand;
import ru.skogmark.life.console.command.StartCommandHandler;
import ru.skogmark.life.console.command.StopCommandHandler;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Optional;

public class ConsoleLauncher {

    public static void main(String[] args) {
        printText("Enter command: ");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            GameContext gameContext = new GameContext();
            while (!gameContext.isTerminated()) {
                String inputLine = reader.readLine();
                if (inputLine != null) {
                    Optional<ConsoleCommand> consoleCommand = ConsoleCommand.byCode(inputLine.toLowerCase());
                    if (consoleCommand.isPresent()) {
                        CommandHandler commandHandler = createCommandHandler(consoleCommand.get());
                        commandHandler.handle(gameContext);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void printText(String text) {
        System.out.print(text);
    }

    private static CommandHandler createCommandHandler(ConsoleCommand consoleCommand) {
        switch (consoleCommand) {
            case START:
                return new StartCommandHandler();
            case STOP:
                return new StopCommandHandler();
            default:
                throw new IllegalArgumentException("Unexpected command: command=" + consoleCommand);
        }
    }
}
