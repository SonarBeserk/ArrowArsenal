package me.sonarbeserk.arrowarsenal.commands;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
import me.sonarbeserk.arrowarsenal.clicklisteners.ArrowSelectListener;
import me.sonarbeserk.arrowarsenal.tracking.PlayerTracker;
import me.sonarbeserk.arrowarsenal.utils.PagedDoubleChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
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
public class MainCmd implements CommandExecutor {

    private ArrowArsenal plugin = null;

    public MainCmd(ArrowArsenal plugin) {

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 0) {

            if(sender instanceof Player) {

                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            } else {

                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            }
        }

        if(args.length > 0) {

            if(args[0].equalsIgnoreCase("help")) {

                if(sender instanceof Player) {

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                    return true;
                } else {

                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("reload")) {

                if(!sender.hasPermission("arrowarsenal.commands.reload")) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("no-permission"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("no-permission"));
                        return true;
                    }
                }

                plugin.getLanguage().reload();
                plugin.reloadConfig();

                if(sender instanceof Player) {

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("reloaded"));
                    return true;
                } else {

                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("reloaded"));
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("list")) {

                if(ArrowRegistry.getInstance().getArrowsMap().size() == 0) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-none-loaded"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-none-loaded"));
                        return true;
                    }
                }

                for(int id: ArrowRegistry.getInstance().getArrowsMap().keySet()) {

                    SArrow arrow = ArrowRegistry.getInstance().getArrowsMap().get(id);

                    if(arrow == null) {continue;}

                    if(sender instanceof Player) {
                        
                        if(arrow.canBuy()) {

                            plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-list").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + "").replace("{cost}", arrow.getCost() + ""));
                            continue;
                        } else {

                            plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-list-no-buy").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            continue;
                        }
                    } else {

                        if(arrow.canBuy()) {

                            plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-list").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + "").replace("{cost}", arrow.getCost() + ""));
                            continue;
                        } else {

                            plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-list-no-buy").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            continue;
                        }
                    }
                }

                return true;
            }

            if(args[0].equalsIgnoreCase("info")) {

                if(args.length == 1) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                        return true;
                    }
                }

                for(int id: ArrowRegistry.getInstance().getArrowsMap().keySet()) {

                    if(id == Integer.parseInt(args[1].replaceAll("[a-zA-Z]", ""))) {

                        SArrow arrow = ArrowRegistry.getInstance().getArrowsMap().get(id);
                        
                        if(sender instanceof Player) {

                            if(arrow.canBuy()) {

                                plugin.getMessaging().sendMessage(sender, false,  true, plugin.getLanguage().getMessage("arrow-info").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", (arrow.getAuthors() + "").replace("[", "").replace("]", "")).replace("{cost}", arrow.getCost() + "").replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            } else {

                                plugin.getMessaging().sendMessage(sender, false,  true, plugin.getLanguage().getMessage("arrow-info-no-buy").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", (arrow.getAuthors() + "").replace("[", "").replace("]", "")).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            }

                            if(arrow.getMainPermission() == null) {continue;}

                            plugin.getMessaging().sendMessage(sender, false, true, plugin.getLanguage().getMessage("arrow-info-main-permission").replace("{mainpermission}", arrow.getMainPermission()));

                            if(arrow.getExtraPermissions() == null || arrow.getExtraPermissions().size() == 0) {continue;}

                            for(String node: arrow.getExtraPermissions().keySet()){

                                if(arrow.getExtraPermissions().get(node) == null) {continue;}

                                plugin.getMessaging().sendMessage(sender, false, true, plugin.getLanguage().getMessage("arrow-info-permission").replace("{permission}", node).replace("{description}", arrow.getExtraPermissions().get(node)));
                                continue;
                            }

                            return true;
                        } else {

                            if(arrow.canBuy()) {

                                plugin.getMessaging().sendMessage(sender, false,  false, plugin.getLanguage().getMessage("arrow-info").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", (arrow.getAuthors() + "").replace("[", "").replace("]", "")).replace("{cost}", arrow.getCost() + "").replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            } else {

                                plugin.getMessaging().sendMessage(sender, false,  false, plugin.getLanguage().getMessage("arrow-info-no-buy").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", (arrow.getAuthors() + "").replace("[", "").replace("]", "")).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            }

                            if(arrow.getMainPermission() == null) {continue;}

                            plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-info-main-permission").replace("{mainpermission}", arrow.getMainPermission()));

                            if(arrow.getExtraPermissions() == null || arrow.getExtraPermissions().size() == 0) {continue;}
                            
                            for(String node: arrow.getExtraPermissions().keySet()){

                                if(arrow.getExtraPermissions().get(node) == null) {continue;}

                                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-info-permission").replace("{permission}", node).replace("{description}", arrow.getExtraPermissions().get(node)));
                                continue;
                            }

                            return true;
                        }
                    }
                }

                if(sender instanceof Player) {

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-not-found"));
                    return true;
                } else {

                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-not-found"));
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("disable")) {

                if(!sender.hasPermission("arrowarsenal.commands.disable")) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("no-permission"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("no-permission"));
                        return true;
                    }
                }

                if(args.length > 1) {

                    List<String> arrowNamesToDisable = new ArrayList<String>();

                    for(int id: ArrowRegistry.getInstance().getArrowsMap().keySet()) {

                        if(id == Integer.parseInt(args[1].replaceAll("[a-zA-Z]", ""))) {

                            SArrow arrow = ArrowRegistry.getInstance().getArrowsMap().get(id);

                            if(arrow == null) {continue;}

                            if(ArrowRegistry.getInstance().getDisabledArrowNames().contains(arrow.getInternalName())) {

                                if(sender instanceof Player) {

                                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-disabled").replace("{displayname}", arrow.getDisplayName()));
                                    return true;
                                } else {

                                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-disabled").replace("{displayname}", arrow.getDisplayName()));
                                    return true;
                                }
                            }

                            arrowNamesToDisable.add(arrow.getInternalName());

                            if(sender instanceof Player) {

                                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-disable").replace("{displayname}", arrow.getDisplayName()));
                                continue;
                            } else {

                                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-disable").replace("{displayname}", arrow.getDisplayName()));
                                continue;
                            }
                        }
                    }

                    for(String name: arrowNamesToDisable) {

                        ArrowRegistry.getInstance().disableArrow(name);
                        continue;
                    }

                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("enable")) {

                if(!sender.hasPermission("arrowarsenal.commands.enable")) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("no-permission"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("no-permission"));
                        return true;
                    }
                }

                if(args.length > 1) {

                    List<String> arrowNamesToEnable = new ArrayList<String>();

                    for(int id: ArrowRegistry.getInstance().getArrowsMap().keySet()) {

                        if(id == Integer.parseInt(args[1].replaceAll("[a-zA-Z]", ""))) {

                            SArrow arrow = ArrowRegistry.getInstance().getArrowsMap().get(id);

                            if(arrow == null) {continue;}

                            if(!ArrowRegistry.getInstance().getDisabledArrowNames().contains(arrow.getInternalName())) {

                                if(sender instanceof Player) {

                                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-enabled").replace("{displayname}", arrow.getDisplayName()));
                                    return true;
                                } else {

                                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-enabled").replace("{displayname}", arrow.getDisplayName()));
                                    return true;
                                }
                            }

                            arrowNamesToEnable.add(arrow.getInternalName());

                            if(sender instanceof Player) {

                                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-enable").replace("{displayname}", arrow.getDisplayName()));
                                continue;
                            } else {

                                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("arrow-enable").replace("{displayname}", arrow.getDisplayName()));
                                continue;
                            }
                        }
                    }

                    for(String name: arrowNamesToEnable) {

                        ArrowRegistry.getInstance().enableArrow(name);
                        continue;
                    }

                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("select")) {

                if(!sender.hasPermission("arrowarsenal.commands.select")) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("no-permission"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("no-permission"));
                        return true;
                    }
                }

                if(!(sender instanceof Player)) {

                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("command-player-required"));
                    return true;
                }

                if(ArrowRegistry.getInstance().getArrowsMap().size() == 0) {

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-none-loaded"));
                    return true;
                }

                Player player = (Player) sender;

                if(plugin.getConfig().getBoolean("settings.use-select-gui")) {

                    List<ItemStack> arrows = new ArrayList<ItemStack>();

                    for(SArrow sArrow: ArrowRegistry.getInstance().getArrowsMap().values()) {

                        ItemStack stack = new ItemStack(Material.ARROW);

                        ItemMeta meta = stack.getItemMeta();

                        List<String> lore = new ArrayList<String>();

                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', sArrow.getDisplayName()));

                        lore.add("Description: " + sArrow.getDescription());

                        if(sArrow.getCost() == 0) {

                            lore.add("Cost: None");
                        } else {

                            lore.add("Cost: " + sArrow.getCost());
                        }

                        lore.add("Name ID: " + sArrow.getInternalName());

                        meta.setLore(lore);

                        stack.setItemMeta(meta);

                        arrows.add(stack);
                    }

                    ItemStack nextPage = new ItemStack(Material.CACTUS);

                    ItemMeta meta = nextPage.getItemMeta();

                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("settings.next-page-name")));

                    nextPage.setItemMeta(meta);

                    ItemStack prevPage = new ItemStack(Material.NETHER_BRICK);

                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("settings.previous-page-name")));

                    prevPage.setItemMeta(meta);

                    PagedDoubleChestMenu menu = new PagedDoubleChestMenu(plugin, player, plugin.getConfig().getString("settings.arrow-select-menu-name"), arrows, nextPage, prevPage);

                    menu.setClickListener(new ArrowSelectListener(plugin, menu));

                    menu.openInventory();
                } else {

                    if(args.length == 1) {

                        if(PlayerTracker.getInstance().getCurrentArrowName(sender.getName()) == null) {

                            plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-none-selected"));
                            return true;
                        }

                        for(int id: ArrowRegistry.getInstance().getArrowsMap().keySet()) {

                            if(ArrowRegistry.getInstance().getArrowsMap().get(id).getInternalName().equalsIgnoreCase(PlayerTracker.getInstance().getCurrentArrowName(sender.getName()))) {

                                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-current").replace("{displayname}", ArrowRegistry.getInstance().getArrowsMap().get(id).getDisplayName()));
                                return true;
                            }
                        }

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-none-found"));
                        return true;
                    }

                    if(args.length > 1) {

                        if(ArrowRegistry.getInstance().getArrowsMap().size() == 0) {

                            plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-none-loaded"));
                            return true;
                        }

                        for(int id: ArrowRegistry.getInstance().getArrowsMap().keySet()) {

                            if(id == Integer.parseInt(args[1].replaceAll("[a-zA-Z]", ""))) {

                                SArrow arrow = ArrowRegistry.getInstance().getArrowsMap().get(id);

                                if(arrow == null) {continue;}

                                if(!ArrowRegistry.getInstance().getDisabledArrowNames().contains(arrow.getInternalName())) {

                                    PlayerTracker.getInstance().setCurrentArrow(sender.getName(), ArrowRegistry.getInstance().getArrowsMap().get(id));
                                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-current").replace("{displayname}", ArrowRegistry.getInstance().getArrowsMap().get(id).getDisplayName()));
                                    return true;
                                }
                            }
                        }
                    }

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("arrow-none-found"));
                    return true;
                }
            }

            if(sender instanceof Player) {

                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLanguage().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            } else {

                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLanguage().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            }
        }

        return false;
    }
}
