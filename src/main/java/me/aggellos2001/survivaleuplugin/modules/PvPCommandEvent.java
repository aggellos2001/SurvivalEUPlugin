package me.aggellos2001.survivaleuplugin.modules;


import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerData;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerDataEvent;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashSet;
import java.util.Set;

@CommandAlias("bypassPvP")
@CommandPermission("seu.bypasspvp")
public final class PvPCommandEvent extends PluginActivity {

	private static final Set<Player> PVP_BYPASS = new HashSet<>();

	/**
	 * Returns true if player has pvp enabled
	 *
	 * @param p
	 * @return True if pvp is enabled
	 */
	public static boolean hasPvPOn(final Player p) {
		return PlayerDataEvent.getPlayerData(p).pvp;
	}

	@Default
	private void toggleBypass(Player player){
		if (PVP_BYPASS.contains(player)){
			PVP_BYPASS.remove(player);
			Utilities.sendMsg(player,"&cYou are now respecting PvP protections!");
		}else{
			PVP_BYPASS.add(player);
			Utilities.sendMsg(player,"&aYou are now ignoring PvP protections!");
		}
	}


	// Σταματάει το PvP αν είναι OFF (από default είναι OFF)
	@EventHandler(ignoreCancelled = true)
	private void onPvP(final EntityDamageByEntityEvent e) {

		if (!(e.getDamager() instanceof Player)) return;
		if (!(e.getEntity() instanceof Player)) return;

		final var attacker = (Player) e.getDamager();
		final var defender = (Player) e.getEntity();

		if (PVP_BYPASS.contains(attacker)) return; //bypass protection

		final var attackerPvP = PlayerDataEvent.getPlayerData(attacker).pvp;
		final var defenderPvP = PlayerDataEvent.getPlayerData(defender).pvp;

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

		if (PVP_BYPASS.contains(attacker)) return; //bypass protection

		if (attacker.equals(defender)) return;

		final var attackerPvP = PlayerDataEvent.getPlayerData(attacker).pvp;
		final var defenderPvP = PlayerDataEvent.getPlayerData(defender).pvp;

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

		var attacker = e.getPlayer();

		if (PVP_BYPASS.contains(attacker)) return; //bypass protection

		for (final Player player : e.getBlockClicked().getLocation().getNearbyPlayers(4)) {
			final var pvpOfPlayer = PlayerDataEvent.getPlayerData(player).pvp;
			if (!pvpOfPlayer && !player.equals(attacker)) {
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

		var attacker = e.getPlayer();

		if (PVP_BYPASS.contains(attacker)) return; //bypass protection

		if (e.getItem().getType().equals(Material.FLINT_AND_STEEL) || e.getItem().getType().equals(Material.TNT)) {
			for (final Player player : e.getClickedBlock().getLocation().getNearbyPlayers(4)) {
				final var pvpOfPlayer = PlayerDataEvent.getPlayerData(player).pvp;
				if (!pvpOfPlayer && player != attacker) {
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
				final var pvpOfPlayer = PlayerDataEvent.getPlayerData(player).pvp;
				if (!pvpOfPlayer && player != attacker) {
					Utilities.sendMsg(e.getPlayer(), String.format(Language.PVP_OTHER_DISABLED_WARNING.getTranslation(e.getPlayer()), player.getName()));
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	// patch to protect against lingering potions
	@EventHandler
	private void patchLingerPotions(final EntityDamageByEntityEvent e) {

		if (!(e.getDamager() instanceof AreaEffectCloud)) return;
		final var areaEffectCloud = (AreaEffectCloud) e.getDamager();
		if (!(areaEffectCloud.getSource() instanceof Player)) return;

		final var attacker = (Player) areaEffectCloud.getSource();
		final var defender = (Player) e.getEntity();

		if (attacker == null) return;

		if (PVP_BYPASS.contains(attacker)) return; //bypass protection

		if (attacker.equals(defender)) return;

		final var attackerPvP = PlayerDataEvent.getPlayerData(attacker).pvp;
		final var defenderPvP = PlayerDataEvent.getPlayerData(defender).pvp;

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
}
