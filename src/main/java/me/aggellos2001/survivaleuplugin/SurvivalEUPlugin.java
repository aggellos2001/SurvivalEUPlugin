package me.aggellos2001.survivaleuplugin;

import co.aikar.commands.PaperCommandManager;
import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import me.aggellos2001.survivaleuplugin.config.Config;
import me.aggellos2001.survivaleuplugin.hooks.LuckPermsHook;
import me.aggellos2001.survivaleuplugin.modules.AdvertisementScheduler;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerWarp;
import me.aggellos2001.survivaleuplugin.utils.CommandEventRegister;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import net.ess3.api.IEssentials;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SurvivalEUPlugin extends JavaPlugin {

	public static SurvivalEUPlugin instance = null;
	//	public static Config config;
	public static PaperCommandManager COMMAND_MANAGER;
	public static TaskChainFactory chainFactory;
	public static IEssentials IEssentials;

	@Override
	public void onLoad() {
		try {
			//Make sure the directories are created. Nothing happens if they exist already.
			var dataFolder = Files.createDirectories(Path.of(this.getDataFolder().toURI()));
			var playerDataDir = Files.createDirectories(Path.of(this.getDataFolder() + File.separator + "playerData"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onEnable() {

		instance = this;

		chainFactory = BukkitTaskChainFactory.create(this); //task chain init

		COMMAND_MANAGER = new PaperCommandManager(this); //acf command manager init

		Config.loadConfig(this.getDataFolder());

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
		Config.saveConfig(this.getDataFolder());
		PlayerWarp.savePlayerWarps(); //save warps to file playerWarps.dat
		instance = null;
		getLogger().info(Utilities.colorize("&cPlugin unloaded!"));
	}

//	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
//
//		boolean isPlayer = sender instanceof Player;
////		String user = sender instanceof Player ? ((Player) sender).getName() : "Console";
//		String user;
//		if (sender instanceof Player) {
//			user = ((Player) sender).getName();
//		} else {
//			user = "Console";
//		}
////		if (player == null)
////			return false;
////
//		if (!(sender instanceof Player)) {
//			return false;
//		}
//		Player player = ((Player) sender);
//
//		String message = String.join(" ", args);
//		for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
//			if (onlinePlayer.hasPermission("your.permission")) {
//				onlinePlayer.sendMessage(message);
//			}
//		}
//		Arrays.toString(args);
//		return true;
//	}
}
