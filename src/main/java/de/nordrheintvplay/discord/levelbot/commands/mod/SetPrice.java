package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.json.Prices;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public class SetPrice implements Commands {

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {


        if (args.length < 2) {
            event.getChannel().sendMessage("`Falsche Syntax! !setprice <booster|premium|ultra> <Zahl>`").queue();
            return;
        }

        if (Integer.valueOf(args[1]) < 0) {
            event.getChannel().sendMessage("`Ungültiger Preis!`").queue();
            return;
        }

        int price = Integer.parseInt(args[1]);

        switch (args[0]) {

            case "booster": {
                Prices.setPrice("booster", price);
                event.getChannel().sendMessage("`Preis für Münzenbooster auf " + price + " gesetzt`").queue();
                break;
            }

            case "premium": {
                Prices.setPrice("premium", price);
                event.getChannel().sendMessage("`Preis für Premium-Rolle auf " + price + " gesetzt`").queue();

                break;
            }

            case "ultra": {
                Prices.setPrice("premium", price);
                event.getChannel().sendMessage("`Preis für ULTRA-Rolle auf " + price + " gesetzt`").queue();
                break;
            }

            default: {
                event.getChannel().sendMessage("`Item nicht bekannt! verfügbar: booster, premium, ultra`").queue();
                break;
            }

        }

    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        if (Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            event.getChannel().sendMessage("`Setzt den Preis für die Produkte im Shop. Syntax: !setprice <booster|premium|ultra> <Zahl>`").queue();
        }
    }
}
