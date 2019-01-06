package de.nordrheintvplay.discord.levelbot.commands.util;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class CreditsCommand implements Commands {
    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        event.getChannel().sendMessage(new EmbedBuilder()
                .setTitle("ℹ Credits")
                .setDescription("Programmiert von Kaktushose \n" +
                        "Nach einer Idee von SimuPlays")
                .setColor(Color.green)
                .build()).queue();
    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        event.getMessage().getChannel().sendMessage("`Zeigt die Credits`").queue();
    }
}
