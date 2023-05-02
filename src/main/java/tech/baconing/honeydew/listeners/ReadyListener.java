package tech.baconing.honeydew.listeners;

import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import okhttp3.Cache;
import org.jetbrains.annotations.NotNull;
import tech.baconing.honeydew.Honeydew;
import tech.baconing.honeydew.runnables.CacheManager;
import tech.baconing.honeydew.runnables.RegisterCommands;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@NotNull ReadyEvent event) {
        Honeydew.getThreadManager().add(new RegisterCommands());
        Honeydew.getThreadManager().add(new CacheManager());
    }
}
