package me.aggellos2001.survivaleuplugin.utils;


import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Config {

	private final String name;
	private final ArrayList<String> PATHS = new ArrayList<>();
	public final YamlConfig CONFIG;
	private final Map<String, Object> CONFIG_CACHE = new HashMap<>();
	private final JavaPlugin plugin;


	public void addDefaults(String... paths) {
		PATHS.addAll(Arrays.asList(paths));
		initializeConfig();
	}

	public Config(String name, JavaPlugin plugin, File pluginDataFolder) {
		this.name = name;
		this.plugin = plugin;
		CONFIG = new YamlConfig(pluginDataFolder, this.name);
		CONFIG.createConfig();
	}

	private void initializeConfig() {

		for (String value : PATHS) {
			CONFIG_CACHE.put(value, CONFIG.getConfig().get(value));
		}

		plugin.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new UpdateConfigFileFromCache(), Utilities.TicksDuration.MINUTE.getTime(1), Utilities.TicksDuration.MINUTE.getTime(30));
	}

	public Object getValue(String path) {
		return CONFIG_CACHE.get(path);
	}

	public void setValue(String path, Object value) {
		CONFIG_CACHE.put(path, value);
	}

	public void saveConfig() {
		for (String path : PATHS) {
			CONFIG.getConfig().set(path, CONFIG_CACHE.get(path));
		}
		CONFIG.save();
	}

	private class UpdateConfigFileFromCache implements Runnable {

		@Override
		public void run() {
			Config.this.saveConfig();
			Bukkit.getServer().getLogger().info(Utilities.colorize("&aConfig " + Config.this.name + " updated!"));
		}

		private UpdateConfigFileFromCache() {
		}
	}

}
