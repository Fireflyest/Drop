package com.fireflyest.drop.manager;

import com.fireflyest.drop.Drop;
import com.fireflyest.drop.data.Config;
import com.fireflyest.drop.data.Language;
import com.fireflyest.drop.event.PlayerPickUpItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;

import java.util.Map;

public class DropManager {

    private DropManager(){

    }

    public static void placeOnGround(Item item, ArmorStand drop){
        new BukkitRunnable() {
            DropType type = getDropType(item.getItemStack().getType().name());
            @Override
            public void run() {
                if(item.getItemStack().getAmount() == 0){
                    cancel();
                    return;
                }
                if(item.isOnGround()){
                    if(drop.isDead()){
                        cancel();
                        return;
                    }
                    if(item.getLocation().add(0, -0.5, 0).getBlock().getType().name().contains("CHEST")){
                        cancel();
                        return;
                    }
                    switch (type){
                        case LONGER:
                            drop.setHelmet(item.getItemStack());
                            drop.setHeadPose(new EulerAngle(1.57, 0, 0));
                            drop.teleport(item.getLocation().add(0, -DropType.LONGER.height, 0));
                            item.remove();
                            dropGravity(drop, DropType.LONGER);
                            break;
                        case NORMAL:
                            drop.setItemInHand(item.getItemStack());
                            drop.setRightArmPose(new EulerAngle(0, -1.57, 0));
                            drop.teleport(item.getLocation().add(0, -DropType.NORMAL.height, 0));
                            item.remove();
                            dropGravity(drop, DropType.NORMAL);
                            break;
                        case BONE:
                            drop.setHelmet(item.getItemStack());
                            drop.setHeadPose(new EulerAngle(1.57, 0, 0));
                            drop.teleport(item.getLocation().add(0, -DropType.BONE.height, 0));
                            item.remove();
                            dropGravity(drop, DropType.BONE);
                            break;
                        case QUADRATE:
                            cancel();
                            return;
                        default:
                            break;
                    }
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Drop.getInstance(), 0L, 1L);
    }

    public static void dropGravity(ArmorStand drop, DropType type){
        new BukkitRunnable() {
            @Override
            public void run() {
                if(drop.isDead()){
                    cancel();
                    return;
                }
                if(drop.getLocation().add(0, type.height-0.5, 0).getBlock().getType().equals(Material.AIR)){
                    if(type.equals(DropType.NORMAL)){
                        drop.getWorld().dropItem(drop.getLocation().add(0, type.height, 0), drop.getItemInHand());
                    }else{
                        drop.getWorld().dropItem(drop.getLocation().add(0, type.height, 0), drop.getHelmet());
                    }
                    drop.remove();
                    cancel();
                    return;
                }
            }
        }.runTaskTimer(Drop.getInstance(), 0L, 5L);
    }

    public static void pickDrop(Player player){
        new BukkitRunnable() {
            @Override
            public void run() {
                for(Entity entity : player.getNearbyEntities(Config.PICK_RANGE, Config.PICK_RANGE, Config.PICK_RANGE)){
                    if(!entity.getType().equals(EntityType.ARMOR_STAND))continue;
                    if(!"#DROP".equals(entity.getCustomName()))continue;
                    ArmorStand drop = (ArmorStand)entity;
                    if(!pickUpDrop(player, drop)){
                        cancel();
                        return;
                    }
                }
                cancel();
                return;
            }
        }.runTask(Drop.getInstance());
    }

    public static boolean pickUpDrop(Player player, ArmorStand drop){
        ItemStack item = "AIR".equals(drop.getItemInHand().getType().name()) ? drop.getHelmet() : drop.getItemInHand();
        PlayerPickUpItemEvent event;
        int i = 0;
        if(player.getInventory().firstEmpty() == -1){//玩家背包满
            int max = item.getType().getMaxStackSize();
            for(Map.Entry<Integer, ? extends ItemStack> entry : player.getInventory().all(item.getType()).entrySet()){
                if(!entry.getValue().isSimilar(item))continue;
                if(entry.getValue().getAmount() < max){
                    i += max - entry.getValue().getAmount();//可拾取数量
                    break;
                }
            }
            if(i == 0)return false;
        }
        //判断拾取数量
        if(i == 0 || item.getAmount() <= i){//直接拾取
            drop.setCustomName("#DROP_PICK");
            event = new PlayerPickUpItemEvent(player, item, 0);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancel()) return false;
            player.getInventory().addItem(event.getItem());
        }else {
            ItemStack clone = item.clone();
            clone.setAmount(i);
            int reaming = item.getAmount()-i;
            if(reaming > 0) {
                item.setAmount(reaming);
                player.getWorld().dropItem(player.getLocation(), item);
            }
            event = new PlayerPickUpItemEvent(player, clone, reaming);
            Bukkit.getPluginManager().callEvent(event);
            if(event.isCancel()) return false;
            player.getInventory().addItem(event.getItem());
        }
        drop.remove();
        player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 2, 2);
        return true;
    }

    public static void clearDrop(){
        new BukkitRunnable() {
            int amount = 0;
            @Override
            public void run() {
                for(World world : Bukkit.getWorlds()){
                    for(LivingEntity entity : world.getLivingEntities()){
                        if(!"#DROP".equals(entity.getCustomName()) && !"#DROP_PICK".equals(entity.getCustomName()))continue;
                        if(!entity.getType().equals(EntityType.ARMOR_STAND))continue;
                        ArmorStand drop = (ArmorStand)entity;
                        amount += drop.getItemInHand().getAmount();
                        amount += drop.getHelmet().getAmount();
                        entity.remove();
                    }
                }
                Bukkit.broadcastMessage(Language.CLEAR_AMOUNT.replace("%amount%", amount+""));
                cancel();
            }
        }.runTask(Drop.getInstance());
    }

    private static DropType getDropType(String name){
        if(name.contains("BED")
                || name.contains("SKULL")
                || name.contains("HEAD")
                || name.contains("END_ROD")
                || name.contains("AIR")
        ){ return DropType.QUADRATE; }
        else if(name.contains("BOW")
                || name.contains("SWORD")
                || name.contains("HOE")
                || name.contains("AXE")
                || name.contains("ROD")
                || name.contains("SHOVEL")
                || name.contains("STICK")
                || name.contains("BAMBOO")
        ){ return DropType.LONGER; }
        else if(name.equals("BONE")){ return DropType.BONE; }
        return DropType.NORMAL;
    }

    public enum DropType{
        NORMAL(0, 0.70),
        LONGER(1, 1.60),
        QUADRATE(2, 0.0),
        BONE(3, 1.07);

        private int id;
        private double height;

        DropType(int id, double height) {
            this.id = id;
            this.height = height;
        }
    }

}
