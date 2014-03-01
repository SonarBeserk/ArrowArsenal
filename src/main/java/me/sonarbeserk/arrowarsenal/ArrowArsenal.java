package me.sonarbeserk.arrowarsenal;

import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.commands.MainCmd;
import me.sonarbeserk.arrowarsenal.listeners.ArrowListener;
import me.sonarbeserk.arrowarsenal.listeners.FileVersionListener;
import me.sonarbeserk.arrowarsenal.tracking.PlayerTracker;
import me.sonarbeserk.arrowarsenal.utils.Messaging;
import me.sonarbeserk.arrowarsenal.utils.Data;
import me.sonarbeserk.arrowarsenal.utils.Locale;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class ArrowArsenal extends JavaPlugin {

    public static Economy economy = null;

    private Locale locale = null;

    private Data data = null;

    private Messaging messaging = null;

    private ArrowRegistry registry = null;

    private PlayerTracker tracker = null;

    public void onEnable() {

        saveDefaultConfig();

        locale = new Locale(this);

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

                getLogger().warning(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLocale().getMessage("warning-no-economy-found"))));
            } else {

                getLogger().info(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLocale().getMessage("hooked-vault"))));
            }
        } else {

            getLogger().warning(ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', getLocale().getMessage("warning-vault-not-found"))));
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
     * Returns the locale in use
     * @return the locale in use
     */
    public Locale getLocale() {

        return locale;
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

        locale = null;
    }
}
