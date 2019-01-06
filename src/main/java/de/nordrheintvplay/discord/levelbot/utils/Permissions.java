package de.nordrheintvplay.discord.levelbot.utils;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

import static de.nordrheintvplay.discord.levelbot.core.LevelBot.getJda;

public class Permissions {

    public static boolean hasRole(Member member, Perms role) {
        return member.getRoles().stream().anyMatch(guildRole -> guildRole.getIdLong() == role.role.getIdLong());
    }

    public static boolean hasOneOfRoles(Member member, Perms... role) {
        boolean hasOne = false;

        for (Perms p : role) {
            if (hasRole(member, p)) hasOne = true;
        }

        return hasOne;
    }

    public enum Perms {

        MODERATOR(getJda().getRoleById(386290308901502978L)),
        ADMINISTRATOR(getJda().getRoleById(386299757909377034L));

        private final Role role;

        Perms(Role role) {
            this.role = role;
        }

    }

}
