package com.warring.library.events;

import com.warring.library.menus.api.Menu;
import com.warring.library.utils.Utils;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.function.Predicate;
@Getter
public class Filters {

    private static Predicate<? extends Cancellable> cancelled = (cancellable -> !cancellable.isCancelled());;

    private static Predicate<? extends PlayerEvent> ignoreOp = ((event) -> !event.getPlayer().isOp());

    public static Predicate<? extends Cancellable> getCancelled() {
        return cancelled;
    }

    public static Predicate<? extends PlayerEvent> getIgnoreOp() {
        return ignoreOp;
    }

}
