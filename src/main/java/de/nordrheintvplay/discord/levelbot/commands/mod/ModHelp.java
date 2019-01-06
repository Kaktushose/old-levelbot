package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class ModHelp implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN)
                .setTitle("Hilfe für die Moderatoren")
                .addField("Commands", "Prefix: `!`", false)
                .addField("`!addxp <@Member> <Anzahl>`", "fügt einem User XP hinzu", false)
                .addField("`!setxp <@Member> <Anzahl>`", "setzt die XP eines Users", false)
                .addField("`!addcoins <@Member> <Anzahl>`", "fügt einem User Coins hinzu", false)
                .addField("`!setcoins <@Member> <Anzahl>`", "setzt die Coins eines Users" ,false)
                .addField("`!removexp <@Member> <Anzahl>`", "entfernt XP von einem User", false)
                .addField("`!removecoins <@Member> <Anzahl>`", "entfernt Coins von einem User", false)
                .addField("`!setprice` <booster|premium|ultra> <Anzahl>", "setzt den Preis eines Produkts", false)
                .addField("`!init`", "Setzt die Datenbank neu auf", false)
                .addField("`!stop`", "Fährt den Bot herunter", false);
        event.getMessage().getChannel().sendMessage(eb.build()).queue();

    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        execute(event, args);
    }
}
