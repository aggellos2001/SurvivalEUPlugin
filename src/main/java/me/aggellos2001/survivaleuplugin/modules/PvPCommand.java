package me.aggellos2001.survivaleuplugin.modules;


import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerData;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

//@CommandAlias("pvp")
//@Conditions("cooldown:seconds=10")
public final class PvPCommand extends PluginActivity {

//private static final List<Player> PVP_ON = new ArrayList<>();

	/**
	 * Package private gia na epitrefei se alles klaseis an o player exei pvp on
	 *
	 * @param p
	 * @return an exei to pvp on
	 */
	static boolean hasPvPOn(final Player p) {
		return PlayerData.getPlayerData(p).pvp;
	}

//	@Default
//	private void onCommand(final Player player) {
//		if (PVP_ON.contains(player)) {
//			PVP_ON.remove(player);
//			Utilities.sendMsg(player, Language.PVP_DISABLED.getTranslation(player));
//		} else {
//			if (DonationBenefitCommand.hasDonationPotions(player)) {
//				Utilities.sendMsg(player, Language.PVP_DONATION_POTIONS_DENIED.getTranslation(player));
//				return;
//			}
//			PVP_ON.add(player);
//			Utilities.sendMsg(player, Language.PVP_ENABLED.getTranslation(player));
//		}
//	}
//
//	@Subcommand("check")
//	@CommandCompletion("@players")
//	@CommandPermission("seu.pvp.other")
//	private void onCheckPvP(final Player player, @Optional final OnlinePlayer playerToCheck) {
//		if (playerToCheck == null) {
//			final var hasPvP = hasPvPOn(player);
//			if (hasPvP) {
//				Utilities.sendMsg(player, "&aYour PvP status is: &lenabled!");
//			} else {
//				Utilities.sendMsg(player, "&aYour PvP status is: &c&ldisabled!");
//			}
//		} else {
//			final var hasPvP = hasPvPOn(playerToCheck.getPlayer());
//			if (hasPvP) {
//				Utilities.sendMsg(player, String.format("&aPlayer %s PvP status is: &lenabled!", playerToCheck.player.getName()));
//			} else {
//				Utilities.sendMsg(player, String.format("&aPlayer %s PvP status is: &c&ldisabled!", playerToCheck.player.getName()));
//			}
//		}
//	}

	/**
	 * Σταματάει το PvP αν είναι OFF (από default είναι OFF)
	 */
	@EventHandler(ignoreCancelled = true)
	private void onPvP(final EntityDamageByEntityEvent e) {

		if (!(e.getDamager() instanceof Player)) return;
		if (!(e.getEntity() instanceof Player)) return;

		final var attacker = (Player) e.getDamager();
		final var defender = (Player) e.getEntity();

		var attackerPvP = PlayerData.getPlayerData(attacker).pvp;
		var defenderPvP = PlayerData.getPlayerData(defender).pvp;

		if (!attackerPvP) {
			Utilities.sendMsg(attacker, Language.PVP_DISABLED_WARNING.getTranslation(attacker));
			e.setCancelled(true);
			return;
		}

		if (!defenderPvP) {
			Utilities.sendMsg(attacker, String.format(Language.PVP_OTHER_DISABLED_WARNING.getTranslation(attacker), defender.getName()));
			e.setCancelled(true);
		}
	}

	/**
	 * Stops PvP from Projectiles
	 */
	@EventHandler(ignoreCancelled = true)
	private void onProjectilePvP(final EntityDamageByEntityEvent e) {

		if (!(e.getDamager() instanceof Projectile)) return;
		if (!(e.getEntity() instanceof Player)) return;
		if (!(((Projectile) e.getDamager()).getShooter() instanceof Player)) return;

		final var attacker = (Player) ((Projectile) e.getDamager()).getShooter();
		final var defender = (Player) e.getEntity();

		if (attacker == null) return;

		if (attacker.equals(defender)) return;

		var attackerPvP = PlayerData.getPlayerData(attacker).pvp;
		var defenderPvP = PlayerData.getPlayerData(defender).pvp;

		if (!attackerPvP) {
			Utilities.sendMsg(attacker, Language.PVP_DISABLED_WARNING.getTranslation(attacker));
			e.setCancelled(true);
			return;
		}

		if (!defenderPvP) {
			Utilities.sendMsg(attacker, String.format(Language.PVP_OTHER_DISABLED_WARNING.getTranslation(attacker), defender.getName()));
			e.setCancelled(true);
		}
	}


	/**
	 * Stops players to be able to use a Lava bucket in order to kill someone with pvp off!
	 */
	@EventHandler(ignoreCancelled = true)
	private void onLavaPlace(final PlayerBucketEmptyEvent e) {

		if (e.getBucket() != Material.LAVA_BUCKET) return;

		for (final Player player : e.getBlockClicked().getLocation().getNearbyPlayers(4)) {
			var pvpOfPlayer = PlayerData.getPlayerData(player).pvp;
			if (!pvpOfPlayer && !player.equals(e.getPlayer())) {
				Utilities.sendMsg(e.getPlayer(), String.format(Language.PVP_OTHER_DISABLED_WARNING.getTranslation(e.getPlayer()), player.getName()));
				e.setCancelled(true);
			}
		}
	}

	/**
	 * Prevents flint and steel, tnt and bed to kill other players!
	 */
	@EventHandler(ignoreCancelled = true)
	private void onUsingFlintAndSteelOrTntOrBed(final PlayerInteractEvent e) {

		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (!e.hasItem() || !e.hasBlock() || e.getItem() == null) return;
		if (e.getClickedBlock() == null) return;

		if (e.getItem().getType().equals(Material.FLINT_AND_STEEL) || e.getItem().getType().equals(Material.TNT)) {
			for (final Player player : e.getClickedBlock().getLocation().getNearbyPlayers(4)) {
				var pvpOfPlayer = PlayerData.getPlayerData(player).pvp;
				if (!pvpOfPlayer && player != e.getPlayer()) {
					Utilities.sendMsg(e.getPlayer(), String.format(Language.PVP_OTHER_DISABLED_WARNING.getTranslation(e.getPlayer()), player.getName()));
					e.setCancelled(true);
					return;
				}
			}
		}

		if (e.getItem().getType().toString().contains("BED") &&
				e.getItem().getType() != Material.BEDROCK &&
				(e.getClickedBlock().getWorld().getEnvironment().equals(World.Environment.NETHER) ||
						e.getClickedBlock().getWorld().getEnvironment().equals(World.Environment.THE_END))) {

			for (final Player player : e.getClickedBlock().getLocation().getNearbyPlayers(4)) {
				var pvpOfPlayer = PlayerData.getPlayerData(player).pvp;
				if (!pvpOfPlayer && player != e.getPlayer()) {
					Utilities.sendMsg(e.getPlayer(), String.format(Language.PVP_OTHER_DISABLED_WARNING.getTranslation(e.getPlayer()), player.getName()));
					e.setCancelled(true);
					return;
				}
			}
		}
	}
}
