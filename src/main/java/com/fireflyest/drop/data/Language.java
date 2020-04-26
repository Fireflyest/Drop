package com.fireflyest.drop.data;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Language {

    public FileConfiguration getLang() {
        return lang;
    }

    private FileConfiguration lang;

    public static List<String>HELP = new ArrayList<>();
    public static String VERSION;
    public static String TITLE;
    public static String PLAYER_COMMAND;
    public static String RELOADING;
    public static String RELOADED;
    public static String FULL_INV;
    public static String CLEAR_TICK;
    public static String CLEARING;
    public static String CLEAR_AMOUNT;

    public Language(FileConfiguration lang){
        this.lang = lang;
        this.setUp();
    }

    private void setUp(){
        VERSION = lang.getString("Version");
        HELP = lang.getStringList("Help");
        TITLE = lang.getString("Title").replace("&", "§");
        PLAYER_COMMAND = TITLE + lang.getString("PlayerCommand").replace("&", "§");
        RELOADING = TITLE + lang.getString("Reloading").replace("&", "§");
        RELOADED = TITLE + lang.getString("Reloaded").replace("&", "§");
        FULL_INV = TITLE + lang.getString("FullInv").replace("&", "§");
        CLEAR_TICK = TITLE + lang.getString("ClearTick").replace("&", "§");
        CLEARING = TITLE + lang.getString("Clearing").replace("&", "§");
        CLEAR_AMOUNT = TITLE + lang.getString("ClearAmount").replace("&", "§");
    }

}
