package me.sonarbeserk.arrowarsenal.clicklisteners;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
import me.sonarbeserk.arrowarsenal.tracking.PlayerTracker;
import me.sonarbeserk.arrowarsenal.utils.PagedDoubleChestMenu;
import org.bukkit.event.inventory.InventoryClickEvent;
import java.util.List;

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

                    plugin.getMessaging().sendMessage(e.getWhoClicked().getName(), true, true, plugin.getLanguage().getMessage("arrow-current").replace("{displayname}", arrow.getDisplayName()));
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

                        plugin.getMessaging().sendMessage(e.getWhoClicked().getName(), true, true, plugin.getLanguage().getMessage("arrow-current").replace("{displayname}", arrow.getDisplayName()));
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

                        plugin.getMessaging().sendMessage(e.getWhoClicked().getName(), true, true, plugin.getLanguage().getMessage("arrow-current").replace("{displayname}", arrow.getDisplayName()));
                    }
                }
            }
        }
    }
}