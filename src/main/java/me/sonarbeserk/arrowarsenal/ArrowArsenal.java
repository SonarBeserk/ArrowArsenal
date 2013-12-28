package me.sonarbeserk.arrowarsenal;

import me.sonarbeserk.arrowarsenal.commands.MainCmd;
import me.sonarbeserk.arrowarsenal.utils.Utils;
import me.sonarbeserk.arrowarsenal.yamls.Data;
import me.sonarbeserk.arrowarsenal.yamls.Locale;
import org.bukkit.plugin.java.JavaPlugin;

public class ArrowArsenal extends JavaPlugin {

    private Locale locale = null;

    private Data data = null;

    private Utils utils = null;

    public void onEnable() {

        saveDefaultConfig();

        locale = new Locale(this);

        if(!getServer().getPluginManager().isPluginEnabled(this)) {return;}

        data = new Data(this);

        if(!getServer().getPluginManager().isPluginEnabled(this)) {return;}

        utils = new Utils(this);

        getCommand(getDescription().getName().toLowerCase()).setExecutor(new MainCmd(this));
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
    public Utils getUtils() {

        return utils;
    }

    public void onDisable() {

        data.save();
        data = null;

        utils = null;

        locale = null;
    }
}
