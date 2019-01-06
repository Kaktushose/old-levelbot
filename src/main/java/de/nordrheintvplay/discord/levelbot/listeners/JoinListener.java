package de.nordrheintvplay.discord.levelbot.listeners;

import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.LevelUtils;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class JoinListener extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        LevelUtils.addRole(event.getMember(), LevelUtils.Roles.WELCOME);
        Users.addMember(event.getUser().getId());
    }

}
