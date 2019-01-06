package de.nordrheintvplay.discord.levelbot.commands.framework;

import de.nordrheintvplay.discord.levelbot.utils.Const;

import java.util.Arrays;

public class CommandParser {

    public static Command parse(String msg) {

        while (msg.contains("  ")) {
            msg = msg.replaceAll("  ", " ");
        }

        String[] args = msg.trim().replace(Const.PREFIX, "").split(" ");

        String invoke;
        invoke = args[0].toLowerCase();

        args = Arrays.copyOfRange(args, 1, args.length);

        return new Command(invoke, args);
    }


}
