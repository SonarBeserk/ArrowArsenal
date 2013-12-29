package me.sonarbeserk.arrowarsenal.utils;

import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.List;

public abstract class SArrow {

    /**
     * Returns the user friendly name of the arrow
     * @return the user friendly name of the arrow
     */
    public abstract String getName();

    /**
     * Returns the internal name of the arrow, the name to use when saving data
     * @return the internal name of the arrow, the name to use when saving data
     */
    public abstract String getInternalName();

    /**
     * Returns the description of the arrow
     * @return the description of the arrow
     */
    public abstract String getDescription();

    /**
     * Returns the name of the author(s) that made the arrow
     * @return the name of the author(s) that made the arrow
     */
    public abstract List<String> getAuthors();

    /**
     * Called when an arrow is launched
     * @param event the event where the arrow was launched
     */
    public abstract void launch(ProjectileLaunchEvent event);

    /**
     * Called when an arrow hits
     * @param event the event where the arrow hit
     */
    public abstract void hit(ProjectileHitEvent event);
}
