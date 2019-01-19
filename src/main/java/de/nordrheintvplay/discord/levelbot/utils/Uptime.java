package de.nordrheintvplay.discord.levelbot.utils;

import java.util.Date;

public class Uptime {

    private static Date startTime;

    public static void start() {
        Uptime.startTime = new Date();
    }

    public static String getUptime() {
        return getTimeDiff(new Date(), startTime);
    }

    private static String getTimeDiff(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        return parseTimeNumbs(diffHours) + " h, " + parseTimeNumbs(diffMinutes) + " min";
    }

    private static String parseTimeNumbs(long time) {
        String timeString = time + "";
        if (timeString.length() < 2)
            timeString = "0" + time;
        return timeString;
    }

}
