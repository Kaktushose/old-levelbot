package de.nordrheintvplay.discord.levelbot.core;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public static void main(String[] args) {

        SimpleDateFormat date = new SimpleDateFormat("[dd.MM.yy HH:mm:ss]");

        System.out.println(date.format(new Date()) + "[SYSTEM] Bot wird gestartet...");
        //LevelBot.start();
        System.out.println(date.format(new Date()) + "[SYSTEM] Bot wurde gestartet");

    }

}
