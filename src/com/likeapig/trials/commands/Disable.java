package com.likeapig.trials.commands;

import org.bukkit.entity.Player;

import com.likeapig.trials.maps.Map;
import com.likeapig.trials.maps.MapManager;
import com.likeapig.trials.maps.MessageManager;
import com.likeapig.trials.maps.MessageManager.MessageType;

public class Disable extends Commands {
	
	public Disable() {
		super("trials.admin", "Disable a map", "<name>", new String[] { "di" });
	}

	@Override
	public void onCommand(Player sender, String[] args) {
		if (args.length == 0) {
			MessageManager.get().message(sender, "You must specify a map", MessageType.BAD);
			return;
		}
		String id = args[0];
		Map m = MapManager.get().getMap(id);
		if (m == null) {
			MessageManager.get().message(sender, "This map does not exist.", MessageType.BAD);
			return;
		}
		m.setEnabled(false);
		MessageManager.get().message(sender, "Disabled " + m.getName() + ".", MessageType.GOOD);
		return;
	}

}
