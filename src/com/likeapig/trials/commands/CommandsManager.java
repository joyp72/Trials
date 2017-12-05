package com.likeapig.trials.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.likeapig.trials.Main;
import com.likeapig.trials.maps.Map;
import com.likeapig.trials.maps.MapManager;
import com.likeapig.trials.maps.MessageManager;
import com.likeapig.trials.menu.Menus;

public class CommandsManager implements CommandExecutor {

	private List<Commands> cmds;
	private static CommandsManager instance;

	static {
		instance = new CommandsManager();
	}

	public static CommandsManager get() {
		return instance;
	}

	private CommandsManager() {
		cmds = new ArrayList<Commands>();
	}

	public void setup() {
		Main.get().getCommand("tra").setExecutor(this);
		cmds.add(new Join());
		cmds.add(new Create());
		cmds.add(new SetASpawn());
		cmds.add(new SetBSpawn());
		cmds.add(new com.likeapig.trials.commands.List());
		cmds.add(new Leave());
		cmds.add(new Start());
		cmds.add(new Stop());
		cmds.add(new Test());
		cmds.add(new ForceStart());
		cmds.add(new SetRift());
		cmds.add(new Enable());
		cmds.add(new Disable());
		cmds.add(new Edit());
		cmds.add(new SetWins());
	}

	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		Player p = (Player) sender;
		if (!cmd.getName().equalsIgnoreCase("tra")) {
			return true;
		}
		if (args.length == 0) {
			Map m = MapManager.get().getMap("trials");
			new Menus(p, m);
			p.openInventory(Menus.getInvTeams());
			return true;
		}
		if (args.length != 0) {
			Commands c = getCommand(args[0]);
			if (c != null) {
				List<String> a = new ArrayList<String>(Arrays.asList(args));
				a.remove(0);
				args = a.toArray(new String[a.size()]);
				c.commandPreprocess(p, args);
			}
		}
		return true;
	}

	private Commands getCommand(String name) {
		for (Commands c : cmds) {
			if (c.getClass().getSimpleName().trim().equalsIgnoreCase(name.trim())) {
				return c;
			}
			String[] aliases;
			for (int length = (aliases = c.getAliases()).length, i = 0; i < length; ++i) {
				String s = aliases[i];
				if (s.trim().equalsIgnoreCase(name.trim())) {
					return c;
				}
			}
		}
		return null;
	}
}
