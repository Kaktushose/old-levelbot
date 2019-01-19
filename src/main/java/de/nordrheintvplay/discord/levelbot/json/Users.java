package de.nordrheintvplay.discord.levelbot.json;

import de.nordrheintvplay.discord.levelbot.utils.Const;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Users {

    private static String content;
    private static JSONObject json;

    static {
        load();
    }

    public static void addKey() {

        for (Iterator<String> it = json.keys(); it.hasNext(); ) {

            String key = it.next();

            JSONObject member = new JSONObject(json.get(key).toString());
            member.put("", 0);
            json.put(key, member);
        }

        save();

    }


    public static void update(Guild guild) {

        for (Member member : guild.getMembers()) {

            Iterator<String> keys = json.keys();
            boolean registered = false;

            while (keys.hasNext()) {
                String key = keys.next();
                if (key.equals(member.getUser().getId())) {
                    registered = true;
                }
            }

            if (!registered) {
                Users.addUser(member.getUser().getIdLong());
            }

        }

        Iterator<String> keys = json.keys();
        List<String> valuesToRemove = new ArrayList<>();

        while (keys.hasNext()) {
            String key = keys.next();
            boolean onServer = false;
            for (Member member : guild.getMembers()) {
                if (key.equals(member.getUser().getId())) {
                    onServer = true;
                }
            }

            if (!onServer) {
                valuesToRemove.add(key);
            }
        }

        for (String key : valuesToRemove) {
            json.remove(key);
        }
    }

    public static void addUser(long id) {
        JSONObject user = new JSONObject();
        user.put("xp", 0)
                .put("coins", 0)
                .put("role", 1)
                .put("lastxp", 0)
                .put("booster", false)
                .put("premium", false)
                .put("ultra", false)
                .put("boostertime", 0);
        json.put(String.valueOf(id), user);
        save();
    }

    public static User getUser(long id) {
        JSONObject member = new JSONObject(json.get(String.valueOf(id)).toString());
        return new User(member.getInt("role"),
                member.getInt("xp"),
                member.getInt("coins"),
                id,
                member.getLong("lastxp"),
                member.getLong("boostertime"),
                member.getBoolean("premium"),
                member.getBoolean("ultra"),
                member.getBoolean("booster")
        );
    }

    static void updateUser(User user) {
        JSONObject member = new JSONObject();
        member.put("xp", user.getXp())
                .put("coins", user.getCoins())
                .put("role", user.getRole())
                .put("lastxp", user.getLastXp())
                .put("booster", user.getBooster())
                .put("premium", user.getPremium())
                .put("ultra", user.getUltra())
                .put("boostertime", user.getBoosterTime());
        json.put(String.valueOf(user.getId()), member);
        save();
    }

    static void removeUser(User user) {
        json.remove(String.valueOf(user.getId()));
        save();
    }

    private static void save() {

        File file = new File(Paths.get(Const.JSON_PATH).toString());

        try (final FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void load() {

        try {
            content = new String(Files.readAllBytes(Paths.get(Const.JSON_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        json = new JSONObject(content);

    }


}
