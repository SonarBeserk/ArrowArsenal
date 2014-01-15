package me.sonarbeserk.arrowarsenal.tracking;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
import java.util.HashMap;
import java.util.Map;

public class PlayerTracker {

    private static PlayerTracker instance = null;
    
    private ArrowArsenal plugin = null;

    private Map<String, String> playerArrowNamesList = null;

    public PlayerTracker(ArrowArsenal plugin) {

        instance = this;
        
        this.plugin = plugin;

        playerArrowNamesList = new HashMap<String, String>();
    }

    /**
     * Returns the current instance of the PlayerTracker
     * @return the current instance of the PlayerTracker
     */
    public static PlayerTracker getInstance() {

        return instance;
    }

    /**
     * Clears the instance of the registry, only used for cleanup purposes
     */
    public void clearInstance() {

        instance = null;
    }

    /**
     * Returns the internal name of the currently selected player arrow
     * @param playerName the name of the player to return the arrow name of
     * @return the internal name of the currently selected player arrow
     */
    public String getCurrentArrowName(String playerName) {

        for(String name: playerArrowNamesList.keySet()) {

            if(name.equalsIgnoreCase(playerName)) {

                return playerArrowNamesList.get(name);
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

        if(playerArrowNamesList == null || ArrowRegistry.getInstance().getArrows() == null || ArrowRegistry.getInstance().getArrows().size() == 0) {return;}

        playerArrowNamesList.put(playerName, arrow.getInternalName());
    }
}
