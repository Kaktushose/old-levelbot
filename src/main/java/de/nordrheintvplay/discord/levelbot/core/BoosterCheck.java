package de.nordrheintvplay.discord.levelbot.core;

import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.json.Prices;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

import java.util.Date;

public class BoosterCheck {

    public static void check(Guild guild) {

        Date date = new Date();

        for (Member m : guild.getMembers()) {

            if (!Users.hasBooster(m.getUser().getId())) {
                return;
            }
            if (date.getTime() - Users.getBoosterBuyTime(m.getUser().getId()) < 4838400000L) {
                return;
            }

            Users.setBooster(m.getUser().getId(), false);
            Users.setBoosterBuyTime(m.getUser().getId(), 0);

            m.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Dein Münzenbooster ist abgelaufen! Für " + Prices.getPrice("booster") + " Münzen kannst du dir mit dem Befehl `!kaufen münzenbooster` einen neuen kaufen").queue());

        }


    }


}




