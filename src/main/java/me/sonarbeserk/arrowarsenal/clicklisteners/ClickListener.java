package me.sonarbeserk.arrowarsenal.clicklisteners;

import org.bukkit.event.inventory.InventoryClickEvent;

public abstract class ClickListener {

    /**
     * Called when a menu item is clicked
     * @param e the event the click occured in
     */
    public abstract void click(InventoryClickEvent e);
}
