package me.aggellos2001.survivaleuplugin.holidayevents;

import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.concurrent.ThreadLocalRandom;

public class FestiveFireworks extends PluginActivity {

	public static final ThreadLocalRandom random = ThreadLocalRandom.current();

	@EventHandler(ignoreCancelled = true)
	private void dropFirework(final EntityDeathEvent e) {
		final var deadEntity = e.getEntity();
		if (deadEntity.getKiller() == null) {
			return;
		}
		final var percentage = random.nextInt(100);
		if (percentage < 70) // 30% change that a dead mob will drop the firework
			return;
		final var firework = ItemBuilder.from(Material.FIREWORK_ROCKET)
				.setName(Utilities.colorize("<#c54245>&lFestive Firework 2020-2021!"))
				.setLore(Utilities.colorize(
						"<#00b32c>Drops only from dead mobs!",
						"<#0def42>Event active during Christmas",
						"<#0def42>and New Year holidays!"))
				.addItemFlags(ItemFlag.values())
				.glow(true).asGuiItem().getItemStack();
		final var fireworkMeta = (FireworkMeta) firework.getItemMeta();
		fireworkMeta.addEffect(getRandomEffect());
		fireworkMeta.setPower(random.nextInt(1, 4));
		firework.setItemMeta(fireworkMeta);
		deadEntity.getWorld().dropItemNaturally(deadEntity.getLocation(), firework);
	}

	@EventHandler(ignoreCancelled = true)
	private void onUsingFireWork(final PlayerInteractEvent e) {
		final var material = e.getMaterial();
		final var player = e.getPlayer();
		if (e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if (material != Material.FIREWORK_ROCKET) {
			return;
		}
		if (player.hasCooldown(material)) return;
		e.getPlayer().setCooldown(material, Utilities.TicksDuration.SECOND.getTime(1));
	}

	private FireworkEffect getRandomEffect() {
		final var builder = FireworkEffect.builder();
		switch (random.nextInt(5)) {
			case 0: {
				builder.with(FireworkEffect.Type.BALL);
				break;
			}
			case 1: {
				builder.with(FireworkEffect.Type.BALL_LARGE);
				break;
			}
			case 2: {
				builder.with(FireworkEffect.Type.CREEPER);
				break;
			}
			case 3: {
				builder.with(FireworkEffect.Type.BURST);
				break;
			}
			case 4: {
				builder.with(FireworkEffect.Type.STAR);
				break;
			}
		}
		final var colors = random.nextInt(1, 9); //1-8
		for (int i = 0; i < colors; i++) {
			builder.withColor(Color.fromBGR(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		}
		final var fadeColors = random.nextInt(0, 9); // 1-8
		for (int i = 0; i < fadeColors; i++) {
			builder.withFade(Color.fromBGR(random.nextInt(256), random.nextInt(256), random.nextInt(256)));
		}
		builder.trail(random.nextBoolean());
		builder.flicker(random.nextBoolean());
		return builder.build();
	}
}
