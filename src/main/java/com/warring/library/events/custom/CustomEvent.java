package com.warring.library.events.custom;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class CustomEvent extends Event {

    private static HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return getHandlers();
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
