package com.likeapig.trials.commands;

import org.bukkit.entity.Player;

import com.likeapig.trials.Settings;
import com.likeapig.trials.maps.MessageManager;
import com.likeapig.trials.maps.MessageManager.MessageType;

public class SetWins extends Commands {

	public SetWins() {
		super("trials.admin", "Set wins", "<amount>", new String[] { "sw" });
	}

	@Override
	public void onCommand(Player sender, String[] args) {
		if (args.length == 0) {
			MessageManager.get().message(sender, "You must specify an amount.", MessageType.BAD);
			return;
		}
		int wins = Integer.parseInt(args[0]);
		Settings s = Settings.get();
		String name = sender.getName();
		s.setDB(name, wins);
		MessageManager.get().message(sender, "Set your wins to " + wins + ".");
		return;
	}

}
