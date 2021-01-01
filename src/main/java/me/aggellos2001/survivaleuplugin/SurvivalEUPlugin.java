package me.aggellos2001.survivaleuplugin;

import co.aikar.commands.PaperCommandManager;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import me.aggellos2001.survivaleuplugin.hooks.LuckPermsHook;
import me.aggellos2001.survivaleuplugin.modules.AdvertisementScheduler;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerWarp;
import me.aggellos2001.survivaleuplugin.utils.CommandEventRegister;
import me.aggellos2001.survivaleuplugin.utils.Config;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import net.ess3.api.IEssentials;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class SurvivalEUPlugin extends JavaPlugin {

	public static SurvivalEUPlugin instance = null;
	public static Config config;
	public static PaperCommandManager COMMAND_MANAGER;
	public static TaskChainFactory chainFactory;
	public static IEssentials IEssentials;

	@Override
	public void onEnable() {

		instance = this;

		chainFactory = BukkitTaskChainFactory.create(this); //task chain init

		COMMAND_MANAGER = new PaperCommandManager(this); //acf command manager init

		config = new Config("config", this, this.getDataFolder());
		config.addDefaults(config.CONFIG.addDefaultInt("entity-limit", 10),
				config.CONFIG.addDefaultInt("xp-multiplier", 3),
				config.CONFIG.addDefaultInt("slowmode", 3),
				config.CONFIG.addDefaultInt("wild-distance", 100000),
				config.CONFIG.addDefaultInt("wild-cost", 5),
				config.CONFIG.addDefaultInt("wild-retries", 5),
				config.CONFIG.addDefaultInt("wild-delay", 300000),
				config.CONFIG.addDefault("vote-key", "REPLACE_WITH_API_TOKEN"),
				config.CONFIG.addDefault("ip-key", "REPLACE_WITH_API_TOKEN"));

		CommandEventRegister.registerCommandsAndListeners();

		LuckPermsHook.setup();

		final Plugin essentials = Bukkit.getPluginManager().getPlugin("Essentials");
		if (essentials == null) {
			throw new IllegalStateException("Essentials plugin missing!");
		}
		IEssentials = (IEssentials) essentials;

		getServer().getScheduler().scheduleSyncRepeatingTask(this, new AdvertisementScheduler(), 30 * 20, 3600 * 20);

		PlayerWarp.loadPlayerWarps(); // load warps from file playerWarps.dat

		getLogger().info(Utilities.colorize("&aPlugin loaded!"));

	}

	@Override
	public void onDisable() {
		getServer().getScheduler().cancelTasks(instance);
		config.saveConfig();
		PlayerWarp.savePlayerWarps(); //save warps to file playerWarps.dat
		instance = null;
		getLogger().info(Utilities.colorize("&cPlugin unloaded!"));
	}

}
