package com.warring.library.menus.api;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class MenuAPI implements Listener {
    private static MenuAPI instance;

    public static MenuAPI getInstance() {
        if (MenuAPI.instance == null) {
            synchronized (MenuAPI.class) {
                if (MenuAPI.instance == null) {
                    MenuAPI.instance = new MenuAPI();
                }
            }
        }
        return MenuAPI.instance;
    }

    public Menu createMenu(String title, int rows) {
        return new Menu(title, rows);
    }

    public Menu cloneMenu(Menu menu) throws CloneNotSupportedException {
        return menu.clone();
    }

    public void removeMenu(Menu menu) {
        for (HumanEntity viewer : menu.getInventory().getViewers()) {
            if (viewer instanceof Player) {
                menu.closeMenu((Player) viewer);
            } else {
                viewer.closeInventory();
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onMenuItemClicked(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();
        if (inventory.getHolder() instanceof Menu) {
            event.setCancelled(true);
            Menu menu = (Menu) inventory.getHolder();
            if (!(event.getWhoClicked() instanceof Player)) {
                return;
            }
            Player player = (Player) event.getWhoClicked();
            if (event.getSlotType() == InventoryType.SlotType.OUTSIDE) {
                if (menu.exitOnClickOutside()) {
                    menu.closeMenu(player);
                }
            } else {
                int index = event.getRawSlot();
                if (index < inventory.getSize()) {
                    if (event.getAction() != InventoryAction.NOTHING) {
                        menu.selectMenuItem(player, index, InventoryClickType.fromInventoryAction(event.getAction()));
                    }
                } else {
                    if (menu.exitOnClickOutside()) {
                        menu.closeMenu(player);
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMenuClosed(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Inventory inventory = event.getInventory();
            if (inventory.getHolder() instanceof Menu) {
                Menu menu = (Menu) inventory.getHolder();
                MenuCloseBehaviour menuCloseBehaviour = menu.getMenuCloseBehaviour();
                if (menuCloseBehaviour != null) {
                    menuCloseBehaviour.onClose((Player) event.getPlayer(), menu, menu.bypassMenuCloseBehaviour());
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerLogoutCloseMenu(PlayerQuitEvent event) {
        if (event.getPlayer().getOpenInventory() == null || !(event.getPlayer().getOpenInventory().getTopInventory().getHolder() instanceof Menu)) {
            return;
        }
        Menu menu = (Menu) event.getPlayer().getOpenInventory().getTopInventory().getHolder();
        menu.setBypassMenuCloseBehaviour(true);
        menu.setMenuCloseBehaviour(null);
        event.getPlayer().closeInventory();
    }

    public interface MenuCloseBehaviour {
        void onClose(Player p, Menu menu, boolean bypass);
    }
}
