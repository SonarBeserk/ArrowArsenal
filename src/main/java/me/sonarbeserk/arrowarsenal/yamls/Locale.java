package me.sonarbeserk.arrowarsenal.yamls;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class Locale {

    private FileConfiguration locale = null;
    private File localeFile = null;

    private JavaPlugin plugin;

    public Locale(JavaPlugin plugin) {

        //lang directory check/create if not found
        new File(plugin.getDataFolder(), "locale").mkdir();

        this.plugin = plugin;

        saveDefault();
    }

    public void reload() {

        if (plugin.getConfig() == null) {

            plugin.reloadConfig();
        }

        saveDefault();

        if (localeFile == null) {

            localeFile = new File(plugin.getDataFolder() + "/" + "locale", plugin.getConfig().getString("settings.locale") + ".yml");
        }

        locale = YamlConfiguration.loadConfiguration(localeFile);

        // Look for defaults in the jar
        InputStream defConfigStream = plugin.getResource("locale" + "/" + plugin.getConfig().getString("settings.locale") + ".yml");

        if (defConfigStream != null) {

            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
            locale.setDefaults(defConfig);
        } else {

            plugin.getLogger().severe("Unsupported locale file in use. No defaults able to be loaded.");
            return;
        }
    }

    private FileConfiguration getFileConfiguration() {

        if (locale == null) {

            reload();
        }

        return locale;
    }

    private void saveDefault() {

        if (plugin.getConfig() == null) {

            plugin.reloadConfig();
        }

        if (plugin.getConfig().getString("settings.locale") == null || plugin.getConfig().getString("settings.locale").equalsIgnoreCase("")) {

            plugin.getLogger().severe("Invalid Locale Specified! Disabling.");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return;
        }

        if (localeFile == null) {

            localeFile = new File(plugin.getDataFolder() + "/" + "locale" + "/" + plugin.getConfig().getString("settings.locale") + ".yml");
        }

        if (!localeFile.exists()) {

            plugin.saveResource("locale" + "/" + plugin.getConfig().getString("settings.locale") + ".yml", false);
        }
    }

    private void save() {

        if (locale == null || localeFile == null) {return;}

        try {

            getFileConfiguration().save(localeFile);
        } catch (IOException ex) {

            plugin.getLogger().log(Level.SEVERE, "Could not save config to " + localeFile, ex);
        }
    }

    /**
     * Sets the message specified, does not persist
     * @param messagePath the path to the message
     * @param message the message
     */
    public void setMessage(String messagePath, String message) {

        getFileConfiguration().set(messagePath, message);
    }

    /**
     * Returns the message specified if found
     * @param messagePath the path to the message
     * @return the message specified if found
     */
    public String getMessage(String messagePath) {

        if (getFileConfiguration().get(messagePath) == null) {

            plugin.getLogger().severe("(" + messagePath + ") in locale could not be found!");
            return null;
        }

        return getFileConfiguration().getString(messagePath);
    }
}
