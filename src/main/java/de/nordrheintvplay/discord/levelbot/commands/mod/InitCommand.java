package de.nordrheintvplay.discord.levelbot.commands.mod;

import de.nordrheintvplay.discord.levelbot.commands.framework.Commands;
import de.nordrheintvplay.discord.levelbot.json.Users;
import de.nordrheintvplay.discord.levelbot.utils.Const;
import de.nordrheintvplay.discord.levelbot.utils.Permissions;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.TimeUnit;


public class InitCommand extends ListenerAdapter implements Commands {

    private static long msgId;

    @Override
    public void execute(GuildMessageReceivedEvent event, String[] args) {

        if (Permissions.hasRole(event.getMember(), Permissions.Perms.ADMINISTRATOR)) {

            event.getMessage().getChannel().sendMessage("`Bist du sicher, was du da machst? Dies wird die gesamte Datenbank neu aufsetzten! Dadurch gehen alle Daten verloren!`").queue(message -> {
                message.addReaction("\uD83D\uDC4D").queue();
                message.addReaction("\uD83D\uDC4E").queue();
                msgId = message.getIdLong();
            });

        }
    }

    @Override
    public void help(GuildMessageReceivedEvent event, String[] args) {
        if (!Permissions.hasOneOfRoles(event.getMember(), Permissions.Perms.ADMINISTRATOR, Permissions.Perms.MODERATOR)) {
            return;
        }
        event.getMessage().getChannel().sendMessage("`Fügt alle Member neu zur Datenbank hinzu. WARNUNG! LÖSCHT DIE ALTEN WERTE!`").queue();
    }

    @Override
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void onMessageReactionAdd(MessageReactionAddEvent event) {

        if (event.getMessageIdLong() != msgId) {
            return;
        }

        if (event.getMember().getUser().getIdLong() == event.getJDA().getSelfUser().getIdLong()) {
            return;
        }

        if (!event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
            return;
        }

        if (event.getReactionEmote().getName().equals("\uD83D\uDC4D")) {

            File file = new File(Const.JSON_PATH);

            try {
                if (file.exists()) {
                    file.delete();
                    file.createNewFile();
                } else {
                    file.createNewFile();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try (final FileWriter fw = new FileWriter(file)) {

                fw.write("{}");
                fw.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }


            for (Member member : event.getGuild().getMembers()) {

                if (member.getUser().isBot()) continue;

                Users.addUser(member.getUser().getIdLong());

            }

            event.getTextChannel().sendMessage("`JSON neu initalisiert`").queue();


            event.getChannel().getHistory().retrievePast(3).queue(messages -> {

                for (Message m : messages) {
                    event.getChannel().deleteMessageById(m.getId()).queueAfter(3, TimeUnit.SECONDS);
                }

            });


        }

        if (event.getReactionEmote().getName().equals("\uD83D\uDC4E")) {

            event.getChannel().getHistory().retrievePast(2).queue(messages -> {

                for (Message m : messages) {
                    event.getChannel().deleteMessageById(m.getId()).queue();
                }

            });

        }

    }

}
