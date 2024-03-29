package de.nordrheintvplay.discord.levelbot.core;

import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.Const;
import net.dv8tion.jda.core.entities.Member;

import java.util.Timer;
import java.util.TimerTask;

import static de.nordrheintvplay.discord.levelbot.core.LevelBot.getJda;

public class XpTimer {

    public static void run() {

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (Member member : getJda().getGuildById(Const.SERVER_ID).getMembers()) {

                    String userId = member.getUser().getId();

                    if (Users.getRole(userId) != 7) {
                        return;
                    }

                    Users.setCoins(userId, Users.getCoins(userId) + 30);

                }
            }
        }, 0, 1000 * 60 * 1440);


    }
}
