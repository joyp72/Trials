package com.likeapig.trials.commands;

import java.net.InetAddress;

import org.bukkit.entity.Player;
import com.projectkorra.projectkorra.BendingPlayer;
import com.projectkorra.projectkorra.ability.CoreAbility;

public class Test extends Commands {

	public Test() {
		super("trials.admin", "Test", "", new String[] { "t" });
	}

	@Override
	public void onCommand(Player sender, String[] args) {
		Player p = sender;
		InetAddress norm = p.getAddress().getAddress();
		p.sendMessage(norm.toString());

	}

}
