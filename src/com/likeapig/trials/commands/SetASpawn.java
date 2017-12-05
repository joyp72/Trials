package com.likeapig.trials.commands;

import org.bukkit.entity.Player;

import com.likeapig.trials.maps.Map;
import com.likeapig.trials.maps.MapManager;
import com.likeapig.trials.maps.MessageManager;
import com.likeapig.trials.maps.MessageManager.MessageType;

public class SetASpawn extends Commands {
	
	public SetASpawn() {
		super("trials.admin", "Set the Alpha spawn of a map", "<map>", new String[] { "sal" });
	}
	
	@Override
	public void onCommand(Player sender, String[] args) {
		if (args.length == 0) {
			MessageManager.get().message(sender, "You must specify a map!", MessageType.BAD);
			return;
		}
		String id = args[0];
		Map m = MapManager.get().getMap(id);
		if (m == null ) {
			MessageManager.get().message(sender, "Unknown map.", MessageType.BAD);
			return;
		}
		m.setALoc(sender.getLocation());
		MessageManager.get().message(sender, "Alpha spawn set for: " + m.getName(), MessageType.GOOD);
	}

}
