package tech.baconing.honeydew.runnables;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import tech.baconing.honeydew.Honeydew;
import tech.baconing.honeydew.commands.Command;
import tech.baconing.honeydew.commands.LegacyCommand;
import tech.baconing.honeydew.commands.currency.BalanceCommand;
import tech.baconing.honeydew.commands.miscellaneous.TestCommand;

import java.util.HashMap;

public class RegisterCommands extends EndableRunnable {
    private final static Logger logger = LogManager.getLogger(RegisterCommands.class);

    private static HashMap<CommandData, Command> commands = new HashMap<>();
    private static HashMap<String, LegacyCommand> legacyCommands = new HashMap<>();

    @Override
    public void run() {
        if (isRunning()) {
            logger.info("Registering commands...");
            commands.put(new TestCommand().getCommandData(), new TestCommand());
            commands.put(new BalanceCommand().getCommandData(), new BalanceCommand());

            for (CommandData commandData : commands.keySet()) {
                Honeydew.getJda().getShards().forEach(jda -> {
                    jda.upsertCommand(commandData).queue();
                });
                logger.trace("Registered command: " + commandData.getName());
            }

//            Honeydew.getJda().retrieveCommands().queue(commands -> {
//                for (net.dv8tion.jda.api.interactions.commands.Command c : commands) {
//                    c.delete().queue();
//                }
//            });

            logger.info("Registered commands.");

            logger.info("Registering legacy commands...");
            logger.info("Registered legacy commands.");
        }
        stopped = true;
    }

    @Override
    public String getName() {
        return "RegisterCommands";
    }

    @Nullable
    public static Command getCommand(CommandData commandData) {
        return commands.get(commandData);
    }

    @Nullable
    public static Command getCommand(String name) {
        for (CommandData commandData : commands.keySet()) {
            if (commandData.getName().equals(name)) {
                return commands.get(commandData);
            }
        }
        return null;
    }

    public static HashMap<CommandData, Command> getCommands() {
        return commands;
    }

    @Nullable
    public static LegacyCommand getLegacyCommand(String name) {
        return legacyCommands.get(name);
    }

    public static HashMap<String, LegacyCommand> getLegacyCommands() {
        return legacyCommands;
    }
}
