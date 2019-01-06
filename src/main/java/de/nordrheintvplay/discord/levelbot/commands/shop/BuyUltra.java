package de.nordrheintvplay.discord.levelbot.commands.shop;

import de.nordrheintvplay.discord.levelbot.json.Prices;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.LevelUtils;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


public class BuyUltra extends ListenerAdapter {

    private static long msgId;
    private static String userId;
    private static int price;


    public static void execute(GuildMessageReceivedEvent event) {

        price = Prices.getPrice("ultra") + Prices.getPrice("premium");
        userId = event.getAuthor().getId();

        if (Users.hasUltra(userId)) {
            event.getChannel().sendMessage("`Du besitzt bereits die ULTRA-Rolle!`").queue();
            return;
        }

        if (Users.hasPremium(userId)) {
            price = Prices.getPrice("ultra");
        }

        if (Users.getCoins(event.getAuthor().getId()) < price) {
            event.getChannel().sendMessage("`Du hast nicht genug Münzen, um dir die ULTRA-Rolle zu leisten!`").queue();
            return;
        }

        event.getChannel().sendMessage("`Möchtest du die ULTRA-Rolle für " + price + " Münzen kaufen?`").queue(msg -> {
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

            event.getChannel().deleteMessageById(event.getMessageIdLong()).queue();
            event.getChannel().sendMessage("`Du hast die ULTRA-Rolle gekauft!`").queue();

            Users.setCoins(userId, Users.getCoins(userId) - price);
            Users.setUltra(userId, true);

            LevelUtils.removeRole(event.getMember(), LevelUtils.Roles.PREMIUM);
            LevelUtils.addRole(event.getMember(), LevelUtils.Roles.ULTRA);


        }

    }

}
