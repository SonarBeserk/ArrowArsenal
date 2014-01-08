package me.sonarbeserk.arrowarsenal.commands;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
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

                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal"));
                return true;
            } else {

                plugin.getMessaging().sendMessage(sender, false, true, plugin.getLocale().getMessage("usage-arrowarsenal"));
                return true;
            }
        }

        if(args.length > 0) {

            if(args[0].equalsIgnoreCase("help")) {

                if(sender instanceof Player) {

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal"));
                    return true;
                } else {

                    plugin.getMessaging().sendMessage(sender, false, true, plugin.getLocale().getMessage("usage-arrowarsenal"));
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("reload")) {

                if(!sender.hasPermission("arrowarsenal.commands.reload")) {

                    if(sender instanceof Player) {
                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    }
                }

                plugin.getLocale().reload();
                plugin.reloadConfig();

                if(sender instanceof Player) {

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("reloaded"));
                    return true;
                } else {

                    plugin.getMessaging().sendMessage(sender, false, true, plugin.getLocale().getMessage("reloaded"));
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("list")) {

                for(int id: ArrowRegistry.getInstance().getArrows().keySet()) {

                    SArrow arrow = ArrowRegistry.getInstance().getArrows().get(id);

                    if(arrow == null) {continue;}

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("arrow-list").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                        continue;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, true, plugin.getLocale().getMessage("arrow-list").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                        continue;
                    }
                }
            }

            if(sender instanceof Player) {
                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            } else {

                plugin.getMessaging().sendMessage(sender, false, true, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            }
        }

        return false;
    }
}
