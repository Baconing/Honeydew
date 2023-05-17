package tech.baconing.honeydew.commands.currency;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import tech.baconing.honeydew.commands.Command;
import tech.baconing.honeydew.entities.GlobalConfigEntity;
import tech.baconing.honeydew.entities.UserEntity;
import tech.baconing.honeydew.runnables.CacheManager;

public class WorkCommand implements Command {

    @Override
    public void run(SlashCommandInteractionEvent event) {
        // check if currenttimemillis is greater than UserEntity.getWorkTimestamp - GlobalConfigEntity.getWorkCooldown
        UserEntity ue = CacheManager.getUserEntity(event.getUser().getId());
        GlobalConfigEntity ge = CacheManager.getGlobalConfigEntity();
        if (ue == null) {
            // TODO: create event for adding a user to the database on first usage.
            event.reply("wip lmao").queue();
            return;
        }
        ue.request();
        ge.request();
        if (CacheManager.getUserEntity(event.getUser().getId()).getWorkCooldown() + CacheManager.getGlobalConfigEntity().getWorkCooldown() > System.currentTimeMillis()) {
            event.reply("You can't work yet!").queue();
            return;
        }
    }


    @Override
    public CommandData getCommandData() {
        return Commands.slash("work", "Work for money.");
    }

    @Override
    public String getCategory() {
        return "currency";
    }
}
