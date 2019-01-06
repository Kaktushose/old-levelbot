package de.nordrheintvplay.discord.levelbot.utils;

import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

import static de.nordrheintvplay.discord.levelbot.core.LevelBot.getJda;

public class LevelUtils {

    public enum Roles {

        WELCOME(getJda().getRoleById(386302304669663232L)),
        BRONZE(getJda().getRoleById(525264960545226762L)),
        SILBER(getJda().getRoleById(525264967822213130L)),
        GOLD(getJda().getRoleById(525264969919496226L)),
        PlATIN(getJda().getRoleById(525264972079693864L)),
        DIAMANT(getJda().getRoleById(525265778610798600L)),
        BLACK(getJda().getRoleById(525265803268849675L)),
        PREMIUM(getJda().getRoleById(386302591883018242L)),
        ULTRA(getJda().getRoleById(527601363790594075L));

        private final Role role;

        Roles(Role role) {
            this.role = role;
        }
    }


    private static void removeRole(Member m, Role r) {
        getJda().getGuildById(Const.SERVER_ID).getController().removeSingleRoleFromMember(m, r).queue();
    }

    private static void addRole(Member m, Role r) {
        getJda().getGuildById(Const.SERVER_ID).getController().addSingleRoleToMember(m, r).queue();
    }

    public static void addRole(Member m, Roles r) {
        addRole(m, r.role);
    }

    public static void removeRole(Member m, Roles r) {
        removeRole(m, r.role);
    }

    public static Role getRoleByLevel(int level) {

        switch (level) {
            case 1: {
                return Roles.WELCOME.role;
            }

            case 2: {
                return Roles.BRONZE.role;
            }

            case 3: {
                return Roles.SILBER.role;
            }

            case 4: {
                return Roles.GOLD.role;
            }

            case 5: {
                return Roles.PlATIN.role;
            }

            case 6: {
                return Roles.DIAMANT.role;
            }

            case 7: {
                return Roles.BLACK.role;
            }

            default: {
                return null;
            }
        }

    }

    @SuppressWarnings("all")
    public static int getLevelByXp(int xp) {

        if (xp < 100) {
            return 1;
        } else if (xp < 500) {
            return 2;
        } else if (xp < 1000) {
            return 3;
        } else if (xp < 2000) {
            return 4;
        } else if (xp < 3200) {
            return 5;
        } else if (xp < 5000) {
            return 6;
        } else if (xp >= 5000) {
            return 7;
        } else {
            return 0;
        }

    }

    public static int getCoinsByLevel(int level) {

        switch (level) {

            case 1: return 1;
            case 2: return 2;
            case 3: return 3;
            case 4: return 4;
            case 5: return 5;
            case 6: return 5;
            case 7: return 5;
            default: return 0;

        }

    }

    public static void removeRoleByLevel(int level, Member member) {
        removeRole(member, getRoleByLevel(level));
    }

    public static void addRoleByLevel(int level, Member member) {
        addRole(member, getRoleByLevel(level));
    }

}
