package com.likeapig.trials.commands;

import org.bukkit.entity.Player;

import com.likeapig.trials.maps.Map;
import com.likeapig.trials.maps.MapManager;
import com.likeapig.trials.maps.MessageManager;
import com.likeapig.trials.maps.MessageManager.MessageType;

public class Stop extends Commands {
	
	public Stop() {
		super("trials.admin", "Stop a game", "", new String[] { "" });
	}
	
	@Override
	public void onCommand(Player sender, String[] args) {
		Map m = MapManager.get().getMap(sender);
		if (m == null) {
			MessageManager.get().message(sender, "You are not in an arena.", MessageType.BAD);
			return;
		}
		if (!m.isStarted()) {
			MessageManager.get().message(sender, "The game has not started yet.", MessageType.BAD);
			return;
		}
		m.endRound();
		MessageManager.get().message(sender, "Round ended.");
	}
}
