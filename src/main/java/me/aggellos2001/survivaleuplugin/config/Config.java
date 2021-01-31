package me.aggellos2001.survivaleuplugin.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Config {

	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final Config DEFAULT = new Config();
	private static Config config;

	public int entityLimit;
	public int xpMultiplier;
	public int slowModeDelay;
	public int wildBlockRange;
	public float wildCost;
	public int wildDelay;
	public int wildRetries;
	public String voteAPIToken;
	public String vpnProxyCheckAPIToken;

	public Config() {
		this.entityLimit = 20;
		this.xpMultiplier = 1;
		this.slowModeDelay = 1;
		this.wildBlockRange = 100_000;
		this.wildCost = 50;
		this.wildDelay = 3 * 100_000;
		this.wildRetries = 10;
		this.voteAPIToken = "null";
		this.vpnProxyCheckAPIToken = "null";
	}

	public static void loadConfig(File pluginDataFolder) {
		var file = new File(pluginDataFolder, "config.json");
		if (!file.exists()) {
			config = DEFAULT;
			return;
		}
		try (var reader = Files.newBufferedReader(file.toPath())) {
			config = gson.fromJson(reader, Config.class);
			System.out.println("Config loaded!");
			System.out.println(config);
		} catch (final IOException e) {
			config = DEFAULT;
			e.printStackTrace();
		}
	}

	public static void saveConfig(File pluginDataFolder) {
		if (config == null) {
			return;
		}
		var file = new File(pluginDataFolder, "config.json");
		try (var writer = Files.newBufferedWriter(file.toPath())) {
			gson.toJson(config, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Config getConfig() {
		return config;
	}

	@Override
	public String toString() {
		return "entityLimit: " + entityLimit + " xpMultiplier: " + xpMultiplier + " slowModeDelay: " + slowModeDelay +
				" wildBlockRange: " + wildBlockRange + " wildCost: " + wildCost + " wildDelay: " + wildDelay + " wildRetries: " + wildRetries +
				" voteAPIToken: " + voteAPIToken + " vpnProxyCheckAPIToken: " + vpnProxyCheckAPIToken;
	}
}
