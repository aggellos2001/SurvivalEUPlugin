package me.aggellos2001.survivaleuplugin.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class PlayerDataEvent extends PluginActivity {

	/**
	 *  This is a HashMap acting as a cache to store player data
	 */
	private static final Map<UUID, PlayerData> PLAYER_DATA = new HashMap<>();

	/**
	 * This is the directory where we save player data to the disk
	 */
	protected static final File Directory = new File(Path.of(SurvivalEUPlugin.instance.getDataFolder() + File.separator + "playerData").toUri());

	/**
	 * A new instance of Gson with {@link GsonBuilder#setPrettyPrinting()} enabled.
	 * We use this to read and write player data from our cache {@link #PLAYER_DATA} to json files
	 */
	protected static final Gson gson = new GsonBuilder().setPrettyPrinting().create();


	/**
	 * Method to access the private playerData cache from outside.<br>
	 * It will always return a non-null value.
	 *
	 * @param player Player to get data.
	 * @return Players data from cache or default value if player was not found
	 */
	@NotNull
	public static PlayerData getPlayerData(@NotNull final Player player) {
		var data = PLAYER_DATA.getOrDefault(player.getUniqueId(), PlayerData.DEFAULT);
		return data != null ? data : PlayerData.DEFAULT; // this is here in case we accidentally used setPlayerData(player,null)!
	}

	/**
	 * Method to change playerData in the cache.
	 * @param player The player to change data
	 * @param data Data value or null to set players Data to default values. See {@link #getPlayerData(Player)}
	 */
	public static void setPlayerData(@NotNull Player player, @NotNull final PlayerData data) {
		PLAYER_DATA.put(player.getUniqueId(), data);
	}

	/**
	 * Removes player data from cache after data is no longer needed in memory.
	 * For example after data is written to disk.
	 * @param player The player to remove data from cache
	 */
	public static void removePlayerData(final Player player) {
		PLAYER_DATA.remove(player.getUniqueId());
	}

	/**
	 * This method will load the players data from the json file to the cache {@link #PLAYER_DATA} when he joins the server.
	 * We use players UUID to find the file in the data directory see {@link #Directory}. <br>
	 * File names are following the format: <pre>uuid.json</pre>
	 * If the file doesn't exist it add the default values to the cache.
	 * We will create the file when the we have save the data when the player leaves the server {@link #onPlayerLeaveSaveToFile(PlayerQuitEvent)}
	 *
	 */
	@EventHandler(ignoreCancelled = true)
	private void onPlayerJoinLoadDataFromFile(final PlayerJoinEvent event) {

		final var player = event.getPlayer();
		final var playerUUID = event.getPlayer().getUniqueId();
		final var file = new File(PlayerDataEvent.Directory, playerUUID.toString() + ".json");

		if (!file.exists()) {
			PlayerDataEvent.setPlayerData(player, PlayerData.DEFAULT);
			SurvivalEUPlugin.instance.getLogger().info("Loaded default settings for player " + player.getName());
			return;
		}

		try (var reader = Files.newBufferedReader(file.toPath())) {
			final var playerData = gson.fromJson(reader, PlayerData.class);
			PlayerDataEvent.setPlayerData(player, playerData);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * Method that saves player data from the cache {@link #PLAYER_DATA} to a json file in the disk when the
	 * player leaves the server. The file has the following name format: <pre>uuid.json</pre>
	 * If the file does not exist, it will be created automatically by {@link Gson#toJson(Object, Appendable)}
	 */
	@EventHandler(ignoreCancelled = true)
	private void onPlayerLeaveSaveToFile(final PlayerQuitEvent event) {

		final var player = event.getPlayer();
		final var playerUUID = event.getPlayer().getUniqueId();
		final var file = new File(PlayerDataEvent.Directory, playerUUID.toString() + ".json");

		try (var writer = Files.newBufferedWriter(file.toPath())) {
			gson.toJson(PlayerDataEvent.getPlayerData(player), writer);
			PlayerDataEvent.removePlayerData(player);
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
}
