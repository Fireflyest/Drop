package com.fireflyest.drop.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerPickUpItemEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private ItemStack item;

    private int reaming;

    private boolean cancel;

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

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
