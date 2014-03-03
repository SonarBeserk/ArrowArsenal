package me.sonarbeserk.arrowarsenal.clicklisteners;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
import me.sonarbeserk.arrowarsenal.tracking.PlayerTracker;
import me.sonarbeserk.arrowarsenal.utils.PagedDoubleChestMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.List;

public class ArrowSelectListener extends ClickListener {

    private ArrowArsenal plugin = null;

    private PagedDoubleChestMenu menu = null;

    public ArrowSelectListener(ArrowArsenal plugin, PagedDoubleChestMenu menu) {

        this.plugin = plugin;

        this.menu = menu;
    }

    public void click(InventoryClickEvent e) {

        if(menu.getCurrentPage() == 0 && menu.getInventorys().size() == 1) {

            if(e.getCurrentItem().getItemMeta().getLore() == null) {return;}

            List<String> lore = e.getCurrentItem().getItemMeta().getLore();

            for(String loreString: lore) {

                if(loreString.startsWith("Name ID")) {

                    String name = loreString.replace("Name ID", "");

                    SArrow arrow = ArrowRegistry.getInstance().getArrow(name);

                    if(arrow == null) {return;}

                    PlayerTracker.getInstance().setCurrentArrow(e.getWhoClicked().getName(), arrow);

                    plugin.getMessaging().sendMessage(e.getWhoClicked().getName(), true, true, plugin.getLocale().getMessage("arrow-current").replace("{displayname}", arrow.getDisplayName()));
                }
            }
        }

        if(menu.getCurrentPage() == 0 && menu.getInventorys().size() > 1) {

            // next button
            if(e.getSlot() == 53) {

                menu.nextPage();
                return;
            }

            if(e.getSlot() < 53) {

                if(e.getCurrentItem().getItemMeta().getLore() == null) {return;}

                List<String> lore = e.getCurrentItem().getItemMeta().getLore();

                for(String loreString: lore) {

                    if(loreString.startsWith("Name ID")) {

                        String name = loreString.replace("Name ID", "");

                        SArrow arrow = ArrowRegistry.getInstance().getArrow(name);

                        if(arrow == null) {return;}

                        PlayerTracker.getInstance().setCurrentArrow(e.getWhoClicked().getName(), arrow);

                        plugin.getMessaging().sendMessage(e.getWhoClicked().getName(), true, true, plugin.getLocale().getMessage("arrow-current").replace("{displayname}", arrow.getDisplayName()));
                    }
                }
            }
        }

        if(menu.getCurrentPage() > 0 && menu.getInventorys().size() > 1) {

            //previous button
            if(e.getSlot() == 0) {

                menu.prevPage();
                return;
            }

            //next button
            if(e.getSlot() == 53) {

                menu.nextPage();
                return;
            }

            if(e.getSlot() < 53) {

                if(e.getCurrentItem().getItemMeta().getLore() == null) {return;}

                List<String> lore = e.getCurrentItem().getItemMeta().getLore();

                for(String loreString: lore) {

                    if(loreString.startsWith("Name ID")) {

                        String name = loreString.replace("Name ID", "");

                        SArrow arrow = ArrowRegistry.getInstance().getArrow(name);

                        if(arrow == null) {return;}

                        PlayerTracker.getInstance().setCurrentArrow(e.getWhoClicked().getName(), arrow);

                        plugin.getMessaging().sendMessage(e.getWhoClicked().getName(), true, true, plugin.getLocale().getMessage("arrow-current").replace("{displayname}", arrow.getDisplayName()));
                    }
                }
            }
        }
    }
}