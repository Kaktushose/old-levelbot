package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SetCoins implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }

        if (event.getMessage().getMentionedMembers().size() == 0) {
            event.getMessage().getChannel().sendMessage("`Bitte User angeben! Snytax: !setcoins @Member Anzahl`").queue();
            return;
        }

        if (args.length < 1) {
            event.getMessage().getChannel().sendMessage("`Bitte Anzahl an Coins angeben! Syntax: !setcoins @Member Anzahl`").queue();
            return;
        }

        Member member = event.getMessage().getMentionedMembers().get(0);
        String memberId = member.getUser().getId();
        int coins = Integer.parseInt(args[1]);
        Users.setCoins(memberId, coins);

        event.getMessage().getChannel().sendMessage("`Erfolgreich Coins von " + member.getEffectiveName() + " auf " + coins + " gesetzt!`").queue();

    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }
        event.getMessage().getChannel().sendMessage("`Setzt die Anzahl der Coins eines Users. Syntax: !setcoins @Member Anzahl`").queue();
    }
}
