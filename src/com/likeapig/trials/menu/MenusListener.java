package com.likeapig.trials.menu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.alessiodp.partiesapi.Parties;
import com.alessiodp.partiesapi.interfaces.PartiesAPI;
import com.likeapig.trials.Main;
import com.likeapig.trials.Settings;
import com.likeapig.trials.maps.Map;
import com.likeapig.trials.maps.MapManager;
import com.likeapig.trials.maps.MessageManager;
import com.likeapig.trials.maps.MessageManager.MessageType;

import net.md_5.bungee.api.ChatColor;

public class MenusListener implements Listener {

	private static MenusListener instance;
	PartiesAPI api = Parties.getApi();

	static {
		instance = new MenusListener();
	}

	public static MenusListener get() {
		return instance;
	}

	public void setup() {
		Bukkit.getPluginManager().registerEvents(this, Main.get());
	}

	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if (Editor.getLoot() != null) {
			if (e.getInventory().getName().equalsIgnoreCase(Editor.getLoot().getName())) {
				Inventory iv = e.getInventory();
				ItemStack[] items = iv.getContents();
				List<ItemStack> is = Arrays.asList(items);
				Settings.get().set("loot", is);
				return;
			}
		}
	}

	@EventHandler
	public void onClick(InventoryClickEvent e) {

		// Team Menu
		if (Menus.getInvTeams() != null) {
			if (e.getInventory().getName().equalsIgnoreCase(Menus.getInvTeams().getName())) {
				if (e.getCurrentItem() == null) {
					return;
				}
				if (e.getCurrentItem().getType() == Material.CONCRETE) {
					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())
							.equalsIgnoreCase("Alpha")) {
						e.setCancelled(true);

						if (e.getWhoClicked() instanceof Player) {
							Player p = (Player) e.getWhoClicked();
							Map m = MapManager.get().getMap(p);
							if (m != null) {
								MessageManager.get().message(p, "You are already in a map", MessageType.BAD);
								return;
							}
							Map m2 = MapManager.get().getMap(Menus.getInvTeams().getName());
							if (!m2.isEnabled()) {
								MessageManager.get().message(p, "The trials have not started yet!", MessageType.BAD);
								return;
							}
							if (m2.isStarted()) {
								MessageManager.get().message(p, "Map is being used!", MessageType.BAD);
								return;
							}
							if (api.haveParty(p.getUniqueId())) {
								String party = api.getPartyName(p.getUniqueId());
								if (api.getPartyLeader(party) == p.getUniqueId()) {
									m2.addAlphaPlayer(p);
									if (api.getPartyOnlinePlayers(party).size() <= 2) {
										for (Player pm : api.getPartyOnlinePlayers(party)) {
											m2.addAlphaPlayer(pm);
										}
									} else {
										MessageManager.get().message(p, "Maximum of 2 Players allowed on each team.",
												MessageType.BAD);
										return;
									}
								} else {
									MessageManager.get().message(p, "Ask your party leader to join the game.",
											MessageType.BAD);
									return;
								}
							} else {
								m2.addAlphaPlayer(p);
							}
						}
					}
					if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName())
							.equalsIgnoreCase("Bravo")) {
						e.setCancelled(true);

						if (e.getWhoClicked() instanceof Player) {
							Player p = (Player) e.getWhoClicked();
							Map m = MapManager.get().getMap(p);
							if (m != null) {
								MessageManager.get().message(p, "You are already in a map", MessageType.BAD);
								return;
							}
							Map m2 = MapManager.get().getMap(Menus.getInvTeams().getName());
							if (!m2.isEnabled()) {
								MessageManager.get().message(p, "The trials have not started yet!", MessageType.BAD);
								return;
							}
							if (m2.isStarted()) {
								MessageManager.get().message(p, "Map is being used!", MessageType.BAD);
								return;
							}
							if (api.haveParty(p.getUniqueId())) {
								String party = api.getPartyName(p.getUniqueId());
								if (api.getPartyLeader(party) == p.getUniqueId()) {
									m2.addBravoPlayer(p);
									if (api.getPartyOnlinePlayers(party).size() <= 2) {
										for (Player pm : api.getPartyOnlinePlayers(party)) {
											m2.addBravoPlayer(pm);
										}
									} else {
										MessageManager.get().message(p, "Maximum of 2 Players allowed on each team.",
												MessageType.BAD);
										return;
									}
								} else {
									MessageManager.get().message(p, "Ask your party leader to join the game.",
											MessageType.BAD);
									return;
								}
							} else {
								m2.addBravoPlayer(p);
							}
						}
					}
				}
				if (e.getCurrentItem().getType() == Material.CHEST) {
					e.setCancelled(true);
					if (e.getWhoClicked() instanceof Player) {
						Player p = (Player) e.getWhoClicked();
						Settings s = Settings.get();
						if (s.get("looted") == null) {
							s.set("looted", new ArrayList<String>());
						}
						List<String> looted = (List<String>) s.get("looted");
						Map m = MapManager.get().getMap(p);
						if (m != null) {
							MessageManager.get().message(p, "You must leave the current game to receive rewards.",
									MessageType.BAD);
							return;
						}
						if (looted.contains(p.getName())) {
							MessageManager.get().message(p, "You have already received your rewards for this weekend.",
									MessageType.BAD);
							return;
						}
						if (s.getDB(p.getName()) != null) {
							if (s.getWins(p.getName()) < 3) {
								MessageManager.get().message(p, "You have not won enough games to receive rewards.",
										MessageType.BAD);
								return;
							} else {
								List<ItemStack> items = (List<ItemStack>) s.get("loot");
								for (ItemStack is : items) {
									if (is != null) {
										p.getInventory().addItem(is);
									}
								}
								looted.add(p.getName());
								s.set("looted", looted);
								MessageManager.get().message(p, "You have been given your rewards.");
								return;
							}
						} else {
							MessageManager.get().message(p, "You have not won enough games to receive rewards.",
									MessageType.BAD);
							return;
						}
					}
				}
				if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE
						|| e.getCurrentItem().getType() == Material.EYE_OF_ENDER
						|| e.getCurrentItem().getType() == Material.SKULL_ITEM) {
					e.setCancelled(true);
				}
			}
		}

	}

}
