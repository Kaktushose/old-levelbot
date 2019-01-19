package de.nordrheintvplay.discord.levelbot.core;

import de.nordrheintvplay.discord.levelbot.json.User;
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

    public static void check(GuildMessageReceivedEvent event, User user) {

        int xp = user.getXp();
        int level = user.getRole();
        boolean update = false;

        switch (LevelUtils.getLevelByXp(xp)) {

            case 1: {
                if (user.getRole() == 1) return;
                user.setRole(1);
                update = true;
                break;
            }

            case 2: {
                if (user.getRole() == 2) return;
                user.setRole(2);
                user.setCoins(user.getCoins() + 50);
                update = true;
                break;
            }

            case 3: {
                if (user.getRole() == 3) return;
                user.setRole(3);
                user.setCoins(user.getCoins() + 100);
                update = true;
                break;
            }

            case 4: {
                if (user.getRole() == 4) return;
                user.setRole(4);
                user.setCoins(user.getCoins() + 200);
                update = true;
                break;
            }

            case 5: {
                if (user.getRole() == 5) return;
                user.setRole(5);
                user.setCoins(user.getCoins() + 500);
                update = true;
                break;
            }

            case 6: {
                if (user.getRole() == 6) return;
                user.setRole(6);
                user.setCoins(user.getCoins() + 800);
                update = true;
                break;
            }

            case 7: {
                if (user.getRole() == 7) return;
                user.setRole(7);
                user.setCoins(user.getCoins() + 1000);
                update = true;
                break;
            }

        }

        user.save();

        if (update) {

            Member m = event.getGuild().getMemberById(user.getId());

            LevelUtils.removeRoleByLevel(level, m);
            LevelUtils.addRoleByLevel(user.getRole(), m);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setTitle("Level Up!")
                    .setDescription("Glückwunsch " + event.getMember().getAsMention())
                    .setColor(Color.GREEN)
                    .addField(
                            "Du bist einen Rang aufgestiegen", "dein neuer Rang ist `" +
                                    Objects.requireNonNull(LevelUtils.getRoleByLevel(user.getRole())).getName() + "`",
                            false);

            event.getGuild().getTextChannelById(527792445874765835L).sendMessage(event.getMember().getAsMention()).queue();
            event.getGuild().getTextChannelById(527792445874765835L).sendMessage(eb.build()).queue();


        }

    }


    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        Date date = new Date();

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

        User user = Users.getUser(event.getAuthor().getIdLong());

        if (date.getTime() - user.getLastXp() < 600000L) {
            return;
        }

        boolean hasImage = false;
        for (Message.Attachment a : event.getMessage().getAttachments()) {
            if (a.isImage()) {
                hasImage = true;
            }
        }

        if (event.getMessage().getContentRaw().length() >= 10 || hasImage) {

            user.setXp(user.getXp() + 3);

            if (user.getBooster()) {
                user.setCoins(user.getCoins() + LevelUtils.getCoinsByLevel(user.getRole()+ 2));
            } else {
                user.setCoins(user.getCoins() + LevelUtils.getCoinsByLevel(user.getRole()));
            }

            user.setLastxp(date.getTime());
            user.save();
            check(event, user);
            

        }
    }
}
