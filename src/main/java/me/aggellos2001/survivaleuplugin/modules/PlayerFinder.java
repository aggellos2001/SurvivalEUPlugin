package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Single;
import co.aikar.commands.annotation.Subcommand;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.hooks.EssentialsXHook;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

@CommandAlias("playername")
@Conditions("ConsoleOrOp")
public class PlayerFinder extends PluginActivity {

	@Subcommand("startsWith")
	private void findNamesThatStartWith(final Player player, @Single final String startsWith) {
		final var chain = SurvivalEUPlugin.chainFactory.newChain();
		chain.syncFirst(Bukkit::getOfflinePlayers)
				.async(offlinePlayers -> {
					final var matches = new HashSet<String>();
					for (final OfflinePlayer offlinePlayer : offlinePlayers) {
						final var essentialPlayerName = EssentialsXHook.getEssentialsPlayerName(offlinePlayer.getUniqueId());
						if (essentialPlayerName == null) continue;
						if (essentialPlayerName.toLowerCase().startsWith(startsWith.toLowerCase())) {
							matches.add(essentialPlayerName);
						}
					}
					return matches;
				})
				.syncLast(matchedPlayers -> {
					if (matchedPlayers.isEmpty()) {
						Utilities.sendMsg(player, "&cNo players matching!");
					} else {
						Utilities.sendMsg(player, "&aFound " + matchedPlayers.size() + " players matching.");
						var counter = 1;
						for (final String matchedPlayer : matchedPlayers) {
							Utilities.sendMsg(player, "&e" + counter + ". " + matchedPlayer, false);
							counter++;
						}
					}
				}).execute();
	}

	@Subcommand("contains")
	private void findNamesThatContain(final Player player, @Single final String contains) {
		final var chain = SurvivalEUPlugin.chainFactory.newChain();
		chain.syncFirst(Bukkit::getOfflinePlayers)
				.async(offlinePlayers -> {
					final var matches = new HashSet<String>();
					for (final OfflinePlayer offlinePlayer : offlinePlayers) {
						final var essentialPlayerName = EssentialsXHook.getEssentialsPlayerName(offlinePlayer.getUniqueId());
						if (essentialPlayerName == null) continue;
						if (essentialPlayerName.toLowerCase().contains(contains.toLowerCase())) {
							matches.add(essentialPlayerName);
						}
					}
					return matches;
				})
				.syncLast(matchedPlayers -> {
					if (matchedPlayers.isEmpty()) {
						Utilities.sendMsg(player, "&cNo player matching!");
					} else {
						Utilities.sendMsg(player, "&aFound " + matchedPlayers.size() + " players matching.");
						var counter = 1;
						for (final String matchedPlayer : matchedPlayers) {
							Utilities.sendMsg(player, "&e" + counter + ". " + matchedPlayer, false);
							counter++;
						}
					}
				}).execute();
	}

	@Subcommand("endswith")
	private void findPlayerNameThatEndsWith(final Player player, @Single final String endsWith) {
		final var chain = SurvivalEUPlugin.chainFactory.newChain();
		chain.syncFirst(Bukkit::getOfflinePlayers)
				.async(offlinePlayers -> {
					final var matches = new HashSet<String>();
					for (final OfflinePlayer offlinePlayer : offlinePlayers) {
						final var essentialPlayerName = EssentialsXHook.getEssentialsPlayerName(offlinePlayer.getUniqueId());
						if (essentialPlayerName == null) continue;
						if (essentialPlayerName.toLowerCase().endsWith(endsWith.toLowerCase())) {
							matches.add(essentialPlayerName);
						}
					}
					return matches;
				})
				.syncLast(matchedPlayers -> {
					if (matchedPlayers.isEmpty()) {
						Utilities.sendMsg(player, "&cNo player matching!");
					} else {
						Utilities.sendMsg(player, "&aFound " + matchedPlayers.size() + " players matching.");
						var counter = 1;
						for (final String matchedPlayer : matchedPlayers) {
							Utilities.sendMsg(player, "&e" + counter + ". " + matchedPlayer, false);
							counter++;
						}
					}
				}).execute();
	}

	//utility method
	public static boolean existsPlayerWithName(String name){
		var offlinePlayers = Bukkit.getOfflinePlayers();
		final var matches = new HashSet<String>();
		for (final OfflinePlayer offlinePlayer : offlinePlayers) {
			final var essentialPlayerName = EssentialsXHook.getEssentialsPlayerName(offlinePlayer.getUniqueId());
			if (essentialPlayerName == null) continue;
			if (essentialPlayerName.equalsIgnoreCase(name)) {
				matches.add(essentialPlayerName);
			}
		}
		return !matches.isEmpty();
	}
}
