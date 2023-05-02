package tech.baconing.honeydew.commands;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public interface Command {
    public void run(SlashCommandInteractionEvent event);
    public CommandData getCommandData();
    public String getCategory();
}
