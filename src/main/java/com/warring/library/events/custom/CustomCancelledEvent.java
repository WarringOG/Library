package com.warring.library.events.custom;

import org.bukkit.event.Cancellable;

public class CustomCancelledEvent extends CustomEvent implements Cancellable {

    private boolean cancelled;

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }
}
