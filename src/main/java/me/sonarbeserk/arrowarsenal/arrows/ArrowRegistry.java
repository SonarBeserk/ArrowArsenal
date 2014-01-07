package me.sonarbeserk.arrowarsenal.arrows;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrowRegistry {

    private static ArrowRegistry instance = null;

    private ArrowArsenal plugin = null;

    private Map<Integer, SArrow> arrows = null;

    private List<String> disabledArrowNames = null;

    public ArrowRegistry(ArrowArsenal plugin) {

        instance = this;

        this.plugin = plugin;

        arrows = (Map<Integer, SArrow>) plugin.getData().get("arrow-list");

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
     * Clears the instance of the registry, only used for cleanup purposes
     */
    public void clearInstance() {

        instance = null;
    }

    /**
     * Returns a read-only list of registered arrows
     * @return a read-only list of registered arrows
     */
    public Map<Integer, SArrow> getArrows() {

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

            plugin.getLogger().severe(plugin.getLocale().getMessage("severe-no-displayname").replace("{class}", arrow.getClass().getName()));
            return;

        } else if(arrow.getInternalName() == null) {

            plugin.getLogger().severe(plugin.getLocale().getMessage("severe-no-internalname").replace("{class}", arrow.getClass().getName()));
            return;
        }

        if(arrows.size() == 0) {

            arrows.put(1, arrow);

            plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrowregistry-added").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription()).replace("{authors}", arrow.getAuthors() + ""));

            return;
        }

        arrows.put(arrows.size() + 1, arrow);

        plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrowregistry-added").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription()).replace("{authors}", arrow.getAuthors() + ""));

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

            plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrowregistry-removed").replace("{class}", arrows.getClass().getName()));
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

        plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrowregistry-disabled").replace("{class}", arrows.getClass().getName()));
    }

    /**
     * Enables an arrow in the registry
     * @param internalName the the internal name of the arrow to enable
     */
    public void enableArrow(String internalName) {

        if(arrows == null || arrows.size() == 0) {return;}

        if(!disabledArrowNames.contains(internalName)) {return;}

        disabledArrowNames.remove(internalName);

        plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrowregistry-enabled").replace("{class}", arrows.getClass().getName()));
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
