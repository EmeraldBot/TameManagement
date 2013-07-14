package com.etriacraft.tamemanagement;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_6_R2.entity.CraftHorse;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Horse.Color;
import org.bukkit.entity.Ocelot;
import org.bukkit.entity.Ocelot.Type;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class MobListener implements Listener {

	TameManagement plugin;

	public static HashMap<String, String> transfers = new HashMap();
	public static HashMap<String, String> releases = new HashMap();
	public static HashMap<String, Horse.Style> horsestyles = new HashMap();
	public static HashMap<String, Horse.Color> horsecolors = new HashMap(); 
	public static HashMap<String, Horse.Variant> horsevariants = new HashMap();
	public static Set<String> horseclaims = new HashSet();
	public static Set<String> getInfo = new HashSet();

	public MobListener(TameManagement instance) {
		this.plugin = instance;
	}
	@EventHandler
	public void onCreatureSpawn(CreatureSpawnEvent e) {
		if (e.getSpawnReason() == SpawnReason.BREEDING) {
			Entity entity = e.getEntity();
			if (entity instanceof Wolf) {
				if (!plugin.getConfig().getBoolean("Breeding.Wolf")) {
					e.setCancelled(true);
				}
			}
			if (entity instanceof Ocelot) {
				if (!plugin.getConfig().getBoolean("Breeding.Ocelot")) {
					e.setCancelled(true);
				}
			}
			if (entity instanceof Horse) {
				if (!plugin.getConfig().getBoolean("Breeding.Horse")) {
					e.setCancelled(true);
				}
			}
		}
	}
	@EventHandler
	public void EntityDamageEvent(EntityDamageByEntityEvent e) {
		Entity damager = e.getDamager();
		Entity damaged = e.getEntity();

		if (damager instanceof Player) {
			Player p = (Player) damager;
			if (damaged instanceof Tameable) {
				if (((Tameable) damaged).isTamed()) {
					AnimalTamer tameOwner = ((Tameable) damaged).getOwner();
					if (plugin.getConfig().getBoolean("ProtectTames") == true) {
						if (!p.getName().equals(tameOwner.getName())) {
							p.sendMessage("�cYou can't damage an animal that doesn't belong to you.");
							e.setCancelled(true);
						}
					}
				}

			}
		}
	}

	@EventHandler
	public void PlayerInteract(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		Entity entity = e.getRightClicked();
		if (entity instanceof Tameable) {
			AnimalTamer currentOwner = ((Tameable) entity).getOwner();
			if (entity instanceof Horse) {
				Horse horse = (Horse) entity;
				if (horseclaims.contains(p.getName())) {
					if(horse.getOwner() == null) {
						horse.setOwner(p);
						horseclaims.remove(p.getName());
						e.setCancelled(true);
					} else {
						p.sendMessage("�cThis horse is already owned.");
						e.setCancelled(true);
					}
				}
				// Runs this code on the /tame horse setstyle command.
				if (plugin.getConfig().getBoolean("ProtectHorses")) {
					if (horse.isTamed()) {
						if (!currentOwner.getName().equals(p.getName()) && !p.hasPermission("tamemanagement.protecthorses.override")) {
							p.sendMessage("�cYou can't interact with a horse you do not own.");
							e.setCancelled(true);
						}
					}
				}

				if (horsestyles.containsKey(p.getName())) {
					if (horse.isTamed()) {
						if (!currentOwner.getName().equals(p.getName())) {
							p.sendMessage("�cYou can't change the style on a horse that you do not own.");
							horsestyles.remove(p.getName());
							e.setCancelled(true);
						} else {
							horse.setStyle(horsestyles.get(p.getName()));
							p.sendMessage("�aHorse style changed.");
							e.setCancelled(true);
							horsestyles.remove(p.getName());
						}
					}
				}
				if (horsecolors.containsKey(p.getName())) {
					if (horse.isTamed()) {
						if (!currentOwner.getName().equals(p.getName())) {
							p.sendMessage("�cYou can't change the color of a horse you don't own.");
							horsecolors.remove(p.getName());
							e.setCancelled(true);
						} else {
							horse.setColor(horsecolors.get(p.getName()));
							p.sendMessage("�aHorse color changed.");
							e.setCancelled(true);
							horsecolors.remove(p.getName());
						}
					}
				}
				if (horsevariants.containsKey(p.getName())) {
					if (horse.isTamed()) {
						if (!currentOwner.getName().equals(p.getName())) {
							p.sendMessage("�cYou can't change the variant of a horse you don't own.");
							horsevariants.remove(p.getName());
							e.setCancelled(true);
						} else {
							horse.setVariant(horsevariants.get(p.getName()));
							p.sendMessage("�aHorse variation changed.");
							e.setCancelled(true);
							horsevariants.remove(p.getName());
						}
					}
				}

			}		
			// This code runs on the /tame release command.
			if (releases.containsKey(p.getName())) {
				if (!p.getName().equals(currentOwner.getName())) {
					p.sendMessage("�cYou do not own this animal.");
					return;
				}
				((Tameable) entity).setOwner(null);
				if (entity instanceof Wolf) {
					Wolf wolf = (Wolf) entity;
					if (wolf.isSitting()) {
						wolf.setSitting(false);
					}
				}
				if (entity instanceof Ocelot) {
					Ocelot ocelot = (Ocelot) entity;
					if (ocelot.isSitting()) {
						ocelot.setSitting(false);
					}
					ocelot.setCatType(Type.WILD_OCELOT);
				}
				p.sendMessage("�aYou have released this animal to the wild.");
				e.setCancelled(true);
				releases.remove(p.getName());
			}

			// This code runs on /tame transfer.
			if (transfers.containsKey(p.getName())) {
				String newOwner = transfers.get(p.getName());
				if (!p.getName().equals(currentOwner.getName())) {
					p.sendMessage("�cYou do not own this animal.");
					return;
				}
				Player p2 = Bukkit.getPlayer(newOwner);
				AnimalTamer newOwner2 = p2;
				((Tameable) entity).setOwner(newOwner2);
				p.sendMessage("�aYou have transferred this animal to �3" + p2.getName() + "�a.");
				transfers.remove(p.getName());		
				e.setCancelled(true);
			}
		}
	}

}
