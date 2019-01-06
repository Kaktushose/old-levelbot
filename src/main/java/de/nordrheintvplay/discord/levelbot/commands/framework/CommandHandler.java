package de.nordrheintvplay.discord.levelbot.commands.framework;

import de.nordrheintvplay.discord.levelbot.utils.Const;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler extends ListenerAdapter {

    private static Map<String, Commands> commandsMap = new HashMap<>();

    private void handle(GuildMessageReceivedEvent event, String msg) {

        Command command = CommandParser.parse(msg);


        if (!commandsMap.containsKey(command.getInvoke())) {
            return;
        }

        if (command.getInvoke().equals("hilfe")) {

            if (command.getArgs().length < 1) {
                commandsMap.get(command.getInvoke()).help(event, command.getArgs());

            } else {

                if (!commandsMap.containsKey(command.getArgs()[0])) {
                    return;
                }

                commandsMap.get(command.getArgs()[0]).help(event, command.getArgs());

            }

        } else {

            commandsMap.get(command.getInvoke()).execute(event, command.getArgs());

        }

    }

    public CommandHandler registerCommand(String invoke, Commands command) {

        commandsMap.put(invoke, command);

        return this;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        if (event.getAuthor().getIdLong() != event.getJDA().getSelfUser().getIdLong() && event.getMessage().getContentRaw().startsWith(Const.PREFIX)) {
            handle(event, event.getMessage().getContentRaw());
        }

    }
}
