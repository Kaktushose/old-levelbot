package de.nordrheintvplay.discord.levelbot.core;

import de.nordrheintvplay.discord.levelbot.json.Prices;
import de.nordrheintvplay.discord.levelbot.json.User;
import de.nordrheintvplay.discord.levelbot.json.Users;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

import java.util.Date;

public class BoosterCheck {

    public static void check(Guild guild) {

        Date date = new Date();

        for (Member m : guild.getMembers()) {

            User user = Users.getUser(m.getUser().getIdLong());

            if (!user.hasBooster()) {
                System.out.println("noBooster for " +  m.getEffectiveName());
                continue;
            }
            if (date.getTime() - user.getBoosterTime() >= 4838400000L) {
                System.out.println("Time is okay" + m.getEffectiveName());
                continue;
            }

            user.setBooster( false);
            user.setBoosterTime(0);
            user.save();
            m.getUser().openPrivateChannel().queue(privateChannel -> privateChannel.sendMessage("Dein Münzenbooster ist abgelaufen! Für " + Prices.getPrice("booster") + " Münzen kannst du dir mit dem Befehl `!kaufen münzenbooster` einen neuen kaufen").queue());

        }

    }

}




