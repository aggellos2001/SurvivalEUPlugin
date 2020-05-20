package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import me.aggellos2001.survivaleuplugin.hooks.LuckPermsHook;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;


@CommandAlias("donation|donations|rank|ranks")
public final class DonationBenefitCommand extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

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

	private void givePotions(final Player player, final LuckPermsHook.Ranks playerRank) {
		if (playerRank.equals(LuckPermsHook.Ranks.EMERALD)) {
			player.addPotionEffects(Arrays.asList(this.EMERALD_EFFECTS));

		}
		if (playerRank.equals(LuckPermsHook.Ranks.DIAMOND)) {
			player.addPotionEffects(Arrays.asList(this.DIAMOND_EFFECTS));
		}
		if (playerRank.equals(LuckPermsHook.Ranks.IRON)) {
			player.addPotionEffects(Arrays.asList(this.IRON_EFFECTS));
		}
		if (playerRank.equals(LuckPermsHook.Ranks.COAL)) {
			player.addPotionEffects(Arrays.asList(this.COAL_EFFECTS));
		}
	}

	/**
	 * Package private methods
	 *
	 * @param player the player
	 * @return Returns true if the player has donation effects turned on
	 */
	static boolean hasDonationPotions(final Player player) {
		return HAS_EFFECTS_ON.contains(player);
	}

	@Default
	//@CommandCompletion("potion|potions")
	private void onCommand(final Player player) {
		Utilities.sendMsg(player, Language.DONATION_INFO.getTranslation(player));
	}

	@Subcommand("potion|potions")
	private void onPotions(final Player player) {
		final var playerRank = LuckPermsHook.getPlayerRank(player);
		if (playerRank.equals(LuckPermsHook.Ranks.DIAMOND) || playerRank.equals(LuckPermsHook.Ranks.EMERALD) || playerRank.equals(LuckPermsHook.Ranks.IRON) || playerRank.equals(LuckPermsHook.Ranks.COAL)) {

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
				givePotions(player, playerRank);
				HAS_EFFECTS_ON.add(player);
				Utilities.sendMsg(player,Language.DONATION_POTIONS_ENABLED.getTranslation(player));
			}
		} else {
			Utilities.sendMsg(player,Language.DONATION_NOT_DONATED_ACCESS_DENIED.getTranslation(player));
		}
	}
}
