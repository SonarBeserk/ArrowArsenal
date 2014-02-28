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

        if(items.size() <= 54) {

            Inventory inventory = plugin.getServer().createInventory(null, 54, name);

            if(inventory == null) {return;}

            for(int i = 0; i < items.size(); i++) {

                inventory.setItem(i, items.get(i));
            }

            if(pageMap.size() == 0) {

                pageMap.put(0, inventory);
                return;
            }

            if(pageMap.size() > 0) {

                pageMap.put(pageMap.size() + 1, inventory);
                return;
            }
        }

        if(nextPage == null || prevPage == null) {return;}

        if(items.size() > 54) {

            int invCounter = 0;

            int parseCounter = 0;

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
                    continue;
                }

                if(invCounter == 52) {

                    if(pageMap.size() == 0) {

                        currentInventory.setItem(invCounter, items.get(parseCounter));
                        parseCounter++;
                        invCounter++;

                        currentInventory.setItem(invCounter, nextPage);
                        invCounter++;
                        continue;
                    }

                    if(pageMap.size() > 0) {

                        currentInventory.setItem(invCounter, nextPage);
                        invCounter++;
                        continue;
                    }
                }

                if(invCounter == 54) {

                    if(pageMap.size() == 0) {

                        pageMap.put(0, currentInventory);
                        currentInventory = null;
                        invCounter = 0;
                        continue;
                    } else {

                        pageMap.put(pageMap.size() + 1, currentInventory);
                        currentInventory = null;
                        invCounter = 0;
                        continue;
                    }
                }
            }
        }
    }

    /**
     * Opens the inventory for the player
     * @param player the player to open the inventory for
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
     * Changes to the next page in the inventory. Open the inventory again to show the new page
     */
    public void nextPage() {

        if(currentPage + 1 > pageMap.size()) {return;}

        if(currentPage > pageMap.size()) {

            currentPage = pageMap.size();
        }

        currentPage++;

        openInventory();
    }

    /**
     * Changes to the previous page in the inventory. Open the inventory again to show the new page
     */
    public void prevPage() {

        if(currentPage < 0) {

            currentPage = 0;
        }

        if(currentPage == 0) {return;}

        currentPage--;

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
