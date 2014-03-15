package me.sonarbeserk.arrowarsenal.listeners;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
import me.sonarbeserk.arrowarsenal.enums.ArrowState;
import me.sonarbeserk.arrowarsenal.tracking.PlayerTracker;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;

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
public class ArrowListener implements Listener {

    private ArrowArsenal plugin = null;

    public ArrowListener(ArrowArsenal plugin) {

        this.plugin = plugin;
    }

    @EventHandler(ignoreCancelled = true)
    public void launch(ProjectileLaunchEvent e) {

        if(e.getEntity() == null || !(e.getEntity().getType() == EntityType.ARROW)) {return;}

        Arrow arrow = (Arrow) e.getEntity();

        if(arrow.getShooter() == null || !(arrow.getShooter() instanceof Player)) {return;}

        if(arrow.hasMetadata("launched")) {return;}

        Player shooter = (Player) arrow.getShooter();

        if(shooter == null || PlayerTracker.getInstance().getCurrentArrowName(shooter.getName()) == null) {return;}

        SArrow sArrow = ArrowRegistry.getInstance().getArrow(PlayerTracker.getInstance().getCurrentArrowName(shooter.getName()));

        if(sArrow == null) {return;}

        if(sArrow.getMainPermission() == null) {return;}

        if(!shooter.hasPermission(sArrow.getMainPermission()) && !arrow.hasMetadata("warned")) {

            plugin.getMessaging().sendMessage(shooter, true, true, plugin.getLanguage().getMessage("no-permission"));
            arrow.setMetadata("warned", new FixedMetadataValue(plugin, "true"));
            return;
        }

        arrow.setMetadata("shooter", new FixedMetadataValue(plugin, shooter.getName()));

        sArrow.launch(e);

        if(sArrow.getState() == null || sArrow.getState() == ArrowState.FAILED || sArrow.getState() == ArrowState.PROGRESSING) {return;}

        if(sArrow.getState() == ArrowState.SUCCEEDED && sArrow.getCost() > 0 && ArrowArsenal.economy != null) {

            if(!arrow.hasMetadata("charged")) {

                if(!ArrowArsenal.economy.has(shooter.getName(), sArrow.getCost())) {

                    plugin.getMessaging().sendMessage(shooter, true, true, plugin.getLanguage().getMessage("arrow-can-not-afford"));
                    return;
                }

                ArrowArsenal.economy.withdrawPlayer(shooter.getName(), sArrow.getCost());
                arrow.setMetadata("charged", new FixedMetadataValue(plugin, "charged"));
            }
        }

        arrow.setMetadata("launched", new FixedMetadataValue(plugin, "true"));
    }

    @EventHandler(ignoreCancelled = true)
    public void hit(ProjectileHitEvent e) {

        if(e.getEntity() == null || !(e.getEntity().getType() == EntityType.ARROW)) {return;}

        Arrow arrow = (Arrow) e.getEntity();

        if(arrow.getShooter() == null || !(arrow.getShooter() instanceof Player)) {return;}

        if(arrow.hasMetadata("hit")) {return;}

        Player shooter = (Player) arrow.getShooter();

        if(shooter == null || PlayerTracker.getInstance().getCurrentArrowName(shooter.getName()) == null) {return;}

        SArrow sArrow = ArrowRegistry.getInstance().getArrow(PlayerTracker.getInstance().getCurrentArrowName(shooter.getName()));

        if(sArrow == null) {return;}

        if(sArrow.getMainPermission() == null) {return;}

        if(!shooter.hasPermission(sArrow.getMainPermission()) && !arrow.hasMetadata("warned")) {

            plugin.getMessaging().sendMessage(shooter, true, true, plugin.getLanguage().getMessage("no-permission"));
            arrow.setMetadata("warned", new FixedMetadataValue(plugin, "true"));
            return;
        }

        arrow.setMetadata("shooter", new FixedMetadataValue(plugin, shooter.getName()));

        sArrow.hit(e);

        if(sArrow.getState() == null || sArrow.getState() == ArrowState.FAILED || sArrow.getState() == ArrowState.PROGRESSING) {return;}

        if(sArrow.getState() == ArrowState.SUCCEEDED && sArrow.getCost() > 0 && ArrowArsenal.economy != null) {

            if(!arrow.hasMetadata("charged")) {

                if(!ArrowArsenal.economy.has(shooter.getName(), sArrow.getCost())) {

                    plugin.getMessaging().sendMessage(shooter, true, true, plugin.getLanguage().getMessage("arrow-can-not-afford"));
                    return;
                }

                ArrowArsenal.economy.withdrawPlayer(shooter.getName(), sArrow.getCost());
                arrow.setMetadata("charged", new FixedMetadataValue(plugin, "charged"));
            }
        }

        arrow.setMetadata("hit", new FixedMetadataValue(plugin, "true"));
    }

    @EventHandler(ignoreCancelled = true)
    public void hitEntity(EntityDamageByEntityEvent e) {

        if(e.getEntity() == null || e.getDamager() == null || !(e.getDamager().getType() == EntityType.ARROW)) {return;}

        Arrow arrow = (Arrow) e.getDamager();

        if(arrow.getShooter() == null || !(arrow.getShooter() instanceof Player)) {return;}

        if(arrow.hasMetadata("hit")) {return;}

        Player shooter = (Player) arrow.getShooter();

        if(shooter == null || PlayerTracker.getInstance().getCurrentArrowName(shooter.getName()) == null) {return;}

        if(shooter.equals(e.getEntity())) {return;}

        SArrow sArrow = ArrowRegistry.getInstance().getArrow(PlayerTracker.getInstance().getCurrentArrowName(shooter.getName()));

        if(sArrow == null) {return;}

        if(sArrow.getMainPermission() == null) {return;}

        if(!shooter.hasPermission(sArrow.getMainPermission()) && !arrow.hasMetadata("warned")) {

            plugin.getMessaging().sendMessage(shooter, true, true, plugin.getLanguage().getMessage("no-permission"));
            arrow.setMetadata("warned", new FixedMetadataValue(plugin, "true"));
            return;
        }

        arrow.setMetadata("shooter", new FixedMetadataValue(plugin, shooter.getName()));

        sArrow.hitEntity(e);

        if(sArrow.getState() == null || sArrow.getState() == ArrowState.FAILED || sArrow.getState() == ArrowState.PROGRESSING) {return;}

        if(sArrow.getState() == ArrowState.SUCCEEDED && sArrow.getCost() > 0 && ArrowArsenal.economy != null) {

            if(!arrow.hasMetadata("charged")) {

                if(!ArrowArsenal.economy.has(shooter.getName(), sArrow.getCost())) {

                    plugin.getMessaging().sendMessage(shooter, true, true, plugin.getLanguage().getMessage("arrow-can-not-afford"));
                    return;
                }

                ArrowArsenal.economy.withdrawPlayer(shooter.getName(), sArrow.getCost());
                arrow.setMetadata("charged", new FixedMetadataValue(plugin, "charged"));
            }
        }

        arrow.setMetadata("hit", new FixedMetadataValue(plugin, "true"));
    }
}
