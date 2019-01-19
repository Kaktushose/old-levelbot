package de.nordrheintvplay.discord.levelbot.commands.mod;

import com.sun.management.OperatingSystemMXBean;
import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import de.nordrheintvplay.discord.levelbot.utils.Uptime;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.lang.management.ManagementFactory;

public class InfoCommand implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {
        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }

        long totalMemory = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getTotalPhysicalMemorySize() / 1024 / 1024;
        long freeMemory = ((OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean()).getFreePhysicalMemorySize() / 1024 / 1024;
        long usedMemory = totalMemory - freeMemory;

        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Informationen")
                .setColor(Color.GREEN)
                .addField("Ping", event.getJDA().getPing() + "ms", false)
                .addField("RAM-Nutzung", usedMemory + " MB von " + totalMemory + " MB" , false)
                .addField("Uptime", Uptime.getUptime(), false);

        event.getChannel().sendMessage(eb.build()).queue();


    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }
        event.getChannel().sendMessage("`Zeigt Informationen zum Bot`").queue();
    }
}
