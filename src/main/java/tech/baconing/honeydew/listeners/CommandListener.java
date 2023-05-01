package tech.baconing.honeydew.listeners;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tech.baconing.honeydew.runnables.RegisterCommands;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (RegisterCommands.getCommand(event.getInteraction().getName()) == null) {
            event.reply("Unable to find command, try again later or contact support.").setEphemeral(true).queue();
        } else {
            RegisterCommands.getCommand(event.getInteraction().getName()).run(event);
        }
    }
}
