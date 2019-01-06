package de.nordrheintvplay.discord.levelbot.commands.level;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.LevelUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class StatsCommand implements Commands {
    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (event.getMessage().getMentionedMembers().size() == 0) {

            String userId = event.getAuthor().getId();
            String items = "";

            if (Users.hasBooster(userId)) {
                items = "Münzenbooster ";
            }

            if (Users.hasPremium(userId)) {
                items = items + "Premium-Rang ";
            }

            if (Users.hasUltra(userId)) {
                items = items + "ULTRA-Rang";
            }

            items = items.trim().replaceAll(" ", ", ");

            if (items.isEmpty()) items = "keine";

            event.getChannel().sendMessage(new EmbedBuilder()
                    .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl())
                    .setDescription("Kontoinformation")
                    .setColor(Color.green)
                    .addField("Deine aktuellen XP", String.valueOf(Users.getXp(userId)), false)
                    .addField("Deine aktuellen Münzen", String.valueOf(Users.getCoins(userId)), false)
                    .addField("Deine aktuelle Stufe", LevelUtils.getRoleByLevel(Users.getRole(event.getAuthor().getId())).getName(), false)
                    .addField("Deine Items", items, false)
                    .build()).queue();
        } else {

            String userId = event.getMessage().getMentionedUsers().get(0).getId();

            String items = "";

            if (Users.hasBooster(userId)) {
                items = "Münzenbooster ";
            }

            if (Users.hasPremium(userId)) {
                items = items + "Premium-Rang ";
            }

            if (Users.hasUltra(userId)) {
                items = items + "ULTRA-Rang";
            }

            items = items.trim().replaceAll(" ", ", ");

            if (items.isEmpty()) items = "keine";

            event.getChannel().sendMessage(new EmbedBuilder()
                    .setAuthor(event.getMessage().getMentionedUsers().get(0).getName(), null, event.getMessage().getMentionedUsers().get(0).getAvatarUrl())
                    .setColor(Color.green)
                    .setDescription("Kontoinformation")
                    .addField("Deine aktuellen XP", String.valueOf(Users.getXp(userId)), false)
                    .addField("Deine aktuellen Münzen", String.valueOf(Users.getCoins(userId)), false)
                    .addField("Deine aktuelle Stufe", LevelUtils.getRoleByLevel(Users.getRole(event.getMessage().getMentionedUsers().get(0).getId())).getName(), false)
                    .addField("Deine Items", items, false)
                    .build()).queue();
        }
    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        event.getMessage().getChannel().sendMessage("`Zeigt die Informationen zu  einem User. Syntax: !info (optional <@Member>)`").queue();
    }
}
