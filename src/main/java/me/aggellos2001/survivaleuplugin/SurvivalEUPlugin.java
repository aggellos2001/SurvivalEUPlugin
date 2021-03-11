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
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
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
	public static CoreProtectAPI coreProtectAPI;

	@Override
	public void onLoad() {
		try {
			//Make sure the directories are created. Nothing happens if they exist already.
			final var dataFolder = Files.createDirectories(Path.of(this.getDataFolder().toURI()));
			final var playerDataDir = Files.createDirectories(Path.of(this.getDataFolder() + File.separator + "playerData"));
		} catch (final IOException e) {
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

		IEssentials = getEssentialsAPI();

		getServer().getScheduler().scheduleSyncRepeatingTask(this, new AdvertisementScheduler(), 30 * 20, 3600 * 20);

		PlayerWarp.loadPlayerWarps(); // load warps from file playerWarps.dat

		coreProtectAPI = getCoreProtectAPI();

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
	private CoreProtectAPI getCoreProtectAPI() {
		Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

		// Check that CoreProtect is loaded
		if (!(plugin instanceof CoreProtect)) {
			return null;
		}

		// Check that the API is enabled
		CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
		if (!CoreProtect.isEnabled()) {
			return null;
		}

		// Check that a compatible version of the API is loaded
		if (CoreProtect.APIVersion() < 6) {
			return null;
		}

		return CoreProtect;
	}

	private  IEssentials getEssentialsAPI(){
		final Plugin essentials = Bukkit.getPluginManager().getPlugin("Essentials");
		if (essentials == null) {
			throw new IllegalStateException("Essentials plugin missing!");
		}
		return (IEssentials) essentials;
	}
}
