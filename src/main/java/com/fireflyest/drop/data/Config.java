package com.fireflyest.drop.data;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    public static FileConfiguration getConfig() {
        return config;
    }

    private static FileConfiguration config;

    public static String VERSION;

    public static int PERIOD;
    public static double PICK_RANGE;

    public Config(FileConfiguration config){
        this.config = config;
        this.setUp();
    }

    private void setUp(){
        VERSION = config.getString("Version");
        PERIOD = config.getInt("Period");
        PICK_RANGE = config.getDouble("PickRange");
    }

    public static void setKey(String key, Object value) {
        config.set(key, value);
    }

}
