package me.sonarbeserk.arrowarsenal.tracking;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
import java.util.HashMap;
import java.util.Map;

public class PlayerTracker {

    private static PlayerTracker instance = null;
    
    private ArrowArsenal plugin = null;

    private Map<String, String> playerArrowNamesMap = null;

    public PlayerTracker(ArrowArsenal plugin) {

        instance = this;
        
        this.plugin = plugin;

        playerArrowNamesMap = new HashMap<String, String>();

        Map<String, Object> persistedMap = new HashMap<String, Object>();

        if(plugin.getData().getConfigurationSection("arrow-selections") != null) {

            persistedMap = plugin.getData().getConfigurationSection("arrow-selections").getValues(false);
        }

        for(String name: persistedMap.keySet()) {

            playerArrowNamesMap.put(name, String.valueOf(persistedMap.get(name)));
        }
    }

    /**
     * Returns the current instance of the PlayerTracker
     * @return the current instance of the PlayerTracker
     */
    public static PlayerTracker getInstance() {

        return instance;
    }

    /**
     * Returns the internal name of the currently selected player arrow
     * @param playerName the name of the player to return the arrow name of
     * @return the internal name of the currently selected player arrow
     */
    public String getCurrentArrowName(String playerName) {

        for(String name: playerArrowNamesMap.keySet()) {

            if(name.equalsIgnoreCase(playerName)) {

                return playerArrowNamesMap.get(name);
            }
        }

        return null;
    }

    /**
     * Sets the player's current arrow
     * @param playerName the name of the player to change the arrow selection of
     * @param arrow the new arrow to use
     */
    public void setCurrentArrow(String playerName, SArrow arrow) {

        if(playerArrowNamesMap == null || ArrowRegistry.getInstance().getArrowsMap() == null || ArrowRegistry.getInstance().getArrowsMap().size() == 0) {return;}

        playerArrowNamesMap.put(playerName, arrow.getInternalName());
    }

    /**
     * Returns a read-only version of player's arrow selections
     * @return a read-only version of player's arrow selections
     */
    public Map<String, String> getPlayerArrowsNameList() {

        Map<String, String> readOnlyMap = new HashMap<String, String>();

        readOnlyMap.putAll(playerArrowNamesMap);

        return readOnlyMap;
    }
}
