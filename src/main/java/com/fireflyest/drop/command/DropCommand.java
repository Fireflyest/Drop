package com.fireflyest.drop.command;

import com.fireflyest.drop.Drop;
import com.fireflyest.drop.data.Language;
import com.fireflyest.drop.data.YamlManager;
import com.fireflyest.drop.manager.DropManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DropCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(!cmd.getName().equalsIgnoreCase("drop")) return true;
        Player player = (sender instanceof Player)? (Player)sender : null;
        if(args.length == 0) {

        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("help")) {
                for(String msg : Language.HELP){ sender.sendMessage(msg.replace("&", "§")); }
            }else if(args[0].equalsIgnoreCase("reload")){
                sender.sendMessage(Language.RELOADING);
                YamlManager.loadConfig();
                sender.sendMessage(Language.RELOADED);
            }else if(args[0].equalsIgnoreCase("default")){
                YamlManager.upDateConfig();
                sender.sendMessage(Language.RELOADING);
                YamlManager.loadConfig();
                sender.sendMessage(Language.RELOADED);
            }else if(args[0].equalsIgnoreCase("clear")){
                Bukkit.broadcastMessage(Language.CLEAR_TICK);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.broadcastMessage(Language.CLEARING);
                        DropManager.clearDrop();
                    }
                }.runTaskLater(Drop.getInstance(), 600L);
            }else if(args[0].equalsIgnoreCase("test")){
                if(player == null) { sender.sendMessage(Language.PLAYER_COMMAND); return true; }
            }
        }else sender.sendMessage("正确用法§3" + cmd.getUsage());
        return true;
    }

}
