package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.core.XpHandler;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class AddXp implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }

        if (args.length < 2) {
            event.getMessage().getChannel().sendMessage("`Bitte Anzahl an XP angeben! Syntax: !addxp <@Member|all> <Anzahl>`").queue();
            return;
        }

        int xp;

        try {
            xp = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("`" + args[1] + " ist keine gültige Zahl!`").queue();
            return;
        }

        /*
        if (args[0].equals("all")) {

            for (Member m : event.getGuild().getMembers()) {

                if (m.getUser().isBot()) continue;

                int oldXp = Users.getXp(m.getUser().getId());
                Users.setXp(m.getUser().getId(), oldXp + xp);

                XpHandler.check(event, m.getUser().getId());

            }

            event.getChannel().sendMessage("`Allen Usern " + xp + " XP hinzugefügt`").queue();

            return;
        }
        */
        if (event.getMessage().getMentionedMembers().size() == 0) {
            event.getMessage().getChannel().sendMessage("`Bitte User angeben! Snytax: !addxp <@Member|all> <Anzahl>`").queue();
            return;

        }

        Member member = event.getMessage().getMentionedMembers().get(0);
        String memberId = member.getUser().getId();

        int oldXp = Users.getXp(memberId);
        int newXp = oldXp + xp;

        Users.setXp(memberId, newXp);

        event.getMessage().getChannel().sendMessage("`Erfolgreich XP von " + member.getEffectiveName() + " auf " + newXp + " gesetzt!`").queue();

        XpHandler.check(event, event.getMember().getUser().getId());

    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }
        event.getMessage().getChannel().sendMessage("`Fügt einem User eine bestimme Anzahl an XP hinzu. Syntax: !addxp <@Member|all> <Anzahl>`").queue();
    }
}


