package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.core.XpHandler;
import de.nordrheintvplay.discord.levelbot.json.User;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SetXp implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }

        if (event.getMessage().getMentionedMembers().size() == 0) {
            event.getMessage().getChannel().sendMessage("`Bitte User angeben! Snytax: !setxp @Member Anzahl`").queue();
            return;
        }

        if (args.length < 1) {
            event.getMessage().getChannel().sendMessage("`Bitte Anzahl an Coins angeben! Syntax: !setxp @Member Anzahl`").queue();
            return;
        }

        Member member = event.getMessage().getMentionedMembers().get(0);
       User user  = Users.getUser(member.getUser().getIdLong());

       int xp;
        try {
            xp = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("`" + args[1] + " ist keine gültige Zahl!").queue();
            return;
        }
        user.setXp(xp).save();

        event.getMessage().getChannel().sendMessage("`Erfolgreich XP von " + member.getEffectiveName() + " auf " + xp + " gesetzt!`").queue();

        XpHandler.check(event, Users.getUser(event.getMember().getUser().getIdLong()));

    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }
        event.getMessage().getChannel().sendMessage("`Setzt die Anzahl der XP eines Users. Syntax: !setxp @Member Anzahl`").queue();
    }

}
