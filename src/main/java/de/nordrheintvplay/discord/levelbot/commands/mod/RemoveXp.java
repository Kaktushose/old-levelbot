package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class RemoveXp implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        if (Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }

        if (event.getMessage().getMentionedMembers().size() == 0) {
            event.getMessage().getChannel().sendMessage("`Bitte User angeben! Snytax: !removexp @Member Anzahl`").queue();
            return;
        }

        if (args.length < 1) {
            event.getMessage().getChannel().sendMessage("`Bitte Anzahl an XP angeben! Syntax: !removexp @Member Anzahl`").queue();
            return;
        }

        Member member = event.getMessage().getMentionedMembers().get(0);
        String memberId = member.getUser().getId();

        Users.setXp(memberId, Users.getXp(memberId) - Integer.valueOf(args[1]));

        event.getMessage().getChannel().sendMessage("`XP von " + member.getEffectiveName() + " erfolgreich auf " + Users.getCoins(memberId) + " XP reduziert`").queue();
    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        if (Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }

        event.getMessage().getChannel().sendMessage("`Entfernt eine bestimme Anzahl an XP Syntax: !removexp <@Member> <Anzahl>`").queue();
    }
}
