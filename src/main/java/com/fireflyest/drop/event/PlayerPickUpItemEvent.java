package com.fireflyest.drop.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPickUpItemEvent extends PlayerEvent {

    private static final HandlerList HANDLERS = new HandlerList();

    private static ItemStack item;

    private static int reaming;

    private static boolean cancel;

    public PlayerPickUpItemEvent(Player player, ItemStack item, int reaming) {
        super(player);
        this.item = item;
        this.reaming = reaming;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public ItemStack getItem(){
        return item;
    }

    public void setItem(ItemStack item){
        this.item = item;
    }

    public int getReaming(){
        return reaming;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }

}
