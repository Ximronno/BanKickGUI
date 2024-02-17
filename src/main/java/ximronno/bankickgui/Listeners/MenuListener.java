package ximronno.bankickgui.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.persistence.PersistentDataType;
import ximronno.bankickgui.BanKickGUI;


public class MenuListener implements Listener {

    private final BanKickGUI plugin;

    public MenuListener(BanKickGUI plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        if(e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("main-menu-name")))) {
            int switch_int = 0;
            try {
                switch_int = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(plugin.key, PersistentDataType.INTEGER);
            } catch (NullPointerException event) {
                e.setCancelled(true);
                return;
            }
            switch (switch_int) {
                case 1:
                    Player target = plugin.getServer().getPlayerExact(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                    plugin.openChoiceMenu((Player) e.getView().getPlayer(), target);
                    break;
                case 2:
                    e.getView().getPlayer().closeInventory();
                    break;
            }
            e.setCancelled(true);
        }
        else if(e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("choice-menu-name"))))  {
            int switch_int = 0;
            Player target = null;
            try {
                switch_int = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(plugin.key, PersistentDataType.INTEGER);
                target = Bukkit.getPlayer(e.getClickedInventory().getItem(4).getItemMeta().getDisplayName());
            } catch (NullPointerException event) {
                e.setCancelled(true);
                return;
            }
            switch (switch_int) {
                case 3:
                    plugin.openConfirmMenu((Player) e.getView().getPlayer(), true,target);
                    break;
                case 4:
                    plugin.openConfirmMenu((Player) e.getView().getPlayer(), false,target);
                    break;
                case 5:
                    plugin.openMainMenu((Player) e.getView().getPlayer());
                    break;
            }
            e.setCancelled(true);
        }
        else if(e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("confirm-menu-name"))))  {
            int switch_int = 0;
            Player target = null;
            try {
                target  = Bukkit.getPlayer(e.getClickedInventory().getItem(4).getItemMeta().getDisplayName());
                switch_int = e.getCurrentItem().getItemMeta().getPersistentDataContainer().get(plugin.key, PersistentDataType.INTEGER);
            } catch (NullPointerException event) {
                e.setCancelled(true);
                return;
            }
            Player player = (Player) e.getView().getPlayer();
            if(e.getClickedInventory().getItem(4).getItemMeta().getPersistentDataContainer().get(plugin.key,PersistentDataType.INTEGER) == 8) {
                switch (switch_int) {
                    case 7:
                        plugin.banPlayer(target);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("successfully-banned-message")));
                        plugin.openMainMenu(player);
                        break;
                    case 6:
                        player.closeInventory();
                        plugin.openChoiceMenu((Player) e.getView().getPlayer(),target);
                        break;
                }
            }
            else if(e.getClickedInventory().getItem(4).getItemMeta().getPersistentDataContainer().get(plugin.key,PersistentDataType.INTEGER) == 9) {
                switch (switch_int) {
                    case 7:
                        plugin.kickPlayer(target);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',plugin.getConfig().getString("successfully-kicked-message")));
                        plugin.openMainMenu(player);
                        break;
                    case 6:
                        player.closeInventory();
                        plugin.openChoiceMenu((Player) e.getView().getPlayer(),target);
                        break;
                }
            }
            e.setCancelled(true);
        }
    }
}
