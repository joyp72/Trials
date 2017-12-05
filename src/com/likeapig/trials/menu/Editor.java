package com.likeapig.trials.menu;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.likeapig.trials.Settings;

public class Editor {

	public static Inventory loot;
	public Settings s = Settings.get();

	public static Inventory getLoot() {
		return loot;
	}

	public Editor(Player p) {

		loot = Bukkit.createInventory(p, 9, "Editor");
		if (s.get("loot") != null) {
			List<ItemStack> items = (List<ItemStack>) s.get("loot");
			ItemStack[] is = items.toArray(new ItemStack[0]);
			loot.setContents(is);
		}

	}

}
