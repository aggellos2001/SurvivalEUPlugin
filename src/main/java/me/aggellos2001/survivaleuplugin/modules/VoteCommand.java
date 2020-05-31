package me.aggellos2001.survivaleuplugin.modules;


import co.aikar.commands.CommandManager;
import co.aikar.commands.ConditionFailedException;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.MessageActionHandler;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@CommandAlias("vote")
@Conditions("cooldown:seconds=10")
public final class VoteCommand extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	private final HttpClient client = HttpClient.newHttpClient();


	private String checkIfVoted(final Player p) {
		//get vote key from config
		var voteKey = (String) SurvivalEUPlugin.config.getValue("vote-key");
		if (voteKey.equalsIgnoreCase("REPLACE_WITH_API_TOKEN")) {
			Bukkit.getServer().getLogger().info("VOTE KEY API NOT CONFIGURED");
			return null;
		}
		try {
			var request = HttpRequest.newBuilder()
					.uri(new URI("https://minecraft-mp.com/api/?object=votes&element=claim&key=" + voteKey + "&username=" + p.getName()))
					.version(HttpClient.Version.HTTP_2)
					.timeout(Duration.ofSeconds(10))
					.GET()
					.build();
			final var response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
			return response.body();
		} catch (URISyntaxException | InterruptedException | IOException e) {
			return null;
		}
	}


	// -----------------
	private String checkIfClaimed(final Player p) {

		var voteKey = (String) SurvivalEUPlugin.config.getValue("vote-key");
		if (voteKey.equalsIgnoreCase("REPLACE_WITH_API_TOKEN")) {
			Bukkit.getServer().getLogger().info("VOTE KEY API NOT CONFIGURED");
			return null;
		}

		try {
			var requestToClaimReward = HttpRequest.newBuilder()
					.uri(new URI("https://minecraft-mp.com/api/?action=post&object=votes&element=claim&key=" + voteKey + "&username=" + p.getName()))
					.timeout(Duration.ofSeconds(10))
					.POST(HttpRequest.BodyPublishers.noBody())
					.build();
			final var responseToSeeIfClaimed = this.client.send(requestToClaimReward, HttpResponse.BodyHandlers.ofString());
			return responseToSeeIfClaimed.body();
		} catch (URISyntaxException | IOException | InterruptedException e) {
			return null;
		}
	}

	@Default
	private void onCommand(final Player player) {
		Utilities.sendMsg(player, Language.VOTE_INFO.getTranslation(player));
	}

	@Subcommand("check")
	@CommandCompletion("@players")
	private void onVoteCheck(final Player player, @Optional final OnlinePlayer playerToCheck) {
		if (playerToCheck == null) {
			var chain = SurvivalEUPlugin.chainFactory.newChain();
			chain.asyncFirst(() -> checkIfVoted(player))
					.abortIfNull(new MessageActionHandler(), player, Language.VOTE_ERROR.getTranslation(player))
					.syncLast(result -> {
						switch (Integer.parseInt(result)) {
							case 0:
								Utilities.sendMsg(player, Language.VOTE_DID_NOT_VOTED.getTranslation(player));
								break;
							case 1:
								Utilities.sendMsg(player, Language.VOTE_DID_NOT_CLAIMED.getTranslation(player));
								break;
							case 2:
								Utilities.sendMsg(player, Language.VOTE_VOTED.getTranslation(player));
								break;
						}
					}).execute();
		} else {
			if (!CommandManager.getCurrentCommandIssuer().hasPermission("seu.vote.others")) {
				throw new ConditionFailedException("No permission to check others voting status!");
			}
			SurvivalEUPlugin.chainFactory.newChain()
					.asyncFirst(() -> checkIfVoted(playerToCheck.getPlayer()))
					.abortIfNull(new MessageActionHandler(), player, Language.VOTE_ERROR.getTranslation(player))
					.syncLast(result -> {
						switch (Integer.parseInt(result)) {
							case 0:
								Utilities.sendMsg(player, "&cΟ παίκτης " + playerToCheck.getPlayer().getName() + " δεν έχει κάνει vote ακόμα!");
								break;
							case 1:
								Utilities.sendMsg(player, "&aΟ παίκτης " + playerToCheck.getPlayer().getName() + " έκανε vote &cαλλά δεν έκανε ακόμα &a&l/vote claim!");
								break;
							case 2:
								Utilities.sendMsg(player, "&aΟ παίκτης " + playerToCheck.getPlayer().getName() + " έκανε vote και έχει πάρει την ανταμοιβή του!");
								break;
						}
					}).execute();
		}
	}


	@Subcommand("claim")
	private void onClaim(final Player player) {
		if (Utilities.getPlayerEmptySlots(player) < 1) {
			Utilities.sendMsg(player, Language.VOTE_NO_INV_SPACE.getTranslation(player));
			return;
		}
		var chain = SurvivalEUPlugin.chainFactory.newChain();
		chain.asyncFirst(() -> checkIfVoted(player))
				.abortIfNull(new MessageActionHandler(), player, Language.VOTE_ERROR.getTranslation(player))
				.storeAsData("checkIfVoted")
				.asyncFirst(() -> checkIfClaimed(player))
				.abortIfNull(new MessageActionHandler(), player, Language.VOTE_ERROR.getTranslation(player))
				.storeAsData("checkIfClaimed")
				.sync(() -> {
					var checkIfVoted = chain.getTaskData("checkIfVoted");
					var checkIfClaimed = chain.getTaskData("checkIfClaimed");
					if (checkIfVoted.equals("0") && checkIfClaimed.equals("0")) {
						Utilities.sendMsg(player, Language.VOTE_DID_NOT_VOTED.getTranslation(player));
					} else if (checkIfClaimed.equals("0")) {
						Utilities.sendMsg(player, Language.VOTE_ALREADY_CLAIMED.getTranslation(player));
					} else {
						//Give player rewards here
						final var rewards = new String[]{
								"acb " + player.getName() + " 500",
								"give " + player.getName() + " phantom_membrane 2",
								"eco give " + player.getName() + " 1000"
						};
						for (final String reward : rewards) {
							Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), reward);
						}
						Utilities.sendMsg(player, Language.VOTE_REWARDS.getTranslation(player));
						Utilities.sendMsg(player, Language.VOTE_REWARD_RECEIVED.getTranslation(player));
						// everyone that the player voted
						Bukkit.broadcastMessage(Utilities.colorize(String.format(Language.VOTE_BROADCAST.english, player.getName()),true));
					}
				}).execute();
	}

	@Subcommand("apikey")
	@CommandPermission("seu.votekey")
	private void changeAPIKey(Player player, @Single String apiKey) {
		SurvivalEUPlugin.config.setValue("vote-key", apiKey);
		Utilities.sendMsg(player, "&aVote api key set to " + apiKey + "!");
	}
}

