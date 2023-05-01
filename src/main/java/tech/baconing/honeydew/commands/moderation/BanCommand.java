package tech.baconing.honeydew.commands.moderation;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import tech.baconing.honeydew.commands.Command;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class BanCommand implements Command {
    //todo: add database interaction and fix weird bugs

    @Override
    public void run(SlashCommandInteractionEvent event) {
        Member member = event.getInteraction().getOption("user").getAsMember();
        Member author = event.getMember();
        String reason = event.getInteraction().getOption("reason") != null ? event.getInteraction().getOption("reason").getAsString() : "No reason provided.";
        String duration = event.getInteraction().getOption("duration") != null ? event.getInteraction().getOption("duration").getAsString() : "null";
        boolean silent = event.getInteraction().getOption("silent") != null ? event.getInteraction().getOption("silent").getAsBoolean() : false;

        if (!(author.hasPermission(Permission.BAN_MEMBERS))) {
            event.reply("You do not have permission to ban members.").setEphemeral(true).queue();
            return;
        }

        if (!(author.canInteract(member)) || member.isOwner()) {
            event.reply("You cannot ban this user.").setEphemeral(true).queue();
            return;
        }

//        if (event.getGuild().getSelfMember().canInteract(member)) {
//            event.reply("I cannot ban this user.").setEphemeral(true).queue();
//            return;
//        }

        Duration durationMillis = null;
        // if (duration != null || duration.equals("")) durationMillis = Duration.parse(duration);

        member.getUser().openPrivateChannel().queue(dm -> {
            dm.sendMessage("You have been banned from " + event.getGuild().getName() + " for " + duration + " for the following reason: " + reason).queue();
        });

        member.ban(0, TimeUnit.DAYS).reason(reason).queue();

        event.reply("Banned " + member.getAsMention()).queue();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("ban", "Ban a user from the server.")
                .addOption(OptionType.USER, "user", "The user to ban.", true)
                .addOption(OptionType.STRING, "reason", "The reason for the ban.", false)
                .addOption(OptionType.STRING, "duration", "The duration of the ban.", false)
                .addOption(OptionType.BOOLEAN, "silent", "Whether or not to send a message to the user.", false)
                .setGuildOnly(true);
    }

    @Override
    public String getCategory() {
        return "moderation";
    }
}
