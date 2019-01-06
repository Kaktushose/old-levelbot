package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class StopCommand implements Commands {
    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.MODERATOR, Permissions.Perms.ADMINISTRATOR)) {
            return;
        }

        event.getChannel().sendMessage("`Bot wird heruntergefahren...`").complete();
        event.getJDA().shutdown();
        System.out.println("[SYSTEM] Bot wird heruntergefahren...");
        System.exit(0);
    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.MODERATOR, Permissions.Perms.ADMINISTRATOR)) {
            return;
        }
        event.getChannel().sendMessage("`Fährt den Discord Bot herunter`").queue();
    }
}
