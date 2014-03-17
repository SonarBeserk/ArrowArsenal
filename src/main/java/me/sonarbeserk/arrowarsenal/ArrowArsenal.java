package me.sonarbeserk.arrowarsenal;

import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.commands.MainCmd;
import me.sonarbeserk.arrowarsenal.listeners.ArrowListener;
import me.sonarbeserk.arrowarsenal.tracking.PlayerTracker;
import me.sonarbeserk.listeners.FileVersionListener;
import me.sonarbeserk.utils.Data;
import me.sonarbeserk.utils.Language;
import me.sonarbeserk.utils.Messaging;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/***********************************************************************************************************************
 *
 * ArrowArsenal - Bukkit plugin to provide a system to organize special arrows
 * ===========================================================================
 *
 * Copyright (C) 2013, 2014 by SonarBeserk
 * http://dev.bukkit.org/bukkit-plugins/arrowarsenal/
 *
 ***********************************************************************************************************************
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 ***********************************************************************************************************************/
public class ArrowArsenal extends JavaPlugin {

    public static Economy economy = null;

    private Language language = null;

    private Data data = null;

    private Messaging messaging = null;

    private ArrowRegistry registry = null;

    private PlayerTracker tracker = null;

    public void onEnable() {

        saveDefaultConfig();

        language = new Language(this);

        if(!getServer().getPluginManager().isPluginEnabled(this)) {return;}

        data = new Data(this);

        messaging = new Messaging(this);

        getCommand(getDescription().getName().toLowerCase()).setExecutor(new MainCmd(this));

        registry = new ArrowRegistry(this);

        tracker = new PlayerTracker(this);

        getServer().getPluginManager().registerEvents(new ArrowListener(this), this);

        getServer().getPluginManager().registerEvents(new FileVersionListener(this), this);

        if(getServer().getPluginManager().getPlugin("Vault") != null && getServer().getPluginManager().getPlugin("Vault").isEnabled()) {

            setupEconomy();

            if(economy == null) {

                getLogger().warning(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLanguage().getMessage("warning-no-economy-found"))));
            } else {

                getLogger().info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLanguage().getMessage("hooked-vault"))));
            }
        } else {

            getLogger().warning(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLanguage().getMessage("warning-vault-not-found"))));
        }
    }

    private boolean setupEconomy() {

        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);

        if(economyProvider != null) {
		
            economy = economyProvider.getProvider();
        }

        return (economy != null);
    }

    /**
     * Returns the language in use
     * @return the language in use
     */
    public Language getLanguage() {

        return language;
    }

    /**
     * Returns the data instance
     * @return the data instance
     */
    public Data getData() {

        return data;
    }

    /**
     * Returns the plugin utils
     * @return the plugin utils
     */
    public Messaging getMessaging() {

        return messaging;
    }

    public void onDisable() {

        data.set("disabled-arrows", ArrowRegistry.getInstance().getDisabledArrowNames());
        data.createSection("arrow-selections", PlayerTracker.getInstance().getPlayerArrowsNameList());
        data.save();
        data = null;

        registry = null;

        tracker = null;

        messaging = null;

        language = null;
    }
}
