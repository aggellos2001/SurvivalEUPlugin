package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.aggellos2001.survivaleuplugin.hooks.LuckPermsHook;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@CommandAlias("donation|donations|rank|ranks")
public final class DonationBenefitCommand extends PluginActivity {

	private static final List<Player> HAS_EFFECTS_ON = new ArrayList<>();

	private final PotionEffect[] EMERALD_EFFECTS = new PotionEffect[]{
			new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2),
			new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.LUCK, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0),
			new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0),
			new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0),
			new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0),
	};

	private final PotionEffect[] DIAMOND_EFFECTS = new PotionEffect[]{
			new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.LUCK, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.NIGHT_VISION, Integer.MAX_VALUE, 0),
			new PotionEffect(PotionEffectType.INCREASE_DAMAGE, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.WATER_BREATHING, Integer.MAX_VALUE, 0),
	};
	private final PotionEffect[] IRON_EFFECTS = new PotionEffect[]{
			new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1),
			new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0),
			new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0),
			new PotionEffect(PotionEffectType.LUCK, Integer.MAX_VALUE, 0),
	};
	private final PotionEffect[] COAL_EFFECTS = new PotionEffect[]{
			new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 0),
			new PotionEffect(PotionEffectType.FAST_DIGGING, Integer.MAX_VALUE, 0),
			new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, Integer.MAX_VALUE, 0),
	};

	private void givePotions(final Player player) {
		if (player.hasPermission("group.emerald")) {
			player.addPotionEffects(Arrays.asList(this.EMERALD_EFFECTS));
			return;
		}
		if (player.hasPermission("group.diamond")) {
			player.addPotionEffects(Arrays.asList(this.DIAMOND_EFFECTS));
			return;
		}
		if (player.hasPermission("group.iron")) {
			player.addPotionEffects(Arrays.asList(this.IRON_EFFECTS));
			return;
		}
		if (player.hasPermission("group.coal")) {
			player.addPotionEffects(Arrays.asList(this.COAL_EFFECTS));
		}
	}

	/**
	 * Package private methods
	 *
	 * @param player the player
	 * @return Returns true if the player has donation effects turned on
	 */
	public static boolean hasDonationPotions(final Player player) {
		return HAS_EFFECTS_ON.contains(player);
	}

	@Default
	//@CommandCompletion("potion|potions")
	private void onCommand(final Player player) {
		Utilities.sendMsg(player, Language.DONATION_INFO.getTranslation(player));
	}

	@Subcommand("potion|potions")
	private void onPotions(final Player player) {
		if (
				!player.hasPermission("group.emerald") &
						!player.hasPermission("group.diamond") &
						!player.hasPermission("group.iron") &
						!player.hasPermission("group.coal")
		) {
			Utilities.sendMsg(player, Language.DONATION_NOT_DONATED_ACCESS_DENIED.getTranslation(player));
			return;
		}

		if (PvPCommand.hasPvPOn(player)) {
			Utilities.sendMsg(player, Language.DONATION_POTION_ENABLE_FAIL_PVP.getTranslation(player));
		}

		if (HAS_EFFECTS_ON.contains(player)) {
			for (final PotionEffect potionEffect : player.getActivePotionEffects()) {
				final var type = potionEffect.getType();
				player.removePotionEffect(type);
			}
			Utilities.sendMsg(player, Language.DONATION_POTIONS_DISABLED.getTranslation(player));
			HAS_EFFECTS_ON.remove(player);
		} else {
			givePotions(player);
			HAS_EFFECTS_ON.add(player);
			Utilities.sendMsg(player, Language.DONATION_POTIONS_ENABLED.getTranslation(player));
		}
	}
}
