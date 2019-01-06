package de.nordrheintvplay.discord.levelbot.core;

import de.nordrheintvplay.discord.levelbot.commands.framework.CommandHandler;
import de.nordrheintvplay.discord.levelbot.commands.level.StatsCommand;
import de.nordrheintvplay.discord.levelbot.commands.mod.*;
import de.nordrheintvplay.discord.levelbot.commands.shop.BuyBooster;
import de.nordrheintvplay.discord.levelbot.commands.shop.BuyPremium;
import de.nordrheintvplay.discord.levelbot.commands.shop.BuyUltra;
import de.nordrheintvplay.discord.levelbot.commands.shop.ShopHelpCommand;
import de.nordrheintvplay.discord.levelbot.commands.util.CreditsCommand;
import de.nordrheintvplay.discord.levelbot.commands.util.HelpCommand;
import de.nordrheintvplay.discord.levelbot.listeners.JoinListener;
import de.nordrheintvplay.discord.levelbot.listeners.LeaveListener;
import de.nordrheintvplay.discord.levelbot.utils.Const;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

public class LevelBot {

    private static JDA jda;

    public static void start() {

        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken(Const.TOKEN)
                .setAutoReconnect(true)
                .setStatus(OnlineStatus.ONLINE)
                .setGame(Game.of(Game.GameType.WATCHING, "!hilfe für Details"))
                .addEventListener(new JoinListener(),
                        new LeaveListener(),
                        new XpHandler(),
                        new CommandHandler(),
                        new InitCommand(),
                        new BuyPremium(),
                        new BuyUltra(),
                        new BuyBooster());

        CommandHandler commandHandler = new CommandHandler();

        commandHandler.registerCommand("hilfe", new HelpCommand())
                .registerCommand("modhilfe", new ModHelp())
                .registerCommand("info", new StatsCommand())
                .registerCommand("credits", new CreditsCommand())
                .registerCommand("init", new InitCommand())
                .registerCommand("stop", new StopCommand())
                .registerCommand("setxp", new SetXp())
                .registerCommand("addxp", new AddXp())
                .registerCommand("setcoins", new SetCoins())
                .registerCommand("addcoins", new AddCoins())
                .registerCommand("setprice", new SetPrice())
                .registerCommand("removecoins", new RemoveCoins())
                .registerCommand("removexp", new RemoveXp())
                .registerCommand("kaufen", new ShopHelpCommand());


        try {
            jda = builder.buildBlocking();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }

        //Users.check(jda.getGuildById(Const.SERVER_ID));
        XpTimer.run();
        BoosterCheck.check(jda.getGuildById(Const.SERVER_ID));

    }

    public static JDA getJda() {
        return jda;
    }
}
