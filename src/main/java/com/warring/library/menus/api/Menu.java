package com.warring.library.menus.api;

import com.google.common.collect.Maps;
import com.warring.library.WarringPlugin;
import com.warring.library.pair.Pair;
import com.warring.library.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;

public class Menu implements InventoryHolder {
    private ConcurrentMap<Integer, MenuItem> items;
    private String title;
    private int rows;
    private boolean exitOnClickOutside;
    private MenuAPI.MenuCloseBehaviour menuCloseBehaviour;
    private boolean bypassMenuCloseBehaviour;
    private Menu parentMenu;
    private Inventory inventory;
    private int currentPage;
    private int maxPage;
    private Map<Integer, List<Pair<Integer, MenuItem>>> pageItems;
    private List<Integer> pageSlots;

    public Menu(String title, int rows) {
        this(title, rows, null);
    }

    private Menu(String title, int rows, Menu parentMenu) {
        this.items = Maps.newConcurrentMap();
        this.exitOnClickOutside = false;
        this.bypassMenuCloseBehaviour = false;
        this.title = Utils.toColor(title);
        this.rows = rows;
        this.parentMenu = parentMenu;
    }

    public MenuAPI.MenuCloseBehaviour getMenuCloseBehaviour() {
        return this.menuCloseBehaviour;
    }

    public void setMenuCloseBehaviour(MenuAPI.MenuCloseBehaviour menuCloseBehaviour) {
        this.menuCloseBehaviour = menuCloseBehaviour;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public Map<Integer, List<Pair<Integer, MenuItem>>> getPageItems() {
        return pageItems;
    }

    public void setPageItems(Map<Integer, List<Pair<Integer, MenuItem>>> pageItems) {
        this.pageItems = pageItems;
    }

    public List<Integer> getPageSlots() {
        return pageSlots;
    }

    public void setPageSlots(List<Integer> pageSlots) {
        this.pageSlots = pageSlots;
    }

    public boolean nextPage(Player player) {
        if (currentPage >= maxPage) return false;
        currentPage++;
        clearPageSlots();
        for (Pair<Integer, MenuItem> pair : pageItems.get(currentPage)) {
            addMenuItem(pair.getValue(), pair.getKey());
        }
        updateMenu();
        return true;
    }

    public boolean previousPage(Player player) {
        if (currentPage <= 1) return false;
        currentPage--;
        clearPageSlots();
        for (Pair<Integer, MenuItem> pair : pageItems.get(currentPage)) {
            addMenuItem(pair.getValue(), pair.getKey());
        }
        updateMenu();
        return true;
    }

    public void clearPageSlots() {
        for (Integer slot : pageSlots) {
            removeMenuItem(slot);
        }
    }

    public void setupPages(List<MenuItem> items, List<Integer> pageSlots) {
        currentPage = 1;
        pageItems = new HashMap<>();
        this.pageSlots = new ArrayList<>();
        this.pageSlots.addAll(pageSlots);
        if (items.size() <= pageSlots.size()) {
            maxPage = 1;
            List<Pair<Integer, MenuItem>> itemSlotList = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                itemSlotList.add(new Pair<>(pageSlots.get(i), items.get(i)));
            }
            pageItems.put(1, itemSlotList);
        } else {
            int pagesWholeNumber = items.size() / pageSlots.size();
            if (pagesWholeNumber * pageSlots.size() == items.size()) {
                maxPage = pagesWholeNumber;
            } else {
                maxPage = pagesWholeNumber + 1;
            }
            int count = 0;
            int page = 1;
            pageItems.put(1, new ArrayList<>());
            for (MenuItem item : items) {
                if (count == pageSlots.size()) {
                    count = 0;
                    page++;
                    pageItems.put(page, new ArrayList<>());
                }
                pageItems.get(page).add(new Pair<>(pageSlots.get(count), item));
                count++;
            }
        }
        setupInitialPage();
    }

    private void setupInitialPage() {
        clearPageSlots();
        for (Pair<Integer, MenuItem> pair : pageItems.get(currentPage)) {
            addMenuItem(pair.getValue(), pair.getKey());
        }
    }

    public void setBypassMenuCloseBehaviour(boolean bypassMenuCloseBehaviour) {
        this.bypassMenuCloseBehaviour = bypassMenuCloseBehaviour;
    }

    public boolean bypassMenuCloseBehaviour() {
        return this.bypassMenuCloseBehaviour;
    }

    private void setExitOnClickOutside(boolean exit) {
        this.exitOnClickOutside = exit;
    }

    public Map<Integer, MenuItem> getMenuItems() {
        return this.items;
    }

