package com.likeapig.trials.commands;

import org.bukkit.entity.Player;

import com.likeapig.trials.maps.Map;
import com.likeapig.trials.maps.MapManager;
import com.likeapig.trials.maps.MessageManager;
import com.likeapig.trials.maps.MessageManager.MessageType;

public class Create extends Commands {

	public Create() {
		super("trials.admin", "Create a Map", "<name>", new String[] { "c", "createmap" });
	}

	@Override
	public void onCommand(Player sender, String[] args) {
		if (args.length == 0) {
			MessageManager.get().message(sender, "You must specify a map name.", MessageType.BAD);
			return;
		}
		final String id = args[0];
		Map m = MapManager.get().getMap(id);
		if (m != null) {
			MessageManager.get().message(sender, "That map already exists.", MessageType.BAD);
			return;
		}
		MapManager.get().registerMap(id);
		MessageManager.get().message(sender, "Map created!", MessageType.GOOD);
	}

}
