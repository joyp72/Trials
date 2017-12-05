package com.likeapig.trials.commands;

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
		BendingPlayer bp = BendingPlayer.getBendingPlayer(p);
		int i = 1;
		CoreAbility ca = CoreAbility.getAbility(bp.getAbilities().get(i));
		String norm = ca.getElement().getColor() + ca.getName();
		p.sendMessage(norm);

	}

}
