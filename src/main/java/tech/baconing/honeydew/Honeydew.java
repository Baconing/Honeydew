package tech.baconing.honeydew;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;
import okhttp3.Cache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tech.baconing.honeydew.entities.UserEntity;
import tech.baconing.honeydew.runnables.CacheManager;
import tech.baconing.honeydew.runnables.RegisterListeners;
import tech.baconing.honeydew.runnables.ThreadManager;
import tech.baconing.honeydew.util.Config;
import tech.baconing.honeydew.util.Database;

import java.sql.SQLException;
import java.util.HashMap;

public class Honeydew {
    private static final Logger logger = LogManager.getLogger(Honeydew.class);
    private static ShardManager jda;
    private static ThreadManager threadManager;

    public static void start() throws SQLException {
        logger.info("Starting Honeydew...");
        threadManager = new ThreadManager();
        threadManager.start();
        jda = DefaultShardManagerBuilder.createDefault(Config.getConfig().getToken()).build();
        threadManager.add(new RegisterListeners());
        Database.init();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                threadManager.end().wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            jda.shutdown();
        }));

        UserEntity asd = new UserEntity("521808271368126467");
        asd.setBalance(100L);
        CacheManager.saveUserEntity(asd);
    }

    public static ShardManager getJda() {
        return jda;
    }

    public static ThreadManager getThreadManager() {
        return threadManager;
    }
}
