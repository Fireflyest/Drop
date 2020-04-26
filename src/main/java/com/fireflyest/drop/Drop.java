package com.fireflyest.drop;

import com.fireflyest.drop.command.DropCommand;
import com.fireflyest.drop.command.DropTab;
import com.fireflyest.drop.data.Language;
import com.fireflyest.drop.data.YamlManager;
import com.fireflyest.drop.listener.DropEventListener;
import com.fireflyest.drop.manager.DropManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;
import org.bukkit.scheduler.BukkitRunnable;

@Plugin(name="Drop", version="1.0.2")
@Author(value = "Fireflyest")
@Command(name = "drop", usage = "/drop <reload|help|default>",
        permission = "drop.use", permissionMessage = "§6▶§f你没有使用该指令的权限§3 drop.use")
@Command(name = "d")
@ApiVersion(value = ApiVersion.Target.v1_14)
public class Drop extends JavaPlugin {

    private static JavaPlugin plugin;

    public static JavaPlugin getInstance(){ return plugin; }

    @Override
    public void onEnable() {
        plugin = this;
        YamlManager.iniYamlManager();
        this.getServer().getPluginManager().registerEvents( new DropEventListener(), this );
        this.getCommand("drop").setExecutor(new DropCommand());
        this.getCommand("drop").setTabCompleter(new DropTab());
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if(i == 30){
                    i = 0;
                    Bukkit.broadcastMessage(Language.CLEARING);
                    DropManager.clearDrop();
                }else if(i == 29){
                    Bukkit.broadcastMessage(Language.CLEAR_TICK);
                }
                i++;
            }
        }.runTaskTimer(this, 0L, 600L);
    }

    @Override
    public void onDisable() {

    }

}
