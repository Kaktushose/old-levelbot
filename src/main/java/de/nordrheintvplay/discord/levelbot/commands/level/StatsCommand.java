package de.nordrheintvplay.discord.levelbot.commands.level;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.json.User;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.LevelUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class StatsCommand implements Commands {
    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (event.getMessage().getMentionedMembers().size() == 0) {

            User user = Users.getUser(event.getAuthor().getIdLong());

            String items = "";

            
            
            if (user.hasBooster()) {
                items = "Münzenbooster ";
            }

            if (user.getPremium()) {
                items = items + "Premium-Rang ";
            }

            if (user.getUltra()) {
                items = items + "ULTRA-Rang";
            }

            items = items.trim().replaceAll(" ", ", ");

            if (items.isEmpty()) items = "keine";

            event.getChannel().sendMessage(new EmbedBuilder()
                    .setAuthor(event.getAuthor().getName(), null, event.getAuthor().getAvatarUrl())
                    .setDescription("Kontoinformation")
                    .setColor(Color.green)
                    .addField("Deine aktuellen XP", String.valueOf(user.getXp()), false)
                    .addField("Deine aktuellen Münzen", String.valueOf(user.getCoins()), false)
                    .addField("Deine aktuelle Stufe", LevelUtils.getRoleByLevel(user.getRole()).getName(), false)
                    .addField("Deine Items", items, false)
                    .build()).queue();
        } else {

            User user = Users.getUser(event.getMessage().getMentionedUsers().get(0).getIdLong());

            String items = "";

            if (user.hasBooster()) {
                items = "Münzenbooster ";
            }

            if (user.getPremium()) {
                items = items + "Premium-Rang ";
            }

            if (user.getUltra()) {
                items = items + "ULTRA-Rang";
            }

            items = items.trim().replaceAll(" ", ", ");

            if (items.isEmpty()) items = "keine";

            event.getChannel().sendMessage(new EmbedBuilder()
                    .setAuthor(event.getMessage().getMentionedUsers().get(0).getName(), null, event.getMessage().getMentionedUsers().get(0).getAvatarUrl())
                    .setColor(Color.green)
                    .setDescription("Kontoinformation")
                    .addField("Deine aktuellen XP", String.valueOf(user.getXp()), false)
                    .addField("Deine aktuellen Münzen", String.valueOf(user.getCoins()), false)
                    .addField("Deine aktuelle Stufe", LevelUtils.getRoleByLevel(user.getRole()).getName(), false)
                    .addField("Deine Items", items, false)
                    .build()).queue();

        }
    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        event.getMessage().getChannel().sendMessage("`Zeigt die Informationen zu  einem User. Syntax: !info (optional <@Member>)`").queue();
    }
}
