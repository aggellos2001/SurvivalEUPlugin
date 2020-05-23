package me.aggellos2001.survivaleuplugin.modules;


import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Tameable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public final class ProtectTamedEvent extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return true;
	}

	@Override
	public boolean hasCommands() {
		return false;
	}


	@EventHandler(ignoreCancelled = true)
	private void onPlayerKillingTamedAnimal(final EntityDamageByEntityEvent e) {

		final var attacker = e.getDamager();
		if (!(e.getEntity() instanceof Tameable)) return;

		final var defenderEntity = (Tameable) e.getEntity();
		final var defenderEntityOwnerUniqueId = defenderEntity.getOwnerUniqueId();
		if (defenderEntityOwnerUniqueId == null) return;

		if (attacker instanceof Player) {
			final var player = (Player) attacker;
			if (player.isOp()) return;
			if (!defenderEntityOwnerUniqueId.equals(player.getUniqueId())) {
				Utilities.sendMsg(player,Language.TAMED_HARM_DENIED.getTranslation(player));
				e.setCancelled(true);
			}
		}
		if (attacker instanceof Projectile) {
			final var projectile = (Projectile) attacker;
			if (!(projectile.getShooter() instanceof Player)) return;
			final var arrowShooter = (Player) projectile.getShooter();
			if (!defenderEntityOwnerUniqueId.equals(arrowShooter.getUniqueId())) {
				Utilities.sendMsg(arrowShooter,Language.TAMED_HARM_DENIED.getTranslation(arrowShooter));
				e.setCancelled(true);
			}
		}
	}

	@EventHandler(ignoreCancelled = true)
	private void onPlacingLavaNearTamed(final PlayerBucketEmptyEvent e) {
		final var playerUUID = e.getPlayer().getUniqueId();
		if (!e.getBucket().equals(Material.LAVA_BUCKET)) return;
		for (final LivingEntity entity : e.getBlockClicked().getLocation().getNearbyLivingEntities(5)) {
			if (!(entity instanceof Tameable)) continue;
			final var tamedEntity = (Tameable) entity;
			if (tamedEntity.isTamed() && tamedEntity.getOwnerUniqueId() != playerUUID) {
				Utilities.sendMsg(e.getPlayer(),Language.TAMED_PUT_LAVA_DENIED.getTranslation(e.getPlayer()));
			}
		}
	}
}
