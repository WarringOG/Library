package com.warring.library.menus;

import com.warring.library.ServerVersion;
import com.warring.library.WarringPlugin;
import com.warring.library.menus.api.Menu;
import com.warring.library.menus.api.MenuAPI;
import com.warring.library.menus.api.MenuItem;
import com.warring.library.utils.ItemUtils;
import com.warring.library.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class CustomMenu {

    private Menu menu;
    private int size;
    private ConfigurationSection sec;

    public CustomMenu(ConfigurationSection sec) {
        this.sec = sec;
        menu = MenuAPI.getInstance().createMenu(Utils.toColor(sec.getString("Title")), sec.getInt("Size") / 9);
        this.size = sec.getInt("Size");
        this.sec = sec;
        for (int i = 0; i < size; ++i) {
            menu.addMenuItem(new MenuItem.UnclickableMenuItem() {
                @Override
                public ItemStack getItemStack() {
                    return getItem();
                }
            }, i);
        }
    }

    public CustomMenu(String title, int size) {
        menu = MenuAPI.getInstance().createMenu(Utils.toColor(title), size / 9);
        this.size = size;
        for (int i = 0; i < size; ++i) {
            menu.addMenuItem(new MenuItem.UnclickableMenuItem() {
                @Override
                public ItemStack getItemStack() {
                    return getItem();
                }
            }, i);
        }
    }

    public int getSize() {
        return size;
    }

    public Menu getMenu() {
        return menu;
    }

    public void openMenu(Player p) {
        menu.openMenu(p);
    }

    public ConfigurationSection getSec() {
        return sec;
    }

    public void setMenuBehavior(MenuAPI.MenuCloseBehaviour menuBehavior) {
        menu.setMenuCloseBehaviour(menuBehavior);
    }

    public void setParent(Menu parent) {
        menu.setParent(parent);
    }

    public Menu clone() {
        return menu.cloneMenu();
    }

    public void setupPages(List<MenuItem> items, List<Integer> list) {
        menu.setupPages(items, list);
    }

    public ItemStack getItem() {
        return ServerVersion.isOver_V1_12() ? ItemUtils.getConfigItemNonLegacy(WarringPlugin.getInstance().getConfig().getConfigurationSection("MenuOptions.FillerItem")) : ItemUtils.getConfigItemLegacy(WarringPlugin.getInstance().getConfig().getConfigurationSection("MenuOptions.FillerItem"));
    }
}
