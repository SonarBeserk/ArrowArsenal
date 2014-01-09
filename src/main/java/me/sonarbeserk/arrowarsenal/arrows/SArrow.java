package me.sonarbeserk.arrowarsenal.arrows;

import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import java.util.List;
import java.util.Map;

public abstract class SArrow {

    /**
     * Returns the user friendly name of the arrow
     * @return the user friendly name of the arrow
     */
    public abstract String getDisplayName();

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
     * Returns a map of the permissions, the order being permission value then reason for the permission
     * @return a map of the permissions, the order being permission value then reason for the permission
     */
    public abstract Map<String, String> getPermissions();

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

    /**
     * Called when an arrow hits an entity
     * @param event the event where the arrow hit an entity
     */
    public abstract void hitEntity(EntityDamageByEntityEvent event);

    /**
     * Called when the arrow is enabled
     */
    public abstract void onEnable();

    /**
     * Called when the arrow is disabled
     */
    public abstract void onDisable();
}
