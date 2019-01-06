package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.core.XpHandler;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class AddCoins implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }

        if (args.length < 2) {
            event.getMessage().getChannel().sendMessage("`Bitte Anzahl an Coins angeben! Syntax: !addcoins <@Member|all> <Anzahl>`").queue();
            return;
        }

        int coins;
        try {
            coins = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("`" + args[1] + " ist keine gültige Zahl`").queue();
            return;
        }

        /*
        if (args[0].equals("all")) {

            for (Member m : event.getGuild().getMembers()) {

                if (m.getUser().isBot()) continue;

                int oldCoins = Users.getCoins(m.getUser().getId());
                Users.setCoins(m.getUser().getId(), oldCoins +  coins);

                XpHandler.check(event, m.getUser().getId());

            }

            event.getChannel().sendMessage("`Allen Usern " + coins + " Coins hinzugefügt`").queue();
            return;
        }
        */
        if (event.getMessage().getMentionedMembers().size() == 0) {

            event.getMessage().getChannel().sendMessage("`Bitte User angeben! Snytax: !addcoins <@Member|all> <Anzahl>`").queue();
            return;
        }

        Member member = event.getMessage().getMentionedMembers().get(0);
        String memberId = member.getUser().getId();

        int oldCoins = Users.getCoins(memberId);
        int newCoins = oldCoins + coins;

        Users.setCoins(memberId, newCoins);

        event.getMessage().getChannel().sendMessage("`Erfolgreich Coins von " + member.getEffectiveName() + " auf " + newCoins + " gesetzt!`").queue();
    }


    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }
        event.getMessage().getChannel().sendMessage("`Fügt einem User eine bestimme Anzahl an Coins hinzu. Syntax: !addcoins <@Member|all> <Anzahl>`").queue();
    }
}
