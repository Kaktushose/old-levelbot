package de.nordrheintvplay.discord.levelbot.json;

import de.nordrheintvplay.discord.levelbot.utils.Const;
import de.nordrheintvplay.discord.levelbot.utils.LevelUtils;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;


public class Users {

    private static String content;
    private static JSONObject json;

    static {
        load();
    }

    public static void addMember(String id) {
        JSONObject member = new JSONObject();
        member.put("xp", 0)
                .put("coins", 0)
                .put("role", 1)
                .put("lastxp", 0)
                .put("booster", false)
                .put("premium", false)
                .put("ultra", false)
                .put("boostertime", 0);
        json.put(id, member);
        save();
    }


    public static void update() {

        for (Iterator<String> it = json.keys(); it.hasNext();) {

            String key = it.next();

            JSONObject member = new JSONObject(json.get(key).toString());
            member.put("boostertime", 0);
            json.put(key, member);
        }

        save();

    }

    public static void check(Guild guild) {

        for (Iterator<String> it = json.keys(); it.hasNext();) {

            String key = it.next();

            if (guild.getMembers().stream().noneMatch(member -> member.getUser().getId().equals(key))) {
                removeMember(key);
            }

        }

        for (Member member : guild.getMembers()) {

            if (!json.has(member.getUser().getId())) {
                addMember(member.getUser().getId());
            }

            if (getRole(member.getUser().getId()) == 1) {
                LevelUtils.addRole(member, LevelUtils.Roles.WELCOME);
            }

        }

    }

    public static void removeMember(String id) {
        json.remove(id);
        save();
    }

    public static long getLastXp(String id) {
        return Long.valueOf(new JSONObject(json.get(id).toString()).get("lastxp").toString());
    }

    public static int getXp(String id) {
        return (int) new JSONObject(json.get(id).toString()).get("xp");
    }

    public static int getCoins(String id) {
        return (int) new JSONObject(json.get(id).toString()).get("coins");
    }

    public static int getRole(String id) {
        return (int) new JSONObject(json.get(id).toString()).get("role");
    }

    public static boolean hasBooster(String id) {
        return (boolean) new JSONObject(json.get(id).toString()).get("booster");
    }

    public static boolean hasPremium(String id) {
        return (boolean) new JSONObject(json.get(id).toString()).get("premium");
    }

    public static boolean hasUltra(String id) {
        return (boolean) new JSONObject(json.get(id).toString()).get("ultra");
    }

    public static long getBoosterBuyTime(String id) {
        return (long) new JSONObject(json.get(id).toString()).get("boostertime");
    }

    public static void setLastxp(String id, long lastxp) {
        JSONObject member = new JSONObject(json.get(id).toString());
        member.put("lastxp", lastxp);
        json.put(id, member);
        save();
    }

    public static void setXp(String id, int xp) {
        JSONObject member = new JSONObject(json.get(id).toString());
        member.put("xp", xp);
        json.put(id, member);
        save();
    }

    public static void setCoins(String id, int coins) {
        JSONObject member = new JSONObject(json.get(id).toString());
        member.put("coins", coins);
        json.put(id, member);
        save();
    }

    public static void setRole(String id, int role) {
        JSONObject member = new JSONObject(json.get(id).toString());
        member.put("role", role);
        json.put(id, member);
        save();
    }

    public static void setBooster(String id, boolean value) {
        JSONObject member = new JSONObject(json.get(id).toString());
        member.put("booster", value);
        json.put(id, member);
        save();
    }

    public static void setPremium(String id, boolean value) {
        JSONObject member = new JSONObject(json.get(id).toString());
        member.put("premium", value);
        json.put(id, member);
        save();
    }

    public static void setUltra(String id, boolean value) {
        JSONObject member = new JSONObject(json.get(id).toString());
        member.put("ultra", value);
        json.put(id, member);
        save();
    }

    public static void setBoosterBuyTime(String id, long time) {
        JSONObject member = new JSONObject(json.get(id).toString());
        member.put("boostertime", time);
        json.put(id, member);
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
