package me.aggellos2001.survivaleuplugin.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public final class PlayerDataEvent extends PluginActivity {

	protected static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	@EventHandler(ignoreCancelled = true)
	private void onPlayerJoinLoadDataFromFile(final PlayerJoinEvent event) {
		final var player = event.getPlayer();
		final var playerUUID = event.getPlayer().getUniqueId();

		final var file = new File(PlayerData.Directory, playerUUID.toString() + ".json");

		if (!file.exists()) {
			PlayerData.updatePlayerData(player, PlayerData.DEFAULT);
			SurvivalEUPlugin.instance.getLogger().info("Loaded default settings for player " + player.getName());
			return;
		}

		try (var reader = Files.newBufferedReader(file.toPath())) {
			final var playerData = gson.fromJson(reader, PlayerData.class);
			PlayerData.updatePlayerData(player, playerData); //put players data into array
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}


	//Write data from hashmap to file as json and then remove it from the map when player leaves
	@EventHandler(ignoreCancelled = true)
	private void onPlayerLeaveSaveToFile(final PlayerQuitEvent event) {

		final var player = event.getPlayer();
		final var playerUUID = event.getPlayer().getUniqueId();
		final var file = new File(PlayerData.Directory, playerUUID.toString() + ".json");

		//Check if cache exists before writing
		if (PlayerData.getPlayerData(event.getPlayer()) == null) {
			return;
		}

		try (var writer = Files.newBufferedWriter(file.toPath())) {
			gson.toJson(PlayerData.getPlayerData(player), writer);
			PlayerData.removePlayerData(player);
		} catch (final Exception e) {
			//if writing changes fails nothing happens and file remains unchanged thus preventing further data loss.
			e.printStackTrace();
		}
	}
}
