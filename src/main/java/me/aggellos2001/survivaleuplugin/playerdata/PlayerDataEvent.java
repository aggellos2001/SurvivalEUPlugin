package me.aggellos2001.survivaleuplugin.playerdata;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SuppressWarnings("ResultOfMethodCallIgnored")
public final class PlayerDataEvent extends PluginActivity {

	protected static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

	/**
	 * Initializes the file and creates the directory if it does not exist.
	 */
	private void fileCheck(final File file) {
		try {
			if (!PlayerData.Directory.exists()) {
				PlayerData.Directory.mkdirs();
			}
			if (!file.exists()) {
				file.createNewFile();
			}
			if (file.length() == 0) {
				try (var writer = Files.newBufferedWriter(file.toPath())) {
					gson.toJson(PlayerData.DEFAULT, writer);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		} catch (final IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Failed to create file " + file.getAbsolutePath());
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlayerJoinLoadDataFromFile(final PlayerJoinEvent event) {
		final var player = event.getPlayer();
		final var playerUUID = event.getPlayer().getUniqueId();
		final var file = new File(PlayerData.Directory, playerUUID.toString() + ".json");

		//Makes sure the directory and the file is created and that there's valid data
		fileCheck(file);

		try (var reader = Files.newBufferedReader(file.toPath())) {
			final var playerData = gson.fromJson(reader, PlayerData.class);
//			PlayerData.PLAYER_DATA.put(playerUUID, playerData);
			PlayerData.updatePlayerData(player,playerData);
		} catch (final IOException e) {
			//Puts default settings in case reading the file has failed
//			PlayerData.PLAYER_DATA.put(playerUUID,PlayerData.DEFAULT);
			PlayerData.updatePlayerData(player,PlayerData.DEFAULT);
			e.printStackTrace();
		}
	}


	//Write data from hashmap to file as json and then remove it from the map when player leaves
	@EventHandler(ignoreCancelled = true)
	private void onPlayerLeaveSaveToFile(final PlayerQuitEvent e) {

		final var player = e.getPlayer();
		final var playerUUID = e.getPlayer().getUniqueId();
		final var file = new File(PlayerData.Directory, playerUUID.toString() + ".json");

		//If something went wrong and cache is null make sure not to write null into the file or bad things will happen!
		if (PlayerData.getPlayerData(e.getPlayer()) != null){
			try (var writer = Files.newBufferedWriter(file.toPath())) {
				gson.toJson(PlayerData.getPlayerData(player), writer);
//				PlayerData.PLAYER_DATA.remove(playerUUID);
				PlayerData.removePlayerData(player);
			} catch (final Exception ex) {
				//if writing changes fails nothing happens and file remains unchanged thus preventing further data loss.
				ex.printStackTrace();
			}
		}
	}
}
