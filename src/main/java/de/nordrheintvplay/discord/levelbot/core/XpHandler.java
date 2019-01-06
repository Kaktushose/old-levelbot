package de.nordrheintvplay.discord.levelbot.core;

import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.Const;
import de.nordrheintvplay.discord.levelbot.utils.LevelUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

public class XpHandler extends ListenerAdapter {

    public static void check(GuildMessageReceivedEvent event, String userId) {

        int xp = Users.getXp(userId);
        int level = Users.getRole(userId);
        boolean update = false;

        switch (LevelUtils.getLevelByXp(xp)) {

            case 1: {
                if (Users.getRole(userId) == 1) return;
                Users.setRole(userId, 1);
                update = true;
                break;
            }

            case 2: {
                if (Users.getRole(userId) == 2) return;
                Users.setRole(userId, 2);
                Users.setCoins(userId, Users.getCoins(userId) + 50);
                update = true;
                break;
            }

            case 3: {
                if (Users.getRole(userId) == 3) return;
                Users.setRole(userId, 3);
                Users.setCoins(userId, Users.getCoins(userId) + 100);
                update = true;
                break;
            }

            case 4: {
                if (Users.getRole(userId) == 4) return;
                Users.setRole(userId, 4);
                Users.setCoins(userId, Users.getCoins(userId) + 200);
                update = true;
                break;
            }

            case 5: {
                if (Users.getRole(userId) == 5) return;
                Users.setRole(userId, 5);
                Users.setCoins(userId, Users.getCoins(userId) + 500);
                update = true;
                break;
            }

            case 6: {
                if (Users.getRole(userId) == 6) return;
                Users.setRole(userId, 6);
                Users.setCoins(userId, Users.getCoins(userId) + 800);
                update = true;
                break;
            }

            case 7: {
                if (Users.getRole(userId) == 7) return;
                Users.setRole(userId, 7);
                Users.setCoins(userId, Users.getCoins(userId) + 1000);
                update = true;
                break;
            }

        }

        if (update) {

            Member m = event.getGuild().getMemberById(userId);

            LevelUtils.removeRoleByLevel(level, m);
            LevelUtils.addRoleByLevel(Users.getRole(userId), m);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Level Up!")
                    .setDescription("Glückwunsch " + event.getMember().getAsMention())
                    .setColor(Color.GREEN)
                    .addField(
                            "Du bist einen Rang aufgestiegen", "dein neuer Rang ist `" +
                                    Objects.requireNonNull(LevelUtils.getRoleByLevel(Users.getRole(userId))).getName() + "`",
                            false);

            event.getGuild().getTextChannelById(527792445874765835L).sendMessage(event.getMember().getAsMention()).queue();
            event.getGuild().getTextChannelById(527792445874765835L).sendMessage(eb.build()).queue();


        }

    }


    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Date date = new Date();

        String userId = event.getAuthor().getId();

        if (event.getChannel().getIdLong() == 408630688523485184L) { //Rollen-Reports-Tickets
            return;
        }

        if (event.getChannel().getIdLong() == 386279631189311500L) { //Support-Chat
            return;
        }

        if (event.getAuthor().isBot()) {
            return;
        }

        if (Arrays.stream(Const.PREFIX_LIST).anyMatch(event.getMessage().getContentRaw()::startsWith)) {
            return;
        }

        if (date.getTime() - Users.getLastXp(userId) < 600000L) {
            return;
        }

        boolean hasImage = false;
        for (Message.Attachment a : event.getMessage().getAttachments()) {
            if (a.isImage()) {
                hasImage = true;
            }
        }

        if (event.getMessage().getContentRaw().length() >= 10 || hasImage) {

            Users.setXp(userId, Users.getXp(userId) + 5);

            if (Users.hasBooster(userId)) {
                Users.setCoins(userId, Users.getCoins(userId) + LevelUtils.getCoinsByLevel(Users.getRole(userId)) + 2);
            } else {
                Users.setCoins(userId, Users.getCoins(userId) + LevelUtils.getCoinsByLevel(Users.getRole(userId)));
            }

            Users.setLastxp(userId, date.getTime());
            check(event, userId);

        }
    }
}
