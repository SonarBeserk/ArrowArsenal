package me.sonarbeserk.arrowarsenal.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Utils {

    private JavaPlugin plugin = null;

    public Utils(JavaPlugin plugin) {

        this.plugin = plugin;
    }

    /**
     * Sends a commandsender a message
     * @param receiver the commandsender to send a message to
     * @param colored if the message should be colored
     * @param msg the message to send
     */
    public void sendMessage(CommandSender receiver, boolean colored, String msg) {

        if(receiver == null || msg == null) {return;}

        if(receiver instanceof ConsoleCommandSender) {

            receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)));
            return;
        }

        if(colored) {

            receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            return;
        } else if(!colored) {

            receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)));
            return;
        }
    }

    /**
     * Sends a player a message
     * @param receiver the plater to send a message to
     * @param colored if the message should be colored
     * @param msg the message to send
     */
    public void sendMessage(Player receiver, boolean colored, String msg) {

        if(receiver == null || msg == null) {return;}

        if(colored) {

            receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
            return;
        } else if(!colored) {

            receiver.sendMessage(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', msg)));
            return;
        }
    }

    /**
     * Logs a debug message to the JavaPlugin's logger
     * @param eCheck checks for debug mode being enabled
     * @param message the message to log
     */
    public void debug(boolean eCheck, String message) {

        if(eCheck) {

            if(!plugin.getConfig().getBoolean("settings.debug")) {return;}

            plugin.getLogger().info(message);
        } else {

            plugin.getLogger().info(message);
        }
    }
}
