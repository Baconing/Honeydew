package tech.baconing.honeydew;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.baconing.honeydew.runnables.RegisterListeners;
import tech.baconing.honeydew.runnables.ThreadManager;
import tech.baconing.honeydew.util.Config;

import java.util.HashMap;

public class Honeydew {
    private static final Logger logger = LogManager.getLogger(Honeydew.class);
    private static JDA jda;
    private static ThreadManager threadManager;

    public static void start() {
        logger.info("Starting Honeydew...");
        threadManager = new ThreadManager();
        threadManager.start();
        jda = JDABuilder.createDefault(Config.getConfig().getToken()).build();
        threadManager.add(new RegisterListeners());
    }

    public static JDA getJda() {
        return jda;
    }

    public static ThreadManager getThreadManager() {
        return threadManager;
    }
}
