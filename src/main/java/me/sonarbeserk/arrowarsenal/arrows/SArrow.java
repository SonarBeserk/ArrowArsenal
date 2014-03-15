package me.sonarbeserk.arrowarsenal.arrows;

import me.sonarbeserk.arrowarsenal.enums.ArrowState;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
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
     * Returns the base permission required for the arrow
     * @return the base permission required for the arrow
     */
    public abstract String getMainPermission();

    /**
     * Returns a map of the extra permissions, the order being the permission node then the reason for the permission
     * @return a map of the extra permissions, the order being the permission node then the reason for the permission
     */
    public abstract Map<String, String> getExtraPermissions();

    /**
     * Returns if the arrow is possible to be bought with currency
     * @return if the arrow is possible to be bought with currency
     */
    public abstract boolean canBuy();

    /**
     * Returns the cost to buy the arrow if it can be bought
     * @return the cost to buy the arrow if it can be bought
     */
    public abstract int getCost();

    /**
     * Returns the current arrow state
     * @return the current arrow state
     */
    public abstract ArrowState getState();

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
