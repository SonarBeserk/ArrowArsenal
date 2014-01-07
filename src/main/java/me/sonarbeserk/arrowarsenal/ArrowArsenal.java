package me.sonarbeserk.arrowarsenal;

import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.commands.MainCmd;
import me.sonarbeserk.arrowarsenal.utils.Messaging;
import me.sonarbeserk.arrowarsenal.utils.Data;
import me.sonarbeserk.arrowarsenal.utils.Locale;
import org.bukkit.plugin.java.JavaPlugin;

public class ArrowArsenal extends JavaPlugin {

    private Locale locale = null;

    private Data data = null;

    private Messaging utils = null;

    private ArrowRegistry registry = null;

    public void onEnable() {

        saveDefaultConfig();

        locale = new Locale(this);

        if(!getServer().getPluginManager().isPluginEnabled(this)) {return;}

        data = new Data(this);

        if(!getServer().getPluginManager().isPluginEnabled(this)) {return;}

        utils = new Messaging(this);

        getCommand(getDescription().getName().toLowerCase()).setExecutor(new MainCmd(this));

        registry = new ArrowRegistry(this);
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

    return utils;
    }

    public void onDisable() {

        data.set("disabled-arrows", ArrowRegistry.getInstance().getDisabledArrowNames());
        data.set("arrow-list", ArrowRegistry.getInstance().getArrows());
        data.save();
        data = null;

        registry.clearInstance();

        utils = null;

        locale = null;
    }
}
