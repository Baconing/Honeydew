package tech.baconing.honeydew.commands.currency;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import tech.baconing.honeydew.commands.Command;
import tech.baconing.honeydew.entities.UserEntity;
import tech.baconing.honeydew.runnables.CacheManager;

public class BalanceCommand implements Command {
    @Override
    public void run(SlashCommandInteractionEvent event) {
        User u = event.getOption("user") != null ? event.getOption("user").getAsUser() : event.getUser();
        UserEntity ue = CacheManager.getUserEntity(u.getId());
        if (u.equals(event.getUser())) {
            event.reply("Your balance is $" + ue.getBalance()).queue();
            ue.release();
            return;
        }
        event.reply(u.getAsMention() + "'s balance is $" + ue.getBalance());
        ue.release();
    }

    @Override
    public CommandData getCommandData() {
        return Commands.slash("balance", "Check the balance of you or another user.")
                .addOption(OptionType.USER, "user", "The user whose balance to check.", false);
    }

    @Override
    public String getCategory() {
        return "currency";
    }
}