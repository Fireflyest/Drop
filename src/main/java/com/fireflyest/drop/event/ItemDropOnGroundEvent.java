package com.fireflyest.drop.event;

import com.fireflyest.drop.manager.DropType;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityEvent;

public class ItemDropOnGroundEvent extends EntityEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private ArmorStand drop;

    private Item item;

    private DropType type = DropType.NORMAL;

    private boolean cancel;

    public ItemDropOnGroundEvent(Item item, ArmorStand drop) {
        super(item);
        this.item = item;
        this.drop = drop;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public HandlerList getHandlerList(){
        return HANDLERS;
    }

    public ArmorStand getDrop() {
        return drop;
    }

    public void setDrop(ArmorStand drop) {
        this.drop = drop;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public DropType getType() {
        return type;
    }

    public void setType(DropType type) {
        this.type = type;
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
