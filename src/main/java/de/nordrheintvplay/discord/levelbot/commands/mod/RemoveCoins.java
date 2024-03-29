package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import static de.nordrheintvplay.discord.levelbot.utils.Permissions.Perms;

public class RemoveCoins implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (Permissions.hasOneOfRoles(event.getMember(), Perms.ADMINISTRATOR, Perms.MODERATOR)) {
            return;
        }

        if (event.getMessage().getMentionedMembers().size() == 0) {
            event.getMessage().getChannel().sendMessage("`Bitte User angeben! Snytax: !removecoins <@Member> <Anzahl>`").queue();
            return;
        }

        if (args.length < 1) {
            event.getMessage().getChannel().sendMessage("`Bitte Anzahl an Coins angeben! Syntax: !removecoins <@Member> <Anzahl>`").queue();
            return;
        }

        Member member = event.getMessage().getMentionedMembers().get(0);
        String memberId = member.getUser().getId();

        Users.setCoins(memberId, Users.getCoins(memberId) - Integer.valueOf(args[1]));

        event.getMessage().getChannel().sendMessage("`Coins von " + member.getEffectiveName() + " erfolgreich auf " + Users.getCoins(memberId) + " Münzen reduziert`").queue();

    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {

        if (Permissions.hasOneOfRoles(event.getMember(), Perms.ADMINISTRATOR, Perms.MODERATOR)) {
            return;
        }

        event.getMessage().getChannel().sendMessage("`Entfernt eine bestimme Anzahl an Münzen Syntax: !removecoins <@Member> <Anzahl>`").queue();

    }
}
