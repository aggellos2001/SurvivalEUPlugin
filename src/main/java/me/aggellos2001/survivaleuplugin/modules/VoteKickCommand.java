package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.hooks.EssentialsXHook;
import me.aggellos2001.survivaleuplugin.hooks.LuckPermsHook;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

@CommandAlias("votekick")
public final class VoteKickCommand extends PluginActivity {

	private final double percentageToBeKicked = 0.66;
	private final Set<Player> votedYes = new HashSet<>();
	private final Set<Player> voted = new HashSet<>();
	private boolean isVoteOnGoing;
	private boolean coolDown;
	private Player playerBeingKicked;
	private int playerCountWhenVoteStarted;

	private static final List<String> history = new ArrayList<>();

	@Default
	//@CommandCompletion("@players")
	private void onVoteKickMainUI(final Player player) {

		if (this.isVoteOnGoing) {
			voteUI(player);
			return;
		}
		if (this.coolDown) {
			Utilities.sendMsg(player, "&cWait few minutes before starting a new vote!");
			return;
		}

		final var voteKickMenu = new PaginatedGui(6, 45, Utilities.colorize("<g:#0339fc:#6d0cc7>VoteKick"));
		voteKickMenu.setDefaultClickAction(event -> event.setCancelled(true));

		for (final Player playerLoop : Bukkit.getOnlinePlayers()) {
			if (playerLoop.equals(player)) continue;
			if (LuckPermsHook.INSTANCE.isStaff(playerLoop)) continue;
			voteKickMenu.addItem(
					ItemBuilder.from(Material.PLAYER_HEAD)
							.setName(Utilities.colorize("&e" + playerLoop.getName()))
							.setSkullOwner(playerLoop)
							.asGuiItem(event -> {
								this.isVoteOnGoing = true;
								this.playerBeingKicked = playerLoop;
								this.playerCountWhenVoteStarted = nonAfkPlayers() - 1;
								this.votedYes.add(player);
								this.voted.add(player);
								this.coolDown = true;
								if (EssentialsXHook.isAFK(this.playerBeingKicked)) {
									this.playerCountWhenVoteStarted += 1; //because afks are not counted we add +1 if playerbeingkicked player is afk
								}
								Utilities.sendMsg(Bukkit.getOnlinePlayers(),
										"&aPlayer &e" + player.getName() + " &awants to kick &e" + this.playerBeingKicked.getName() + "&a!");
								voteKickMenu.close(player);

								Bukkit.getScheduler().runTaskLater(SurvivalEUPlugin.instance, () -> {
									final var percentageOfYes = (double) this.votedYes.size() / this.playerCountWhenVoteStarted;
									if (percentageOfYes >= this.percentageToBeKicked) {
										Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ipban " + this.playerBeingKicked.getName() + " 30m Votekicked -s");
										Utilities.sendMsg(Bukkit.getOnlinePlayers(), "&aVotekicked player &e" + this.playerBeingKicked.getName() + "&a!");
										SurvivalEUPlugin.instance.getLogger().info("VOTEKICK SUCCESS: Player " + this.playerBeingKicked.getName() + " got kicked because of " + player.getName() + "!");
										final Date now = new Date();
										final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
										history.add("[" + format.format(now) + "]" + " Player " + this.playerBeingKicked.getName() + " got kicked because of " + player.getName() + "!");
									} else {
										Utilities.sendMsg(Bukkit.getOnlinePlayers(), "&cVote failed. Not enough YES votes!");
									}
									this.isVoteOnGoing = false;
									this.votedYes.clear();
									this.voted.clear();
								}, Utilities.TicksDuration.MINUTE.getTime(1));
								Bukkit.getScheduler().runTaskLater(SurvivalEUPlugin.instance,
										() -> this.coolDown = false, Utilities.TicksDuration.MINUTE.getTime(5));
							})
			);
		}

		//next
		voteKickMenu.setItem(6, 6, ItemBuilder.from(Material.LIME_DYE)
				.setName(Utilities.colorize("&a&lNext"))
				.asGuiItem(event -> voteKickMenu.next())
		);
		//previous
		voteKickMenu.setItem(6, 4, ItemBuilder.from(Material.GRAY_DYE)
				.setName(Utilities.colorize("&c&lPrevious"))
				.asGuiItem(event -> voteKickMenu.previous())
		);

		voteKickMenu.getFiller().fillBottom(ItemBuilder.from(Material.BLACK_STAINED_GLASS_PANE).setName(" ").asGuiItem());

		voteKickMenu.open(player);
	}

	private int nonAfkPlayers() {
		var result = 0;
		for (final Player player : Bukkit.getOnlinePlayers()) {
			if (EssentialsXHook.isAFK(player)) continue;
			result++;
		}
		return result;
	}

	private void voteUI(final Player player) {

		if (this.playerBeingKicked.equals(player)) {
			Utilities.sendMsg(player, "You cannot vote while you are being vote kicked!");
			return;
		}

		if (this.voted.contains(player)) {
			Utilities.sendMsg(player, "&cAlready voted! Wait for vote to end first!");
			return;
		}

		final var voteUI = new Gui(1, Utilities.colorize("<#0d7d07>Kick <#a18d0a>" + this.playerBeingKicked.getName() + " <#0d7d07>?"));
		voteUI.setDefaultClickAction(event -> event.setCancelled(true));

		voteUI.setItem(4, ItemBuilder.from(Material.PLAYER_HEAD)
				.setName(Utilities.colorize("&e" + this.playerBeingKicked.getName()))
				.setSkullOwner(this.playerBeingKicked)
				.asGuiItem()
		);
		voteUI.setItem(3, ItemBuilder.from(Material.RED_STAINED_GLASS_PANE)
				.setName(Utilities.colorize("&cNO"))
				.asGuiItem(event -> {
					//prevents from player voting after vote ends if he keeps gui open
					if (!this.isVoteOnGoing) {
						Utilities.sendMsg(player, "&cVote ended!");
						voteUI.close(player);
						return;
					}
					this.voted.add(player);
					Utilities.sendMsg(player, "&aVoted &cNO &asuccessfully!");
					voteUI.close(player);
				})
		);
		voteUI.setItem(5, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE)
				.setName(Utilities.colorize("&aYES"))
				.asGuiItem(event -> {
					if (!this.isVoteOnGoing) {
						Utilities.sendMsg(player, "&cVote ended!");
						voteUI.close(player);
						return;
					}
					this.voted.add(player);
					this.votedYes.add(player);
					Utilities.sendMsg(player, "&aVoted &aYES &asuccessfully!");
					voteUI.close(player);
				})
		);

		voteUI.open(player);
	}

	@Subcommand("history")
	@Conditions("ConsoleOrOp")
	private void history(final Player player) {
		var count = 1;
		System.out.println(history);
		if (history.isEmpty()) {
			Utilities.sendMsg(player, "&cNo history found!");
			return;
		}
		Utilities.sendMsg(player, "&a&lVotekick history:");
		for (final String line : history) {
			Utilities.sendMsg(player, "&e" + count + ". " + line, false);
			count++;
		}
	}
}
