package tech.baconing.honeydew.commands.miscellaneous;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import tech.baconing.honeydew.commands.Command;

public class TestCommand implements Command {
    @Override
    public void run(SlashCommandInteractionEvent event) {
        event.reply("Test command").queue();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("test", "Command used for testing purposes.");
    }

    @Override
    public String getCategory() {
        return "miscellaneous";
    }
}
