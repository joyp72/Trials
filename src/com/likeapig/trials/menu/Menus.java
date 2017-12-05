package com.likeapig.trials.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.likeapig.trials.Settings;
import com.likeapig.trials.maps.Map;

import net.md_5.bungee.api.ChatColor;

public class Menus {

	private static Inventory teams;

	public static Inventory getInvTeams() {
		return teams;
	}

	public Menus(Player p, Map m) {

		teams = Bukkit.createInventory(p, 9, m.getName());

		ItemStack alpha = new ItemStack(Material.CONCRETE, 1, (byte) 14);
		{
			ItemMeta meta = alpha.getItemMeta();
			meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + "Alpha");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Join alpha team");
			lore.add(" ");
			lore.add(ChatColor.GRAY + "Players: ");
			for (Player ap : m.getAPlayers()) {
				lore.add(ChatColor.RED + "  " + ap.getName());
			}
			meta.setLore(lore);
			alpha.setItemMeta(meta);
			teams.setItem(2, alpha);
		}

		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		{
			SkullMeta hmeta = (SkullMeta) head.getItemMeta();
			hmeta.setOwner(p.getName());
			hmeta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Player Stats");
			int wins;
			if (Settings.get().getDB(p.getName()) == null) {
				wins = 0;
			} else {
				wins = Settings.get().getDB(p.getName());
			}
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + p.getName());
			lore.add(" ");
			lore.add(ChatColor.WHITE + "Games won: " + ChatColor.GOLD + "" + ChatColor.BOLD + Integer.toString(wins));
			hmeta.setLore(lore);
			head.setItemMeta(hmeta);
			teams.setItem(0, head);

		}

		ItemStack bravo = new ItemStack(Material.CONCRETE, 1, (byte) 11);
		{
			ItemMeta meta = bravo.getItemMeta();
			meta.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Bravo");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.GRAY + "Join bravo team");
			lore.add(" ");
			lore.add(ChatColor.GRAY + "Players: ");
			for (Player bp : m.getBPlayers()) {
				lore.add(ChatColor.BLUE + bp.getName());
			}
			meta.setLore(lore);
			bravo.setItemMeta(meta);
			teams.setItem(6, bravo);
		}

		ItemStack chest = new ItemStack(Material.CHEST, 1);
		{
			ItemMeta meta = chest.getItemMeta();
			meta.setDisplayName(ChatColor.WHITE + "" + ChatColor.BOLD + "Loot");
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.RED + "You must win 3 games in");
			lore.add(ChatColor.RED + "a row to collect this loot!");
			lore.add(" ");
			lore.add(ChatColor.WHITE + "Contains: ");
			lore.add(ChatColor.GRAY + "- " + ChatColor.BLUE + "1 Adept Diamond Weapon");
			lore.add(ChatColor.GRAY + "- " + ChatColor.BLUE + "1 Adept Diamond Armor Piece");
			lore.add(ChatColor.GRAY + "- " + ChatColor.GOLD + "1 Adept Food Item");
			lore.add(ChatColor.GRAY + "- " + ChatColor.GREEN + "Ore Resources");
			lore.add(ChatColor.GRAY + "- " + ChatColor.DARK_PURPLE + "Rare Items..");
			meta.setLore(lore);
			chest.setItemMeta(meta);
			teams.setItem(8, chest);
		}

		ItemStack blank = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15);
		{
			ItemMeta meta = blank.getItemMeta();
			meta.setDisplayName(" ");
			meta.addItemFlags(ItemFlag.values());
			blank.setItemMeta(meta);
			teams.setItem(1, blank);
			teams.setItem(3, blank);
			teams.setItem(5, blank);
			teams.setItem(7, blank);
		}

		ItemStack eli = new ItemStack(Material.EYE_OF_ENDER);
		{
			ItemMeta meta = eli.getItemMeta();
			meta.setDisplayName(ChatColor.GOLD + "" + ChatColor.BOLD + "Trials");
			meta.addItemFlags(ItemFlag.values());
			ArrayList<String> lore = new ArrayList<>();
			lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "Overview: ");
			String l[] = formatLore(
					"Trials is a 2v2 bending minigame. Teammates will be able to revive each other and the first team to eliminate the other will win the round. The first team to win 5 rounds wins the game. If a team does not eliminate the other before the timer ends, a new round will start.",
					30, org.bukkit.ChatColor.GRAY);
			lore.add(l[0]);
			lore.add(l[1]);
			lore.add(l[2]);
			lore.add(l[3]);
			lore.add(l[4]);
			lore.add(l[5]);
			lore.add(l[6]);
			lore.add(" ");
			lore.add(ChatColor.RED + "" + ChatColor.ITALIC + "Losing a game will reset your wins!");
			meta.setLore(lore);
			eli.setItemMeta(meta);
			eli.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
			teams.setItem(4, eli);
		}

	}

	public static String[] formatLore(String text, int size, org.bukkit.ChatColor color) {
		List<String> ret = new ArrayList<String>();

		if (text == null || text.length() == 0)
			return new String[ret.size()];

		String[] words = text.split(" ");
		String rebuild = "";

		int lastAdded = 0;
		for (int i = 0; i < words.length; i++) {
			int wordLen = words[i].length();
			if (rebuild.length() + wordLen > 40 || words[i].contains("\n")
					|| words[i].equals(Character.LINE_SEPARATOR)) {
				lastAdded = i;

				ret.add(color + rebuild);
				rebuild = "";
				if (words[i].equalsIgnoreCase("\n")) {
					words[i] = "";
					continue;
				}

			}
			rebuild = rebuild + " " + words[i];

		}
		if (!rebuild.equalsIgnoreCase(""))
			ret.add(color + rebuild);

		String[] val = new String[ret.size()];
		for (int i = 0; i < ret.size(); i++)
			val[i] = ret.get(i);

		return val;
	}
}
