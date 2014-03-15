package me.sonarbeserk.arrowarsenal.tracking;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
import java.util.HashMap;
import java.util.Map;

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
