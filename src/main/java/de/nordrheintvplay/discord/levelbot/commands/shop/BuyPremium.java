package de.nordrheintvplay.discord.levelbot.commands.shop;

import de.nordrheintvplay.discord.levelbot.json.Prices;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.LevelUtils;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BuyPremium extends ListenerAdapter {

    private static long msgId;
    private static String userId;
    public static void execute(GuildMessageReceivedEvent event) {

        userId = event.getAuthor().getId();

        if (Users.hasPremium(userId)) {
            event.getChannel().sendMessage("`Du besitzt bereits die Premium-Rolle!`").queue();
            return;
        }

        if (Users.getCoins(userId) < Prices.getPrice("premium")) {
            event.getChannel().sendMessage("`Du hast nicht genug Münzen, um dir die Premium-Rolle zu leisten!`").queue();
            return;
        }

        event.getChannel().sendMessage("`Möchtest du die Premium-Rolle für " + Prices.getPrice("premium") + " Münzen kaufen?`").queue(msg -> {
            msg.addReaction("\uD83D\uDC4D").queue();
            msg.addReaction("\uD83D\uDC4E").queue();
            msgId = msg.getIdLong();
        });


    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent event) {

        if (event.getMember().getUser().isBot()) {
            return;
        }


        if (event.getMessageIdLong() != msgId) {
            return;
        }

        if (!event.getMember().getUser().getId().equals(userId)) {
            return;
        }

        if (event.getReactionEmote().getName().equals("\uD83D\uDC4E")) {
            event.getChannel().getHistory().retrievePast(2).queue(messages -> {

                for (Message m : messages) {
                    event.getChannel().deleteMessageById(m.getId()).queue();
                }

            });
            return;
        }

        if (event.getReactionEmote().getName().equals("\uD83D\uDC4D")) {

            Users.setPremium(userId, true);
            Users.setCoins(userId, Users.getCoins(userId) - Prices.getPrice("premium"));
            event.getChannel().deleteMessageById(event.getMessageIdLong()).queue();
            event.getChannel().sendMessage("`Du hast die Premium-Rolle gekauft!`").queue();
            LevelUtils.addRole(event.getMember(), LevelUtils.Roles.PREMIUM);


        }

    }
}
