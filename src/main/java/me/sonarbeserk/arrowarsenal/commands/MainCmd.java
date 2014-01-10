package me.sonarbeserk.arrowarsenal.commands;

import me.sonarbeserk.arrowarsenal.ArrowArsenal;
import me.sonarbeserk.arrowarsenal.arrows.ArrowRegistry;
import me.sonarbeserk.arrowarsenal.arrows.SArrow;
import me.sonarbeserk.arrowarsenal.tracking.PlayerTracker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class MainCmd implements CommandExecutor {

    private ArrowArsenal plugin = null;

    public MainCmd(ArrowArsenal plugin) {

        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(args.length == 0) {

            if(sender instanceof Player) {

                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal"));
                return true;
            } else {

                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("usage-arrowarsenal"));
                return true;
            }
        }

        if(args.length > 0) {

            if(args[0].equalsIgnoreCase("help")) {

                if(sender instanceof Player) {

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal"));
                    return true;
                } else {

                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("usage-arrowarsenal"));
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("reload")) {

                if(!sender.hasPermission("arrowarsenal.commands.reload")) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    }
                }

                plugin.getLocale().reload();
                plugin.reloadConfig();

                if(sender instanceof Player) {

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("reloaded"));
                    return true;
                } else {

                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("reloaded"));
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("list")) {

                if(ArrowRegistry.getInstance().getArrows().size() == 0) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("arrow-none-loaded"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("arrow-none-loaded"));
                        return true;
                    }
                }

                for(int id: ArrowRegistry.getInstance().getArrows().keySet()) {

                    SArrow arrow = ArrowRegistry.getInstance().getArrows().get(id);

                    if(arrow == null) {continue;}

                    if(sender instanceof Player) {
                        
                        if(arrow.canBuy()) {

                            plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("arrow-list").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + "").replace("{cost}", arrow.getCost() + ""));
                            continue;
                        } else {

                            plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("arrow-list-no-buy").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            continue;
                        }
                    } else {

                        if(arrow.canBuy()) {

                            plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("arrow-list").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + "").replace("{cost}", arrow.getCost() + ""));
                            continue;
                        } else {

                            plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("arrow-list-no-buy").replace("{id}", id + "").replace("{displayname}", arrow.getDisplayName()).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            continue;
                        }
                    }
                }

                return true;
            }

            if(args[0].equalsIgnoreCase("info")) {

                if(args.length == 1) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                        return true;
                    }
                }

                for(int id: ArrowRegistry.getInstance().getArrows().keySet()) {

                    if(id == Integer.parseInt(args[1].replaceAll("[a-zA-Z]", ""))) {

                        SArrow arrow = ArrowRegistry.getInstance().getArrows().get(id);
                        
                        if(sender instanceof Player) {

                            if(arrow.canBuy()) {

                                plugin.getMessaging().sendMessage(sender, true,  true, plugin.getLocale().getMessage("arrow-info").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", (arrow.getAuthors() + "").replace("[", "").replace("]", "")).replace("{cost}", arrow.getCost() + "").replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            } else {

                                plugin.getMessaging().sendMessage(sender, true,  true, plugin.getLocale().getMessage("arrow-info-no-buy").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", (arrow.getAuthors() + "").replace("[", "").replace("]", "")).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            }

                            if(arrow.getPermissions() == null || arrow.getPermissions().size() == 0) {continue;}

                            for(String node: arrow.getPermissions().keySet()){

                                if(arrow.getPermissions().get(node) == null) {continue;}

                                plugin.getMessaging().sendMessage(sender, false, true, plugin.getLocale().getMessage("arrow-info-permission").replace("{permission}", node).replace("{description}", arrow.getPermissions().get(node)));
                                continue;
                            }

                            return true;
                        } else {

                            if(arrow.canBuy()) {

                                plugin.getMessaging().sendMessage(sender, false,  false, plugin.getLocale().getMessage("arrow-info").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", (arrow.getAuthors() + "").replace("[", "").replace("]", "")).replace("{cost}", arrow.getCost() + "").replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            } else {

                                plugin.getMessaging().sendMessage(sender, false,  false, plugin.getLocale().getMessage("arrow-info-no-buy").replace("{displayname}", arrow.getDisplayName()).replace("{internalname}", arrow.getInternalName()).replace("{description}", arrow.getDescription() + "").replace("{authors}", (arrow.getAuthors() + "").replace("[", "").replace("]", "")).replace("{enabled}", ArrowRegistry.getInstance().isEnabled(arrow.getInternalName()) + ""));
                            }

                            if(arrow.getPermissions() == null || arrow.getPermissions().size() == 0) {continue;}
                            
                            for(String node: arrow.getPermissions().keySet()){

                                if(arrow.getPermissions().get(node) == null) {continue;}

                                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("arrow-info-permission").replace("{permission}", node).replace("{description}", arrow.getPermissions().get(node)));
                                continue;
                            }

                            return true;
                        }
                    }
                }

                if(sender instanceof Player) {

                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("arrow-not-found"));
                    return true;
                } else {

                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("arrow-not-found"));
                    return true;
                }
            }

            if(args[0].equalsIgnoreCase("disable")) {

                if(!sender.hasPermission("arrowarsenal.commands.disable")) {

                    if(sender instanceof Player) {

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    }
                }

                if(args.length > 1) {

                    List<String> arrowNamesToDisable = new ArrayList<String>();

                    for(int id: ArrowRegistry.getInstance().getArrows().keySet()) {

                        if(id == Integer.parseInt(args[1].replaceAll("[a-zA-Z]", ""))) {

                            SArrow arrow = ArrowRegistry.getInstance().getArrows().get(id);

                            if(arrow == null) {continue;}

                            if(ArrowRegistry.getInstance().getDisabledArrowNames().contains(arrow.getInternalName())) {

                                if(sender instanceof Player) {

                                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("arrow-disabled").replace("{displayname}", arrow.getDisplayName()));
                                    return true;
                                } else {

                                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("arrow-disabled").replace("{displayname}", arrow.getDisplayName()));
                                    return true;
                                }
                            }

                            arrowNamesToDisable.add(arrow.getInternalName());

                            if(sender instanceof Player) {

                                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("arrow-disable").replace("{displayname}", arrow.getDisplayName()));
                                continue;
                            } else {

                                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("arrow-disable").replace("{displayname}", arrow.getDisplayName()));
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

                        plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    } else {

                        plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("no-permission"));
                        return true;
                    }
                }

                if(args.length > 1) {

                    List<String> arrowNamesToEnable = new ArrayList<String>();

                    for(int id: ArrowRegistry.getInstance().getArrows().keySet()) {

                        if(id == Integer.parseInt(args[1].replaceAll("[a-zA-Z]", ""))) {

                            SArrow arrow = ArrowRegistry.getInstance().getArrows().get(id);

                            if(arrow == null) {continue;}

                            if(!ArrowRegistry.getInstance().getDisabledArrowNames().contains(arrow.getInternalName())) {

                                if(sender instanceof Player) {

                                    plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("arrow-enabled").replace("{displayname}", arrow.getDisplayName()));
                                    return true;
                                } else {

                                    plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("arrow-enabled").replace("{displayname}", arrow.getDisplayName()));
                                    return true;
                                }
                            }

                            arrowNamesToEnable.add(arrow.getInternalName());

                            if(sender instanceof Player) {

                                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("arrow-enable").replace("{displayname}", arrow.getDisplayName()));
                                continue;
                            } else {

                                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("arrow-enable").replace("{displayname}", arrow.getDisplayName()));
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

            if(sender instanceof Player) {

                plugin.getMessaging().sendMessage(sender, true, true, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            } else {

                plugin.getMessaging().sendMessage(sender, false, false, plugin.getLocale().getMessage("usage-arrowarsenal").replace("{name}", plugin.getDescription().getName()));
                return true;
            }
        }

        return false;
    }
}
