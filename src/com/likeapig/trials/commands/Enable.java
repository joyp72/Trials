package com.likeapig.trials.commands;

import org.bukkit.entity.Player;

import com.likeapig.trials.maps.Map;
import com.likeapig.trials.maps.MapManager;
import com.likeapig.trials.maps.MessageManager;
import com.likeapig.trials.maps.MessageManager.MessageType;

public class Enable extends Commands {

	public Enable() {
		super("trials.admin", "Enable a map", "<name>", new String[] { "en" });
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
		if (m.isEnabled()) {
			MessageManager.get().message(sender, "This map is already enabled.", MessageType.BAD);
			return;
		}
		m.resetAll();
		m.setEnabled(true);
		MessageManager.get().message(sender, "Enabled " + m.getName() + ".", MessageType.GOOD);
		return;
	}

}