    public boolean addMenuItem(MenuItem item, int x, int y) {
        return this.addMenuItem(item, y * 9 + x);
    }

    public MenuItem getMenuItem(int index) {
        return this.items.get(index);
    }

    public boolean addMenuItem(MenuItem item, int index) {
        ItemStack slot = this.getInventory().getItem(index);
        if (slot != null && slot.getType() != Material.AIR) {
            this.removeMenuItem(index);
        }
        item.setSlot(index);
        this.getInventory().setItem(index, item.getItemStack());
        this.items.put(index, item);
        item.addToMenu(this);
        return true;
    }

    public boolean removeMenuItem(int x, int y) {
        return this.removeMenuItem(y * 9 + x);
    }

    public boolean removeMenuItem(int index) {
        ItemStack slot = this.getInventory().getItem(index);
        if (slot == null || slot.getType().equals(Material.AIR)) {
            return false;
        }
        this.getInventory().clear(index);
        this.items.remove(index).removeFromMenu(this);
        return true;
    }

    void selectMenuItem(Player player, int index, InventoryClickType clickType) {
        if (this.items.containsKey(index)) {
            MenuItem item = this.items.get(index);
            if (item.isClickable()) {
                item.onClick(player, clickType);
            }
        }
    }

    public void openMenu(Player player) {
        if (!this.getInventory().getViewers().contains(player)) {
            player.openInventory(this.getInventory());
        }
    }

    public void closeMenu(Player player) {
        if (this.getInventory().getViewers().contains(player)) {
            this.getInventory().getViewers().remove(player);
            player.closeInventory();
        }
    }

    public void scheduleUpdateTask(Player player, int ticks) {
        new BukkitRunnable() {
            public void run() {
                if (player == null || Bukkit.getPlayer(player.getName()) == null) {
                    this.cancel();
                    return;
                }
                if (player.getOpenInventory() == null || player.getOpenInventory().getTopInventory() == null || player.getOpenInventory().getTopInventory().getHolder() == null) {
                    this.cancel();
                    return;
                }
                if (!(player.getOpenInventory().getTopInventory().getHolder() instanceof Menu)) {
                    this.cancel();
                    return;
                }
                Menu menu = (Menu) player.getOpenInventory().getTopInventory().getHolder();
                if (!menu.inventory.equals(Menu.this.inventory)) {
                    this.cancel();
                    return;
                }
                for (Map.Entry<Integer, MenuItem> entry : menu.items.entrySet()) {
                    Menu.this.getInventory().setItem(entry.getKey(), entry.getValue().getItemStack());
                }
            }
        }.runTaskTimer(WarringPlugin.getInstance(), (long) ticks, (long) ticks);
    }

    public Menu getParent() {
        return this.parentMenu;
    }

    public void setParent(Menu menu) {
        this.parentMenu = menu;
    }

    public Inventory getInventory() {
        if (this.inventory == null) {
            this.inventory = Bukkit.createInventory(this, this.rows * 9, this.title);
        }
        return this.inventory;
    }

    public boolean exitOnClickOutside() {
        return this.exitOnClickOutside;
    }

    @Override
    protected Menu clone() throws CloneNotSupportedException {
        Menu menu = (Menu) super.clone();
        Menu clone = new Menu(this.title, this.rows);
        clone.setExitOnClickOutside(this.exitOnClickOutside);
        clone.setMenuCloseBehaviour(this.menuCloseBehaviour);
        for (Map.Entry<Integer, MenuItem> entry : this.items.entrySet()) {
            clone.addMenuItem(entry.getValue(), entry.getKey());
        }
        return clone;
    }
    
    public Menu cloneMenu() throws CloneNotSupportedException {
        Menu clone = (Menu) super.clone();
        return clone;
    }

    public void updateMenu() {
        for (Map.Entry<Integer, MenuItem> entry : items.entrySet()) {
            if (!entry.getValue().isClickable()) continue;
            Menu.this.getInventory().setItem(entry.getKey(), entry.getValue().getItemStack());
        }
        for (HumanEntity entity : this.getInventory().getViewers()) {
            ((Player) entity).updateInventory();
        }
    }

    public ConcurrentMap<Integer, MenuItem> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return "Menu{title=" + title + ", rows=" + rows + ", exitOnClickOutside=" + exitOnClickOutside + ", bypassMenuCloseBehavior=" + bypassMenuCloseBehaviour + ", parentMenu=" + parentMenu.toString() + ", inventory=" + inventory.toString() + ", currentPage=" + currentPage + ", maxPage=" + maxPage + ", pageItems=" + pageItems + "pageSlots=" + pageSlots + "}";
    }

}
