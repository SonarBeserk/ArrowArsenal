package me.sonarbeserk.arrowarsenal.commands;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCmd implements CommandExecutor {

    private ArrowArsenal plugin = null;

    public MainCmd(ArrowArsenal plugin) {

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 0) {

            if(sender instanceof Player) {

                plugin.getUtils().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            } else {

                plugin.getUtils().sendMessage(sender, false, true, plugin.getLocale().getMessage("usage-arsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            }
        }

        if(args.length > 0) {

            if(args[0].equalsIgnoreCase("help")) {

                if(sender instanceof Player) {

                    plugin.getUtils().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                    return true;
                } else {

                    plugin.getUtils().sendMessage(sender, false, true, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("reload")) {

                if(!sender.hasPermission("neigh.commands.reload")) {

                    if(sender instanceof Player) {
                        plugin.getUtils().sendMessage(sender, true, true, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    } else {

                        plugin.getUtils().sendMessage(sender, true, true, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    }
                }

                plugin.getLocale().reload();
                plugin.reloadConfig();

                if(sender instanceof Player) {

                    plugin.getUtils().sendMessage(sender, true, true, plugin.getLocale().getMessage("reloaded"));
                    return true;
                } else {

                    plugin.getUtils().sendMessage(sender, false, true, plugin.getLocale().getMessage("reloaded"));
                    return true;
                }
            }

            if(sender instanceof Player) {
                plugin.getUtils().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            } else {

                plugin.getUtils().sendMessage(sender, false, true, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            }
        }

        return false;
    }
}
