package com.fireflyest.drop.listener;

import com.fireflyest.drop.data.Language;
import com.fireflyest.drop.manager.DropManager;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class DropEventListener implements Listener {

    @EventHandler
    public void onItemSpawnEvent(ItemSpawnEvent event) {
        Item item = event.getEntity();
        item.setPickupDelay(20);
        ArmorStand drop = (ArmorStand)item.getWorld().spawnEntity(item.getLocation().add(0, -item.getLocation().getY(), 0), EntityType.ARMOR_STAND);
        drop.setBasePlate(false);
        drop.setArms(true);
        drop.setVisible(false);
        drop.setGravity(false);
        drop.setCollidable(false);
        drop.setCustomName("#DROP");
        drop.setCustomNameVisible(false);
//        drop.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100, 1));
        DropManager.placeOnGround(item, drop);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event){
        if(event.isCancelled())return;
        if(!event.getEntity().getType().equals(EntityType.ARMOR_STAND))return;
        if(!"#DROP".equals(event.getEntity().getCustomName()))return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
        if(!event.getEntity().getType().equals(EntityType.ARMOR_STAND))return;
        if(!event.getDamager().getType().equals(EntityType.PLAYER))return;
        if(!"#DROP".equals(event.getEntity().getCustomName()))return;
        ArmorStand drop = (ArmorStand)event.getEntity();
        Player player = (Player)event.getDamager();
        if(!DropManager.pickUpDrop(player, drop)){
            player.sendMessage(Language.FULL_INV);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPick(PlayerArmorStandManipulateEvent event){
        if(event.isCancelled())return;
        if(!"#DROP".equals(event.getRightClicked().getCustomName()))return;
        ArmorStand drop = event.getRightClicked();
        Player player = (Player)event.getPlayer();
        if(!DropManager.pickUpDrop(player, drop)){
            player.sendMessage(Language.FULL_INV);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        if(event.isCancelled())return;
        DropManager.pickDrop(event.getPlayer());
    }

}
