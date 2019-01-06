package de.nordrheintvplay.discord.levelbot.commands.shop;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.json.Prices;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class ShopHelpCommand implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (args.length < 1) {
            help(event, args);
            return;
        }

        if (args[0].equals("münzenbooster")) {
            BuyBooster.execute(event);
            return;
        }

        if (args[0].equals("rolle")) {

            if (args[1].equals("premium")) {
                BuyPremium.execute(event);
                return;
            }

            if (args[1].equals("ultra")) {
                BuyUltra.execute(event);
            }

        }

    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.GREEN)
                .setTitle("Shop")
                .setDescription("Hier siehst du alles, was du kaufen kannst")
                .addField("Premium-Rang", "`Kosten:" + Prices.getPrice("premium") + " Münzen`\nermöglicht den Zugang zu einigen Zusatz-Rechten, Channels sowie exklusiven Gewinnspielen", false)
                .addField("ULTRA-Rang", "`Kosten: " + Prices.getPrice("ultra") + "  Münzen + Premium-Rang`\nein hochwertiger Premium-Rang mit zusätzlichen Boni" , false)
                .addField("Münzen-Booster", "`Kosten: " + Prices.getPrice("booster") + " Münzen`\nerhöht die Anzahl der Münzen um 2 je Nachricht, hält für 8 Wochen", false)
                .addField("Befehle", "`!kaufen münzenbooster`\n`!kaufen rolle premium`\n`!kaufen rolle ultra`", false);
        event.getChannel().sendMessage(eb.build()).queue();

    }
}
