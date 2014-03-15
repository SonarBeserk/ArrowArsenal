package me.sonarbeserk.arrowarsenal.utils;

import me.sonarbeserk.arrowarsenal.clicklisteners.ClickListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
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
/**
 * Inspired by the menus in GuestCraft http://dev.bukkit.org/bukkit-plugins/guestcraft/
 */
public class PagedDoubleChestMenu implements Listener {

    private JavaPlugin plugin = null;

    private Player player = null;

    private String name = null;

    private List<ItemStack> items = null;

    private ItemStack nextPage = null;
    private ItemStack prevPage = null;

    private Map<Integer, Inventory> pageMap = null;

    private int currentPage = 0;

    private ClickListener cListener = null;

    /**
     * Creates a paged double chest menu
     * @param plugin the plugin creating this inventory
     * @param player the player being shown the menu
     * @param name the name to display in the inventory
     * @param items the items to add
     * @param nextPage the itemstack to use for the next page button
     * @param prevPage the itemstack to use for the previous page button
     */
    public PagedDoubleChestMenu(JavaPlugin plugin, Player player, String name, List<ItemStack> items, ItemStack nextPage, ItemStack prevPage) {

        this.plugin = plugin;

        this.player = player;

        this.name = name;

        this.items = items;

        this.nextPage = nextPage;
        this.prevPage = prevPage;

        createPages();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    //Inventorys must be multiples of 9, large chests have 54 slots
    private void createPages() {

        pageMap = new HashMap<Integer, Inventory>();

        if(items.size() == 0) {return;}

        if(items.size() <= 54) {

            Inventory inventory = plugin.getServer().createInventory(null, 54, name);

            if(inventory == null) {return;}

            int invCounter = 0;

            int parseCounter = 0;

            if(items.size() == 1) {

                inventory.setItem(0, items.get(0));
            }

            while(items.size() > 1 && invCounter <= 53 && parseCounter < items.size()) {

                inventory.setItem(invCounter, items.get(parseCounter));

                invCounter++;
                parseCounter++;
            }

            if(pageMap.size() == 0) {

                pageMap.put(0, inventory);
                return;
            }
        }

        if(nextPage == null || prevPage == null) {return;}

        if(items.size() > 54) {

            int invCounter = 0;

            int parseCounter = 0;

            int pageCounter = 0;

            Inventory currentInventory = null;

            while(parseCounter < items.size()) {

                if(currentInventory == null) {

                    currentInventory = plugin.getServer().createInventory(null, 54, name);
                }

                if(pageMap.size() > 0) {

                    currentInventory.setItem(0, prevPage);
                    invCounter++;
                }

                if(invCounter < 52) {

                    currentInventory.setItem(invCounter, items.get(parseCounter));

                    invCounter++;
                    parseCounter++;
                }

                if(invCounter == 52) {

                    if(pageMap.size() == 0) {

                        currentInventory.setItem(invCounter, items.get(parseCounter));
                        parseCounter++;
                        invCounter++;

                        currentInventory.setItem(invCounter, nextPage);
                        invCounter++;
                    }

                    if(pageMap.size() > 0) {

                        currentInventory.setItem(invCounter, nextPage);
                        invCounter++;
                    }
                }

                if(invCounter == 54) {

                    pageMap.put(pageCounter, currentInventory);
                    currentInventory = null;
                    invCounter = 0;
                    pageCounter++;
                }

                if(parseCounter + 1 == items.size()) {

                    pageMap.put(pageCounter, currentInventory);
                    currentInventory = null;
                    invCounter = 0;
                    pageCounter++;
                }
            }
        }
    }

    /**
     * Opens the inventory for the menu's player
     */
    public void openInventory() {

        if(pageMap == null || pageMap.size() == 0 || player == null) {return;}

        if(pageMap.size() == 1) {

            player.openInventory(pageMap.get(0));
            return;
        }

        if(pageMap.size() > 1) {

            player.openInventory(pageMap.get(currentPage));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void pickupListener(InventoryClickEvent e) {

        Inventory inventory = null;

        for(Inventory cInventory: pageMap.values()) {

            if(cInventory.getTitle() == null) {continue;}

            if(cInventory.getTitle().equalsIgnoreCase(e.getInventory().getTitle()) && cInventory.getViewers().equals(e.getInventory().getViewers())) {

                inventory = cInventory;
            }
        }

        if(inventory == null) {return;}

        if(!(e.getAction() == InventoryAction.PICKUP_ALL) && !(e.getAction() == InventoryAction.PICKUP_HALF) && !(e.getAction() == InventoryAction.PICKUP_ONE) && !(e.getAction() == InventoryAction.SWAP_WITH_CURSOR)) {return;}

        for(ItemStack stack: inventory.getContents()) {

            if(stack.equals(e.getCurrentItem())) {

                if(!(cListener == null)) {

                    cListener.click(e);
                }

                e.setCancelled(true);
                return;
            }
        }
    }

    /**
     * Sets the click listener to the provided listener
     * @param cListener the click listener to use
     */
    public void setClickListener(ClickListener cListener) {

        this.cListener = cListener;
    }

    /**
     * Changes to the next page in the inventory.
     */
    public void nextPage() {

        if(currentPage + 1 > pageMap.size()) {return;}

        if(currentPage > pageMap.size()) {

            currentPage = pageMap.size();
        }

        currentPage++;

        player.closeInventory();
        openInventory();
    }

    /**
     * Changes to the previous page in the inventory.
     */
    public void prevPage() {

        if(currentPage < 0) {

            currentPage = 0;
        }

        if(currentPage == 0) {return;}

        currentPage--;

        player.closeInventory();
        openInventory();
    }

    /**
     * Returns the current page the menu is on
     * @return the current page the menu is on
     */
    public int getCurrentPage() {

        return currentPage;
    }

    /**
     * Returns a list of the inventorys of the pages
     * @return a list of the inventorys of the pages
     */
    public List<Inventory> getInventorys() {

        List<Inventory> inventorys = new ArrayList<Inventory>();

        inventorys.addAll(pageMap.values());

        return inventorys;
    }
}
