package me.sonarbeserk.arrowarsenal.arrows;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class ArrowRegistry {

    private static ArrowRegistry instance = null;

    private ArrowArsenal plugin = null;

    private Map<Integer, SArrow> arrows = null;

    private List<String> disabledArrowNames = null;

    public ArrowRegistry(ArrowArsenal plugin) {

        instance = this;

        this.plugin = plugin;

        arrows = new HashMap<Integer, SArrow>();

        if(arrows == null) {

            arrows = new HashMap<Integer, SArrow>();
        }

        disabledArrowNames = (List<String>) plugin.getData().get("disabled-arrows");

        if(disabledArrowNames == null) {

            disabledArrowNames = new ArrayList<String>();
        }
    }

    /**
     * Returns the current instance of the ArrowRegistry
     * @return the current instance of the ArrowRegistry
     */
    public static ArrowRegistry getInstance() {

        return instance;
    }

    /**
     * Returns a read-only map of registered arrows
     * @return a read-only map of registered arrows
     */
    public Map<Integer, SArrow> getArrowsMap() {

        if(arrows == null) {return null;}

        Map<Integer, SArrow> readOnlyList = new HashMap<Integer, SArrow>();

        if(arrows.size() == 0) {

            return readOnlyList;
        }

        for(int id: arrows.keySet()) {

            readOnlyList.put(id, arrows.get(id));
        }

        return readOnlyList;
    }

    /**
     * Returns the arrow with the internal name specified
     * @param internalName the internal name of the arrow
     * @return the arrow with the internal name specified
     */
    public SArrow getArrow(String internalName) {

        if(arrows.size() == 0) {return null;}

        for(int id: arrows.keySet()) {

            if(arrows.get(id).getInternalName().equalsIgnoreCase(internalName)) {

                return arrows.get(id);
            }
        }

        return null;
    }

    /**
     * Returns a read-only list of disabled arrows
     * @return a read-only list of disabled arrows
     */
    public List<String> getDisabledArrowNames() {

        List<String> readOnlyList = new ArrayList<String>();

        readOnlyList.addAll(disabledArrowNames);

        return readOnlyList;
    }

    /**
     * Adds an arrow to the registry
     * @param arrow the arrow to add to the registry
     */
    public void addArrow(SArrow arrow) {

        if(arrows == null) {return;}

        if(arrow.getDisplayName() == null) {

            plugin.getLogger().severe(plugin.getLanguage().getMessage("severe-no-displayname").replace("{class}", arrow.getClass().getName()));
            return;

        } else if(arrow.getInternalName() == null) {

            plugin.getLogger().severe(plugin.getLanguage().getMessage("severe-no-internalname").replace("{class}", arrow.getClass().getName()));
            return;
        }

        if(arrows.size() == 0) {

            arrows.put(1, arrow);

            plugin.getMessaging().debug(plugin.getLanguage().getMessage("debug-arrow-added").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", arrow.getAuthors() + ""));

            return;
        }

        arrows.put(arrows.size() + 1, arrow);

        plugin.getMessaging().debug(plugin.getLanguage().getMessage("debug-arrow-added").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", arrow.getAuthors() + ""));

        return;
    }

    /**
     * Removes an arrow from the registry
     * @param internalName the internal name of the arrow to look for
     */
    public void removeArrow(String internalName) {

        if(arrows == null || arrows.size() == 0) {return;}

        List<Integer> idsToRemove = new ArrayList<Integer>();

        for(int id: arrows.keySet()) {

            if(arrows.get(id) == null) {continue;}

            if(arrows.get(id).getInternalName().equalsIgnoreCase(internalName)) {

                idsToRemove.add(id);
                continue;
            }
        }

        if(idsToRemove.size() == 0) {return;}

        for(int id: idsToRemove) {

            arrows.remove(id);

            plugin.getMessaging().debug(plugin.getLanguage().getMessage("debug-arrow-removed").replace("{class}", arrows.getClass().getName()));
            continue;
        }
    }

    /**
     * Disables an arrow in the registry
     * @param internalName the internal name of the arrow to disable
     */
    public void disableArrow(String internalName) {

        if(arrows == null || arrows.size() == 0) {return;}

        if(disabledArrowNames.contains(internalName)) {return;}

        disabledArrowNames.add(internalName);

        getArrow(internalName).onDisable();

        plugin.getMessaging().debug(plugin.getLanguage().getMessage("debug-arrow-disabled").replace("{class}", arrows.getClass().getName()));
    }

    /**
     * Enables an arrow in the registry
     * @param internalName the the internal name of the arrow to enable
     */
    public void enableArrow(String internalName) {

        if(arrows == null || arrows.size() == 0) {return;}

        if(!disabledArrowNames.contains(internalName)) {return;}

        disabledArrowNames.remove(internalName);

        getArrow(internalName).onEnable();

        plugin.getMessaging().debug(plugin.getLanguage().getMessage("debug-arrow-enabled").replace("{class}", arrows.getClass().getName()));
    }

    /**
     * Returns if the named arrow is enabled
     * @param internalName the internal name of the arrow to look for
     * @return if the named arrow is enabled
     */
    public boolean isEnabled(String internalName) {

        if(disabledArrowNames == null || disabledArrowNames.size() == 0) {return true;}

        for(String name: disabledArrowNames) {

            if(name.equalsIgnoreCase(internalName)) {

                return false;
            }
        }

        return true;
    }
}
