package de.nordrheintvplay.discord.levelbot.commands.framework;

import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public interface Commands {

    void execute(GuildMessageReceivedEvent event, String[] args);

    void help(GuildMessageReceivedEvent event, String[] args);

}
