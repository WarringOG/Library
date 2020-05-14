package com.warring.library.menus;

import com.warring.library.WarringPlugin;
import com.warring.library.menus.api.Menu;
import com.warring.library.menus.api.MenuAPI;
import com.warring.library.menus.api.MenuItem;
import com.warring.library.utils.ItemUtils;
import com.warring.library.utils.Utils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CustomMenu {

    private Menu menu;
    private int size;

    public CustomMenu(ConfigurationSection sec) {
        menu = MenuAPI.getInstance().createMenu(Utils.toColor(sec.getString("Title")), sec.getInt("Size") / 9);
        this.size = sec.getInt("Size");
        for (int i = 0; i < size; ++i) {
            menu.addMenuItem(new MenuItem.UnclickableMenuItem() {
                @Override
                public ItemStack getItemStack() {
                    return ItemUtils.getConfigItem(WarringPlugin.getInstance().getConfig().getConfigurationSection("MenuOptions.FillerItem"));
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
                    return ItemUtils.getConfigItem(WarringPlugin.getInstance().getConfig().getConfigurationSection("MenuOptions.FillerItem"));
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


}
