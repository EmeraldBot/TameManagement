package com.etriacraft.tamemanagement;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;

public class Commands {

	TameManagement plugin;

	public Commands(TameManagement instance) {
		this.plugin = instance;
		init();
	}

	private void init() {
		PluginCommand tame = plugin.getCommand("tame");
		CommandExecutor exe;

		exe = new CommandExecutor() {
			@Override
			public boolean onCommand(CommandSender s, Command c, String label, String[] args) {
				if (args.length < 1) {
					s.sendMessage("-----§6TameManagement Commands§f-----");
					s.sendMessage("§3/tame setowner [Player]§f - Sets Owner of tamed animal.");
					s.sendMessage("§3/tame release§f - Releases a tamed animal.");
					s.sendMessage("§3/tame invoke [Animal Type]§f - Invoke your tamed animals.");
					s.sendMessage("§3/tame horse§f - View Horse Specific Commands.");
					s.sendMessage("§3/tame reload§f - Reload Config File.");
					return true;
				}
				if (args[0].equalsIgnoreCase("horse")) {
					if (args.length == 1) {
						s.sendMessage("-----§6TameManagement Horse Commands§f-----");
						s.sendMessage("§3/tame horse claim§f - Claim a horse.");
						s.sendMessage("§3/tame horse setstyle [Style]§f  - Change Horse Type.");
						s.sendMessage("§3/tame horse setcolor [Color]§f - Change Horse Color.");
						s.sendMessage("§3/tame horse setvariant [Variant]§f - Change horse variant.");
						return true;
					}
					if (args[1].equalsIgnoreCase("claim")) {
						if (!s.hasPermission("tamemanagement.horse.claim")) {
							s.sendMessage("§cYou don't have permission to do that.");
							return true;
						}
						if (MobListener.horseclaims.contains(s.getName())) {
							MobListener.horseclaims.remove(s.getName());
						}
						MobListener.horseclaims.add(s.getName());
						s.sendMessage("§aRight Click the horse that you would like to claim.");
						return true;
					}
					if (args[1].equalsIgnoreCase("setvariant")) {
						if (!s.hasPermission("tamemanagement.horse.setvariant")) {
							s.sendMessage("§cYou don't have permission to do that.");
							return true;
						}
						// Donkey Horse Mule Skeleton Undead
						if (args.length != 3) {
							s.sendMessage("§6Proper Usage: §3/tame horse setvariant [variant]");
							s.sendMessage("§aProper Variants: Horse, Donkey, Mule, Skeleton, Undead");
							return true;
						}
						if (!args[2].equalsIgnoreCase("donkey") && !args[2].equalsIgnoreCase("horse") && !args[2].equalsIgnoreCase("mule") && !args[2].equalsIgnoreCase("skeleton") && !args[2].equalsIgnoreCase("undead")) {
							s.sendMessage("§6Proper Usage: §3/tame horse setvariant [variant]");
							s.sendMessage("§aProper Variants: Horse, Donkey, Mule, Skeleton, Undead");
							return true;
						}
						if (MobListener.horsevariants.containsKey(s.getName())) {
							MobListener.horsevariants.remove(s.getName());
						}
						if (args[2].equalsIgnoreCase("donkey")) {
							MobListener.horsevariants.put(s.getName(), Horse.Variant.DONKEY);
							s.sendMessage("§aRight click the tamed horse you would like to turn into a donkey.");
							return true;
						}
						if (args[2].equalsIgnoreCase("horse")) {
							MobListener.horsevariants.put(s.getName(), Horse.Variant.HORSE);
							s.sendMessage("§aRight click the tamed horse you would like to turn into a normal horse.");
							return true;
						}
						if (args[2].equalsIgnoreCase("mule")) {
							MobListener.horsevariants.put(s.getName(), Horse.Variant.MULE);
							s.sendMessage("§aRight click the tamed horse that you would like to turn into a mule.");
							return true;
						}
						if (args[2].equalsIgnoreCase("skeleton")) {
							MobListener.horsevariants.put(s.getName(), Horse.Variant.SKELETON_HORSE);
							s.sendMessage("§aRight click the tamed horse you would like to turn into a skeleton horse.");
							return true;
						}
						if (args[2].equalsIgnoreCase("undead")) {
							MobListener.horsevariants.put(s.getName(), Horse.Variant.UNDEAD_HORSE);
							s.sendMessage("§aRight click the tamed horse you would like to turn into an udnead horse.");
							return true;
						}
					}
					if (args[1].equalsIgnoreCase("setcolor")) {
						if (!s.hasPermission("tamemanagement.horse.setcolor")) {
							s.sendMessage("§cYou don't have permission to do that.");
							return true;
						}
						if (args.length != 3) {
							// Black, Brown, Chestnut, Creamy,Darkbrown, Gray, White
							s.sendMessage("§6Proper Usage: §3/tame horse setcolor [color]");
							s.sendMessage("§aProper Styles: §3Black, Brown, Chestnut, Creamy, DarkBrown, Gray, White");
							return true;
						}
						if (!args[2].equalsIgnoreCase("black") && !args[2].equalsIgnoreCase("brown") && !args[2].equalsIgnoreCase("chestnut") && !args[2].equalsIgnoreCase("creamy") && !args[2].equalsIgnoreCase("darkbrown") && !args[2].equalsIgnoreCase("gray") && !args[2].equalsIgnoreCase("white")) {
							s.sendMessage("§6Proper Usage: §3/tame horse setcolor [color]");
							s.sendMessage("§aProper Styles: §3Black, Brown, Chestnut, Creamy, DarkBrown, Gray, White");
							return true;
						}
						if (MobListener.horsecolors.containsKey(s.getName())) {
							MobListener.horsecolors.remove(s.getName());
						}
						if (args[2].equalsIgnoreCase("Black")) {
							MobListener.horsecolors.put(s.getName(), Horse.Color.BLACK);
							s.sendMessage("§aRight click the horse that you would like to turn black.");
							return true;
						}
						if (args[2].equalsIgnoreCase("brown")) {
							MobListener.horsecolors.put(s.getName(), Horse.Color.BROWN);
							s.sendMessage("§aRight click the horse that you would like to turn brown.");
							return true;
						}
						if (args[2].equalsIgnoreCase("chestnut")) {
							MobListener.horsecolors.put(s.getName(), Horse.Color.CHESTNUT);
							s.sendMessage("§aRight click the horse that you would like to turn chestnut.");
							return true;
						}
						if (args[2].equalsIgnoreCase("creamy")) {
							MobListener.horsecolors.put(s.getName(), Horse.Color.CREAMY);
							s.sendMessage("§aRight click the horse that you would like to turn a creamy color.");
							return true;
						}
						if (args[2].equalsIgnoreCase("darkbrown")) {
							MobListener.horsecolors.put(s.getName(), Horse.Color.DARK_BROWN);
							s.sendMessage("§aRight click the horse that you would like to turn dark brown.");
							return true;
						}
						if (args[2].equalsIgnoreCase("gray")) {
							MobListener.horsecolors.put(s.getName(), Horse.Color.GRAY);
							s.sendMessage("§aRight click the horse that you would like to turn gray.");
							return true;
						}
						if (args[2].equalsIgnoreCase("white")) {
							MobListener.horsecolors.put(s.getName(), Horse.Color.WHITE);
							s.sendMessage("§aRight click the horse that you would like to turn white.");
							return true;
						}
					}
					if (args[1].equalsIgnoreCase("setstyle")) {
						if (!s.hasPermission("tamemanagement.horse.setstyle")) {
							s.sendMessage("§cYou don't have permission to do that.");
							return true;
						}
						if (args.length != 3) {
							s.sendMessage("§6Proper Usage: §3/tame horse setstyle [Style]");
							s.sendMessage("§aProper Styles: §3BlackDots, WhiteDots, None, White, Whitefield");
							return true;
						}
						if (!args[2].equalsIgnoreCase("blackdots") && !args[2].equalsIgnoreCase("whitedots") && !args[2].equalsIgnoreCase("None") && !args[2].equalsIgnoreCase("whitefield")) {
							s.sendMessage("§6Proper Usage: §3/tame horse setstyle [Style]");
							s.sendMessage("§aProper Styles: §3BlackDots, WhiteDots, None, White, Whitefield");
							return true;
						}
						if (MobListener.horsestyles.containsKey(s.getName())) {
							MobListener.horsestyles.remove(s.getName());
						}
						if (args[2].equalsIgnoreCase("blackdots")) {
							MobListener.horsestyles.put(s.getName(), Horse.Style.BLACK_DOTS);
							s.sendMessage("§aRight click the tamed horse that you would like to give Black Dots to.");
							return true;
						}
						if (args[2].equalsIgnoreCase("whitedots")) {
							MobListener.horsestyles.put(s.getName(), Horse.Style.WHITE_DOTS);
							s.sendMessage("§aRight click the tamed horse that you would like to give white dots to.");
							return true;
						}
						if (args[2].equalsIgnoreCase("none")) {
							MobListener.horsestyles.put(s.getName(), Horse.Style.NONE);
							s.sendMessage("§aRight click the tamed horse that you would like to remove all styles from.");
							return true;
						}
						if (args[2].equalsIgnoreCase("white")) {
							MobListener.horsestyles.put(s.getName(), Horse.Style.WHITE);
							s.sendMessage("§aRight click the tamed horse that you would like to give White Socks / Stripes to.");
							return true;
						}
						if (args[2].equalsIgnoreCase("whitefield")) {
							MobListener.horsestyles.put(s.getName(), Horse.Style.WHITEFIELD);
							s.sendMessage("§aRight click the tamed horse that you would like to apply milky splotches to.");
							return true;
						}
					}
				}
				if (args[0].equalsIgnoreCase("invoke")) {
					if (!s.hasPermission("tamemanagement.invoke")) {
						s.sendMessage("§cYou don't have permission to do that.");
						return true;
					}
					if (args.length != 2) {
						s.sendMessage("§6Proper Usage: §3/tame invoke [Horse|Wolf|Ocelot]");
						return true;
					}
					if (!args[1].equalsIgnoreCase("wolf") && !args[1].equalsIgnoreCase("horse") && !args[1].equalsIgnoreCase("ocelot")) {
						s.sendMessage("§6Proper Usage: §3/tame invoke [Horse|Wolf|Ocelot]");
						return true;
					}
					Set<Entity> calledEntities = new HashSet<Entity>();
					Player player = (Player) s;
					Location loc = player.getLocation();
					World world = player.getWorld();
					List<Entity> entities = world.getEntities();
					for (Entity en: entities) {
						if (en instanceof Tameable) {
							if (args[1].equalsIgnoreCase("Horse")) {
								if (en instanceof Horse) {
									Horse horse = (Horse) en;
									if (horse.isTamed()) {
										if (horse.getOwner() == null) {
											continue;
										}
										if (horse.getOwner().getName().equals(s.getName())) {
											horse.teleport(loc);
											calledEntities.add(en);
										}
									}
								}
							}
							if (args[1].equalsIgnoreCase("Ocelot")) {
								if (en instanceof Ocelot) {
									Ocelot ocelot = (Ocelot) en;
									if (ocelot.isTamed()) {
										if (ocelot.getOwner().getName().equals(s.getName())) {
											ocelot.teleport(loc);
											calledEntities.add(en);
										}
									}
								}
							}
							if (args[1].equalsIgnoreCase("Wolf")) {
								if (en instanceof Wolf) {
									Wolf wolf = (Wolf) en;
									if (wolf.isTamed()) {
										if (wolf.getOwner().getName().equals(s.getName())) {
											wolf.teleport(loc);
											calledEntities.add(en);
										}
									}
								}
							}
						}
					}
					int size = calledEntities.size();
					if (args[1].equalsIgnoreCase("wolf")) {
						s.sendMessage("§cYou have invoked §3" + size + " wolves.");
						return true;
					}
					if (args[1].equalsIgnoreCase("ocelot")) {
						s.sendMessage("§cYou have invoked §3" + size + " ocelots.");
						return true;
					}
					if (args[1].equalsIgnoreCase("horse")) {
						s.sendMessage("§cYou have invoked §3" + size + " horses.");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("reload")) {
					if (!s.hasPermission("tamemanagement.reload")) {
						s.sendMessage("§cYou don't have permission to do that!");
						return true;
					}
					plugin.reloadConfig();
					s.sendMessage("§aTameManagement Config Reloaded.");
				}
				if (args[0].equalsIgnoreCase("release")) {
					if (!plugin.getConfig().getBoolean("AllowReleases")) {
						s.sendMessage("§cThis server does not allow animals to be released into the wild.");
						return true;
					}
					if (!s.hasPermission("tamemanagement.release")) {
						s.sendMessage("§cYou don't have permission to do that!");
						return true;
					}
					if (args.length != 1) {
						s.sendMessage("§3Proper Usage: §6/tame release");
						return true;
					}
					if (MobListener.releases.containsKey(s.getName())) {
						MobListener.releases.remove(s.getName());
					}
					MobListener.releases.put(s.getName(), "Release");
					s.sendMessage("§aRight click the tamed mob that you would like to release.");
					return true;
				}
				if (args[0].equalsIgnoreCase("setowner")) {
					if (!plugin.getConfig().getBoolean("AllowTransfers")) {
						s.sendMessage("§cThis server does not allow tamed animals to be transferred.");
						return true;
					}
					if (!s.hasPermission("tamemanagement.setowner")) {
						s.sendMessage("§cYou don't have permission to do that!");
						return true;
					}
					if (args.length != 2) {
						s.sendMessage("§3Proper Usage: §6/tame setowner [Player]");
						return true;
					}
					Player p = Bukkit.getPlayer(args[1]);
					if (p == null) {
						s.sendMessage("§3That player is not online.");
						return true;
					}
					if (p == s) {
						s.sendMessage("§cYou can't transfer ownership to yourself.");
						return true;
					}
					if (MobListener.transfers.containsKey(s.getName())) {
						MobListener.transfers.remove(s.getName());
					}
					MobListener.transfers.put(s.getName(), p.getName());
					s.sendMessage("§aRight Click the tamed mob that you would like to give to §3" + p.getName() + "§a.");
					return true;
				}
				return true;
			}

		}; tame.setExecutor(exe);
	}

}
