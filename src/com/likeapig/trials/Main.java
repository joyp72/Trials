package com.likeapig.trials;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.java.JavaPlugin;

import com.alessiodp.partiesapi.Parties;
import com.alessiodp.partiesapi.interfaces.PartiesAPI;
import com.likeapig.trials.commands.CommandsManager;
import com.likeapig.trials.maps.Map;
import com.likeapig.trials.maps.MapListener;
import com.likeapig.trials.maps.MapManager;
import com.likeapig.trials.menu.MenusListener;

public class Main extends JavaPlugin {

	public static Main instance;

	public static Main get() {
		return instance;
	}

	public void onEnable() {
		instance = this;
		getLogger().info("Trials Enabled!");
		CommandsManager.get().setup();
		Settings.get().setup(this);
		MapManager.get().setupMaps();
		MapListener.get().setup();
		MenusListener.get().setup();
		
		if (Bukkit.getPluginManager().getPermission("trials.default") == null) {
            Bukkit.getPluginManager().addPermission(new Permission("trials.default"));
            Bukkit.getPluginManager().getPermission("elimination.default").setDefault(PermissionDefault.TRUE);
        }
		if (Bukkit.getPluginManager().getPermission("trials.admin") == null) {
            Bukkit.getPluginManager().addPermission(new Permission("trials.admin"));
            Bukkit.getPluginManager().getPermission("trials.admin").setDefault(PermissionDefault.OP);
        }
		
		if (getServer().getPluginManager().getPlugin("Parties") != null) {
			if (getServer().getPluginManager().getPlugin("Parties").isEnabled()) {
				PartiesAPI api = Parties.getApi();
				getLogger().info("Hooked with Parties.");
			}
		}
		
	}

	public void onDisable() {
		this.getLogger().info("Disabled !");
		for (final Map m : MapManager.get().getMaps()) {
			m.kickAll(true);
		}
	}
}
