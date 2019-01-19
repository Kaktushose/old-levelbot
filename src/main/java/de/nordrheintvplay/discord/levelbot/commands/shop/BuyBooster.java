package de.nordrheintvplay.discord.levelbot.commands.shop;

import de.nordrheintvplay.discord.levelbot.json.Prices;
import de.nordrheintvplay.discord.levelbot.json.User;
import de.nordrheintvplay.discord.levelbot.json.Users;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class BuyBooster extends ListenerAdapter {

    private static long msgId, memberId;
    private static User user;

    public static void execute(GuildMessageReceivedEvent event) {

        user = Users.getUser(event.getAuthor().getIdLong());


        if (user.getBooster()) {
            event.getChannel().sendMessage("`Du besitzt bereits einen Münzen-Booster!`").queue();
            return;
        }

        if (user.getCoins() < Prices.getPrice("booster")) {
            event.getChannel().sendMessage("`Du hast nicht genug Münzen, um dir einen Münzen-Booster zu leisten!`").queue();
            return;
        }

        event.getChannel().sendMessage("`Möchtest du einen Münzen-Booster für " + Prices.getPrice("booster") + " Münzen kaufen?`").queue(msg -> {
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

        if (event.getMember().getUser().getIdLong() != memberId) {
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
            user.setBooster(true);
            user.setCoins(user.getCoins() - Prices.getPrice("booster"));
            event.getChannel().deleteMessageById(event.getMessageIdLong()).queue();
            event.getChannel().sendMessage("`Du hast einen Münzen-Booster gekauft!`").queue();
            user.save();
        }

    }
}
