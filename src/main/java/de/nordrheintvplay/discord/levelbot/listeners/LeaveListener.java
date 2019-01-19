package de.nordrheintvplay.discord.levelbot.listeners;

import de.nordrheintvplay.discord.levelbot.json.Users;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class LeaveListener extends ListenerAdapter {

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        Users.getUser(event.getUser().getIdLong()).remove();
    }
}
