package com.likeapig.trials.commands;

import org.bukkit.entity.Player;

import com.likeapig.trials.menu.Editor;

public class Edit extends Commands {
	
	public Edit() {
		super("trials.admin", "Edit", "", new String[] { "ed" });
	}

	@Override
	public void onCommand(Player sender, String[] args) {
		Player p = sender;
		if (args.length == 0) {
			new Editor(p);
			p.openInventory(Editor.getLoot());
		} else {
			return;
		}

	}

}
