package de.nordrheintvplay.discord.levelbot.json;

import de.nordrheintvplay.discord.levelbot.utils.Const;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Prices {

    private static String content;
    private static JSONObject json;


    static {
        load();
    }

    public static void setPrice(String key, int price) {
        json.put(key, price);
        save();
    }

    public static int getPrice(String key) {
        return (int) json.get(key);
    }


    private static void save() {

        File file = new File(Paths.get(Const.JSON_PRICE_PATH).toString());

        try (final FileWriter fw = new FileWriter(file)) {
            fw.write(json.toString());
            fw.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void load() {

        try {
            content = new String(Files.readAllBytes(Paths.get(Const.JSON_PRICE_PATH)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        json = new JSONObject(content);

    }

}
