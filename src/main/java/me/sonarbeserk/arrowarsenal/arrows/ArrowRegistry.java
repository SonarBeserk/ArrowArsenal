package me.sonarbeserk.arrowarsenal.arrows;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import java.util.ArrayList;
import java.util.List;

public class ArrowRegistry {

    private static ArrowRegistry instance = null;

    private ArrowArsenal plugin = null;

    private List<SArrow> arrows = null;

    private List<String> disabledArrowNames = null;

    public ArrowRegistry(ArrowArsenal plugin) {

        instance = this;

        this.plugin = plugin;

        arrows = new ArrayList<SArrow>();
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
    public static void clearInstance() {

        instance = null;
    }

    /**
     * Returns a read-only list of registered arrows
     * @return a read-only list of registered arrows
     */
    public List<SArrow> getArrows() {

        List<SArrow> readOnlyList = new ArrayList<SArrow>();

        readOnlyList.addAll(arrows);

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

        arrows.add(arrow);

        plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrow-added").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", String.valueOf(arrow.getDescription())).replace("{authors}", arrow.getAuthors() + ""));
    }

    /**
     * Removes an arrow from the registry
     * @param internalName the internal name of the arrow to look for
     */
    public void removeArrow(String internalName) {

        if(arrows == null || arrows.size() == 0) {return;}

        List<SArrow> arrowsToRemove = new ArrayList<SArrow>();

        for(SArrow arrow: arrows) {

            if(arrow.getInternalName().equalsIgnoreCase(internalName)) {

                arrowsToRemove.add(arrow);

                plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrow-removed").replace("{internalname}", arrow.getInternalName()));
                continue;
            }
        }

        arrows.removeAll(arrowsToRemove);
    }

    /**
     * Disables an arrow in the registry
     * @param internalName the internal name of the arrow to disable
     */
    public void disableArrow(String internalName) {

        if(arrows == null || arrows.size() == 0) {return;}

        for(SArrow arrow: arrows) {

            if(arrow.getInternalName().equalsIgnoreCase(internalName)) {

                disabledArrowNames.add(arrow.getInternalName());

                plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrow-disabled").replace("{internalname}", arrow.getInternalName()));
                continue;
            }
        }
    }

    /**
     * Enables an arrow in the registry
     * @param internalName the the internal name of the arrow to enable
     */
    public void enableArrow(String internalName) {

        if(arrows == null || arrows.size() == 0 || disabledArrowNames == null || disabledArrowNames.size() == 0) {return;}

        List<String> namesToRemove = new ArrayList<String>();

        for(String name: disabledArrowNames) {

            if(name.equalsIgnoreCase(internalName)) {

                namesToRemove.add(internalName);

                plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrow-enabled").replace("{internalname}", name));
                continue;
            }
        }

        disabledArrowNames.removeAll(namesToRemove);
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
    }
}
