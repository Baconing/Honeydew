package tech.baconing.honeydew.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public interface LegacyCommand {
    public void run(MessageReceivedEvent event);

    public String getName();
    public String getDescription();
    public String getCategory();
}
