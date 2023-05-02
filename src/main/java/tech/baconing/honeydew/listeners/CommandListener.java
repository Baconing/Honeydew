package tech.baconing.honeydew.listeners;

import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tech.baconing.honeydew.commands.AutoCompleteableCommand;
import tech.baconing.honeydew.commands.Command;
import tech.baconing.honeydew.commands.LegacyCommand;
import tech.baconing.honeydew.runnables.RegisterCommands;
import tech.baconing.honeydew.util.Config;

public class CommandListener extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (RegisterCommands.getCommand(event.getInteraction().getName()) == null) {
            event.reply("Unable to find command, try again later or contact support.").setEphemeral(true).queue();
        } else {
            RegisterCommands.getCommand(event.getInteraction().getName()).run(event);
        }
    }

    @Override
    public void onCommandAutoCompleteInteraction(CommandAutoCompleteInteractionEvent event) {
        Command command = RegisterCommands.getCommand(event.getInteraction().getName());
        if (RegisterCommands.getCommand(event.getInteraction().getName()) == null) { return; }
        AutoCompleteableCommand autoCompleteableCommand = (AutoCompleteableCommand) command;

        autoCompleteableCommand.runAutoComplete(event);
    }
}
