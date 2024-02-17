package ximronno.bankickgui.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import ximronno.bankickgui.BanKickGUI;

import java.util.ArrayList;
import java.util.List;

public class GUIOpenCommand implements CommandExecutor {

    private final BanKickGUI plugin;

    public GUIOpenCommand(BanKickGUI plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("You are not a player!");
            return true;
        }
        Player p = (Player) sender;
        plugin.openMainMenu(p);

        return true;
    }
}
