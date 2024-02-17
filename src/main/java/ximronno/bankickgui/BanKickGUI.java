package ximronno.bankickgui;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import ximronno.bankickgui.Commands.GUIOpenCommand;
import ximronno.bankickgui.Listeners.MenuListener;

import java.util.ArrayList;
import java.util.List;

public final class BanKickGUI extends JavaPlugin {

    @Override
    public void onEnable() {

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        getCommand("bangui").setExecutor(new GUIOpenCommand(this));
        Bukkit.getServer().getPluginManager().registerEvents(new MenuListener(this),this);

    }

    @Override
    public void onDisable() {
    }

    public NamespacedKey key = new NamespacedKey(this, this.getConfig().getString("namespaced-key"));
    public void openMainMenu(Player p) {
        Inventory main_menu = Bukkit.createInventory(p,36, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("main-menu-name")));
        Player[] online_p =  Bukkit.getServer().getOnlinePlayers().toArray(new Player[0]);
        List<String> player_lore = new ArrayList<>();
        player_lore.add(ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("player-lore")));
        for(int i = 0; i < online_p.length; i++)  {
            ItemStack player_item = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta player_item_meta = (SkullMeta) player_item.getItemMeta();
            player_item_meta.setDisplayName(online_p[i].getDisplayName());
            player_item_meta.setOwningPlayer(Bukkit.getOfflinePlayer(online_p[i].getUniqueId()));
            player_item_meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 1);
            player_item_meta.setLore(player_lore);
            player_item.setItemMeta(player_item_meta);

            main_menu.addItem(player_item);
        }
        ItemStack close_item = new ItemStack(Material.BARRIER);
        ItemMeta close_item_meta = close_item.getItemMeta();
        close_item_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("close-button-name")));
        close_item_meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 2);
        close_item.setItemMeta(close_item_meta);

        main_menu.setItem(35,close_item);

        p.openInventory(main_menu);
    }

    public void openConfirmMenu(Player p, boolean kickOrBan, Player target) {
        Inventory confirm_menu = Bukkit.createInventory(p,27, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("confirm-menu-name")));

        ItemStack cancel_item = new ItemStack(Material.BARRIER);
        ItemMeta cancel_item_meta = cancel_item.getItemMeta();
        cancel_item_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("cancel-button-name")));
        cancel_item_meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 6);
        cancel_item.setItemMeta(cancel_item_meta);

        ItemStack accept_item = new ItemStack(Material.GREEN_WOOL);
        ItemMeta accept_item_meta = accept_item.getItemMeta();
        accept_item_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("accept-button-name")));
        accept_item_meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 7);
        accept_item.setItemMeta(accept_item_meta);

        List<String> skull_lore = new ArrayList<>();

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull_meta = (SkullMeta) skull.getItemMeta();

        if(kickOrBan) {
            skull_lore.add(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("skull-ban-lore")));
            skull_meta.setDisplayName(target.getDisplayName());
            skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
            skull.setItemMeta(skull_meta);
            skull_meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER , 8 );
        }
        else {
            skull_lore.add(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("skull-kick-lore")));
            skull_meta.setDisplayName(target.getDisplayName());
            skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
            skull_meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER , 9 );
        }
        skull_lore.add(target.getDisplayName() + "?");
        skull_meta.setLore(skull_lore);
        skull.setItemMeta(skull_meta);

        confirm_menu.setItem(4,skull);
        confirm_menu.setItem(12,cancel_item);
        confirm_menu.setItem(14,accept_item);



        p.openInventory(confirm_menu);
    }
    public void openChoiceMenu(Player p, Player target) {
        Inventory choice_menu = Bukkit.createInventory(p,27, ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("choice-menu-name")));

        List<String> skull_lore = new ArrayList<>();
        skull_lore.add("Choose");
        skull_lore.add("Kick or ban?");

        ItemStack ban_item = new ItemStack(Material.BARRIER);
        ItemMeta ban_item_meta = ban_item.getItemMeta();
        ban_item_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("ban-button-name")));
        ban_item_meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 3);
        ban_item.setItemMeta(ban_item_meta);

        ItemStack kick_item = new ItemStack(Material.LEATHER_BOOTS);
        ItemMeta kick_item_meta = kick_item.getItemMeta();
        kick_item_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("kick-button-name")));
        kick_item_meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 4);
        kick_item.setItemMeta(kick_item_meta);

        ItemStack return_item = new ItemStack(Material.RED_WOOL);
        ItemMeta return_item_meta = return_item.getItemMeta();
        return_item_meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',this.getConfig().getString("return-button-name")));
        return_item_meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, 5);
        return_item.setItemMeta(return_item_meta);

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull_meta = (SkullMeta) skull.getItemMeta();
        skull_meta.setDisplayName(target.getDisplayName());
        skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
        skull_meta.setLore(skull_lore);
        skull.setItemMeta(skull_meta);

        choice_menu.setItem(4,skull);
        choice_menu.setItem(12,ban_item);
        choice_menu.setItem(14,kick_item);
        choice_menu.setItem(26,return_item);



        p.openInventory(choice_menu);
    }
    public void kickPlayer(Player target) {
        target.kickPlayer(this.getConfig().getString("kick-message"));
    }
    public void banPlayer(Player target) {
        String bannedName = target.getDisplayName();
        target.getServer().getBanList(BanList.Type.NAME).addBan(bannedName,this.getConfig().getString("ban-message"),null,null);
        target.kickPlayer(this.getConfig().getString("ban-message"));
    }
}
