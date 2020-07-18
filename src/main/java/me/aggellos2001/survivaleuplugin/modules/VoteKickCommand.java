package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.hooks.EssentialsXHook;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

@CommandAlias("votekick")
@CommandPermission("seu.votekick")
public final class VoteKickCommand extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	private static final double percentageToBeKicked = 0.9;

	private final Set<Player> votedYes = new HashSet<>();
	private final Set<Player> votedNo = new HashSet<>();

	private boolean isVoteOnGoing;
	private Player playerBeingKicked;

	@Default
	@CommandCompletion("@players")
	private void onVoteKick(final Player player, @Optional final OnlinePlayer playerToVote) {
		if (playerToVote != null) {
			if (this.isVoteOnGoing) {
				Utilities.sendMsg(player, Language.VOTEKICK_VOTE_ALREADY_ONGOING.getTranslation(player));
				return;
			}
			if (player.equals(playerToVote.getPlayer())) {
				Utilities.sendMsg(player, Language.VOTEKICK_CANNOT_KICK_SELF.getTranslation(player));
				return;
			}
			this.isVoteOnGoing = true;
			this.playerBeingKicked = playerToVote.getPlayer();

			for (final Player playerInLoop : Bukkit.getOnlinePlayers()) {
				Utilities.colorize(String.format(Language.VOTEKICK_STARTED.getTranslation(player), this.playerBeingKicked, player), true);
			}

			new BukkitRunnable() {
				@Override
				public void run() {
					final var nonAfkPlayers = new HashSet<Player>();

					for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
						if (!EssentialsXHook.isAFK(onlinePlayer)) {
							nonAfkPlayers.add(onlinePlayer);
						}
					}

					final var playersVotedYes = VoteKickCommand.this.votedYes.size();
					final var percentage = playersVotedYes / (double) nonAfkPlayers.size();

					if (percentage < percentageToBeKicked) {
						for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
							Utilities.sendMsg(onlinePlayer, Language.VOTEKICK_FAILED.getTranslation(player));
						}
					}
					if (percentage >= percentageToBeKicked) {
						for (final Player onlinePlayer : Bukkit.getOnlinePlayers()) {
							Utilities.sendMsg(onlinePlayer, String.format(Language.VOTEKICK_SUCCESS.getTranslation(player), VoteKickCommand.this.playerBeingKicked));
						}
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ipban -s " + playerToVote.getPlayer().getName() + " 1h Votekicked!");
					}

					VoteKickCommand.this.votedYes.clear();
					VoteKickCommand.this.votedNo.clear();
					VoteKickCommand.this.isVoteOnGoing = false;
					VoteKickCommand.this.playerBeingKicked = null;
				}
			}.runTaskLater(SurvivalEUPlugin.instance, Utilities.TicksDuration.SECOND.ticks * 60);

		} else {
			Utilities.sendMsg(player,
					"&bVotekick Commands \n" +
							"&e/votekick {player}\n" +
							"/votekick yes\n" +
							"/votekick no"
			);
		}
	}

	@Subcommand("yes|y")
	private void onYes(final Player player) {
		if (!this.isVoteOnGoing) {
			Utilities.sendMsg(player, Language.VOTEKICK_NOT_ONGOING.getTranslation(player));
			return;
		}
		if (player.equals(this.playerBeingKicked)) {
			Utilities.sendMsg(player, Language.VOTEKICK_CANT_VOTE_FOR_SELF.getTranslation(player));
		}
		if (!this.votedYes.contains(player) && !this.votedNo.contains(player)) {
			this.votedYes.add(player);
			Utilities.sendMsg(player, Language.VOTEKICK_VOTED_YES.getTranslation(player));
		} else {
			Utilities.sendMsg(player, Language.VOTEKICK_ALREADY_VOTED.getTranslation(player));
		}
	}

	@Subcommand("no|n")
	private void onNo(final Player player) {
		if (!this.isVoteOnGoing) {
			Utilities.sendMsg(player, Language.VOTEKICK_NOT_ONGOING.getTranslation(player));
			return;
		}
		if (player.equals(this.playerBeingKicked)) {
			Utilities.sendMsg(player, Language.VOTEKICK_CANT_VOTE_FOR_SELF.getTranslation(player));
		}

		if (!this.votedYes.contains(player) && !this.votedNo.contains(player)) {
			this.votedNo.add(player);
			Utilities.sendMsg(player, Language.VOTEKICK_VOTED_NO.getTranslation(player));
		} else {
			Utilities.sendMsg(player, Language.VOTEKICK_ALREADY_VOTED.getTranslation(player));
		}
	}
}
