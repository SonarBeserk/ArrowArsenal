package me.sonarbeserk.arrowarsenal.listeners;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.utils.LatestVersionsFile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public class FileVersionListener {

    private ArrowArsenal plugin = null;

    private LatestVersionsFile latestVersionsFile = null;

    public FileVersionListener(ArrowArsenal plugin) {

        this.plugin = plugin;

        latestVersionsFile = new LatestVersionsFile(plugin);
    }

    @EventHandler(ignoreCancelled = true)
    public void pluginEnable(PluginEnableEvent e) {

        if(!e.getPlugin().getName().equalsIgnoreCase(plugin.getName())) {return;}

        if(latestVersionsFile == null) {return;}

        boolean configOutOfDate = false;
        boolean localeOutOfDate = false;

        if((Double) latestVersionsFile.get("config") > plugin.getConfig().getDouble("version")) {

            configOutOfDate = true;
        }

        if((Double) latestVersionsFile.get("locale") > Double.parseDouble(plugin.getLocale().getMessage("version"))) {

            localeOutOfDate = true;
        }

        if(configOutOfDate) {

            plugin.getMessaging().sendMessage(plugin.getServer().getConsoleSender(), false, false, plugin.getLocale().getMessage("out-of-date-config"));
        }

        if(localeOutOfDate) {

            plugin.getMessaging().sendMessage(plugin.getServer().getConsoleSender(), false, false, plugin.getLocale().getMessage("out-of-date-locale"));
        }

        for(Player player: plugin.getServer().getOnlinePlayers()) {

            if(player.hasPermission("arrowarsenal.notify.files")) {

                if(configOutOfDate) {

                    plugin.getMessaging().sendMessage(player, true, true, plugin.getLocale().getMessage("out-of-date-config"));
                }

                if(localeOutOfDate) {

                    plugin.getMessaging().sendMessage(player, true, true, plugin.getLocale().getMessage("out-of-date-locale"));
                }

                continue;
            }
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void pluginDisable(PluginDisableEvent e) {

        if(!e.getPlugin().getName().equalsIgnoreCase(plugin.getName())) {return;}

        if(latestVersionsFile == null) {return;}

        latestVersionsFile = null;
    }
}
