package me.sonarbeserk.arrowarsenal.arrows;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;

import java.util.ArrayList;
import java.util.List;

public class ArrowRegistry {

    private ArrowRegistry instance = null;

    private ArrowArsenal plugin = null;

    private List<SArrow> arrows = null;

    public ArrowRegistry(ArrowArsenal plugin) {

        instance = this;

        this.plugin = plugin;

        arrows = new ArrayList<SArrow>();
    }

    /**
     * Returns the current instance of the ArrowRegistry
     * @return the current instance of the ArrowRegistry
     */
    public ArrowRegistry getInstance() {

        return instance;
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

        if(arrow.getDisplayName() == null) {

            plugin.getLogger().severe(plugin.getLocale().getMessage("severe-no-displayname").replace("{class}", arrow.getClass().getName()));
            return;
        } else if(arrow.getInternalName() == null) {

            plugin.getLogger().severe(plugin.getLocale().getMessage("severe-no-internalname").replace("{class}", arrow.getClass().getName()));
            return;
        }

        arrows.add(arrow);

        plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrowregistry-added").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", String.valueOf(arrow.getDescription())).replace("{authors}", arrow.getAuthors() + ""));
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

                plugin.getMessaging().debug(plugin.getLocale().getMessage("debug-arrowregistry-removed").replace("{internalname}", arrow.getInternalName()));
                continue;
            }
        }

        arrows.removeAll(arrowsToRemove);
    }
}
