package com.fireflyest.drop.data;

import com.fireflyest.drop.Drop;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Fireflyest
 * Yaml数据管理
 */
public class YamlManager {

    private static JavaPlugin plugin;
    private static Map<String, FileConfiguration> playerData;
    private static Config config;
    private static Language lang;

    private YamlManager(){
    }

    /**
     * 初始化
     */
    public static void iniYamlManager(){
        plugin = Drop.getInstance();
        playerData = new HashMap<>();
        loadConfig();
        for(Player player : Bukkit.getOnlinePlayers()){
            setup("PlayerData", player.getName());
        }
    }

    /**
     * 添加/加载一个yml文件
     * @param folder 文件夹
     * @param ymlName 不带后缀文件名
     * @return FileConfiguration
     */
    public static FileConfiguration setup(String folder, String ymlName) {
        File file = new File(plugin.getDataFolder()+"\\"+folder, ymlName+".yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                if("".equals(folder))plugin.saveResource(ymlName+".yml", true);
            } catch (IOException e) {
                e.printStackTrace();
                Bukkit.getServer().getLogger().severe(String.format("无法创建文件 %s!", ymlName+".yml"));
            }
        }

        YamlConfiguration yml = YamlConfiguration.loadConfiguration(file);
        if(!"".equals(folder))playerData.put(ymlName, yml);
        return yml;
    }

    /**
     * 保存一个玩家的数据
     * @param name 玩家游戏名
     */
    public static void savePlayerData(String name) {
        File file = new File(plugin.getDataFolder()+"\\PlayerData", name+".yml");

        try {
            playerData.get(name).save(file);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(String.format("无法保存玩家数据 %s!", name+".yml"));
        }
    }

    /**
     * 获取玩家的数据
     * @param name 玩家游戏名
     * @return FileConfiguration
     */
    public static FileConfiguration getPlayerData(String name){
        if(!playerData.containsKey(name))setup("PlayerData", name);
        return playerData.get(name);
    }

    /**
     * 获取玩家某个节点的keys
     * @param name 玩家游戏名
     * @param section 路径
     * @return Set<String>
     */
    public static Set<String> getPlayerDataKeys(String name, String section){
        if(!playerData.containsKey(name))setup("PlayerData", name);
        if(!playerData.get(name).contains(section))playerData.get(name).createSection(section);
        return playerData.get(name).getConfigurationSection(section).getKeys(false);
    }

    /**
     * 写入玩家数据
     * @param name 玩家游戏名
     * @param key 数据键值
     * @param value 数据值
     */
    public static void setPlayerData(String name, String key, Object value){
        playerData.get(name).set(key, value);
        savePlayerData(name);
    }

    /**
     * 保存配置数据
     * @param key 据键值
     * @param value 数据值
     * @throws IOException 无法保存
     */
    public static void setConfigData(String key, Object value) {
        config.setKey(key, value);
        File file = new File(plugin.getDataFolder(), "config.yml");

        try {
            config.getConfig().save(file);
        } catch (IOException e) {
            Bukkit.getServer().getLogger().severe(String.format("无法保存数据 %s!", "config.yml"));
        }
    }

    /**
     * 加载配置文件
     */
    public static void loadConfig() {
        config = new Config(setup("", "config"));
        lang = new Language(setup("", "language"));
    }

    /**
     * 更新配置文件
     */
    public static void upDateConfig(){
        plugin.saveResource("config.yml", true);
        plugin.saveResource("language.yml", true);
    }

}
