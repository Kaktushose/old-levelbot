package de.nordrheintvplay.discord.levelbot.commands.util;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class HelpCommand implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN)
                .setTitle("Hilfe")
                .addField("Erklärung", "Dieser Bot verwaltet das Level-System auf dem nordrheintvplay Discord. Eine genaue Erklärung findest du in #ankündigungen", false)
                .addField("Commands", "Prefix: `!`", false)
                .addField("`!hilfe <Command>`", "zeigt eine genauere Erklärung für einen Command an", false)
                .addField("`!info (optional <@Member>)`", "Zeigt Informationen zu einem Benutzer an", false)
                .addField("`!credits`", "zeigt die Credits", false);

        event.getMessage().getChannel().sendMessage(eb.build()).queue();
    }
}
