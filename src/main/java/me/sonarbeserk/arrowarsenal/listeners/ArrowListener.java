package me.sonarbeserk.arrowarsenal.listeners;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
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

        if(arrow == null) {return;}

        sArrow.launch(e);

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

        if(arrow == null) {return;}

        sArrow.hit(e);

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

        SArrow sArrow = ArrowRegistry.getInstance().getArrow(PlayerTracker.getInstance().getCurrentArrowName(shooter.getName()));

        if(arrow == null) {return;}

        sArrow.hitEntity(e);

        arrow.setMetadata("hit", new FixedMetadataValue(plugin, "true"));
    }
}
