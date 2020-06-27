package com.warring.library.menus.api;

import com.warring.library.WarringPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MenuItem {


    private Menu menu;
    private int slot;
    private boolean clickable = true;
    private MenuItemStack itemStack;
    private ItemAction action;
    private Player player;

    public MenuItem(ItemAction action, MenuItemStack item, Player player) {
        this.action = action;
        this.itemStack = item;
        this.player = player;
    }

    void addToMenu(Menu menu) {
        this.menu = menu;
    }

    public void onClick(Player p0, InventoryClickType p1) {
        action.onClick(p0, p1, this);
    }

    void removeFromMenu(Menu menu) {
        if (this.menu == menu) {
            this.menu = null;
        }
    }

    public ItemStack getItemStack() {
        return itemStack.getItem(player);
    }

    public void setItemStack(MenuItemStack item, boolean update) {
        this.itemStack = item;

        if (update && (this.getMenu() != null)) {
            this.getMenu().addMenuItem(this, getSlot());
            this.getMenu().updateMenu();
        }
    }

    private Menu getMenu() {
        return this.menu;
    }

    private int getSlot() {
        return this.slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public boolean isClickable() {
        return clickable;
    }

    private void setClickable(boolean clickable) {
        this.clickable = clickable;
    }

    public void setTemporaryIcon(MenuItemStack item, long time) {
        ItemStack currentItemStack = getItemStack();
        setClickable(false);
        menu.getInventory().setItem(getSlot(), item.getItem(player));
        menu.updateMenu();
        Bukkit.getScheduler().runTaskLater(WarringPlugin.getInstance(), () -> {
            setClickable(true);
            menu.getInventory().setItem(getSlot(), currentItemStack);
            menu.updateMenu();
        }, time);
    }

    @Override
    public String toString() {
        return "MenuItem{menu=" + menu.toString() + ", item=" + getItemStack().toString() + ", slot=" + slot + ", clickable=" + clickable + "}";
    }

    public static class UnclickableMenuItem extends MenuItem {

        public UnclickableMenuItem(MenuItemStack item, Player player) {
            super((p, type, menuItem) -> {}, item, player);
        }

    }

    public interface MenuItemStack {
        ItemStack getItem(Player p);
    }

    public interface ItemAction {

        void onClick(Player p, InventoryClickType type, MenuItem item);
    }

}
