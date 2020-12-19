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


	public void addDefaults(final String... paths) {
		this.PATHS.addAll(Arrays.asList(paths));
		initializeConfig();
	}

	public Config(final String name, final JavaPlugin plugin, final File pluginDataFolder) {
		this.name = name;
		this.plugin = plugin;
		this.CONFIG = new YamlConfig(pluginDataFolder, this.name);
		this.CONFIG.createConfig();
	}

	private void initializeConfig() {

		for (final String value : this.PATHS) {
			this.CONFIG_CACHE.put(value, this.CONFIG.getConfig().get(value));
		}

		this.plugin.getServer().getScheduler().scheduleSyncRepeatingTask(this.plugin, new UpdateConfigFileFromCache(), Utilities.TicksDuration.MINUTE.getTime(1), Utilities.TicksDuration.HOUR.getTime(5));
	}

	public Object getValue(final String path) {
		return this.CONFIG_CACHE.get(path);
	}

	public void setValue(final String path, final Object value) {
		this.CONFIG_CACHE.put(path, value);
	}

	public void saveConfig() {
		if (this.PATHS.size() == 0) {
			return;
		}
		for (final String path : this.PATHS) {
			this.CONFIG.getConfig().set(path, this.CONFIG_CACHE.get(path));
		}
		this.CONFIG.save();
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
