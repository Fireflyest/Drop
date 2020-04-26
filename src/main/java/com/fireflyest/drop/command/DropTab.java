package com.fireflyest.drop.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class DropTab implements TabCompleter {

    private static List<String> list = new ArrayList<String>();

    public DropTab(){
        list.add("reload");
        list.add("default");
        list.add("help");
        list.add("clear");
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args){
        if(command.getName().equalsIgnoreCase("drop")){
            List<String> tab = new ArrayList<String>();
            if(args.length == 1){
                for(String sub : list){
                    if(sub.contains(args[0]))tab.add(sub);
                }
            }
            return tab;
        }
        return null;
    }

}
