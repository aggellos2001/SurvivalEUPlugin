package me.aggellos2001.survivaleuplugin.hooks;

import com.earth2me.essentials.IEssentials;
import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.util.UUID;

public final class EssentialsXHook {

	private static final IEssentials ESSENTIALS = SurvivalEUPlugin.IEssentials;

	public static void subtractPlayerBalance(final Player player, final double amount) {
		final var user = ESSENTIALS.getUser(player);
		try {
			Economy.subtract(user, new BigDecimal(amount));
		} catch (final NoLoanPermittedException ignored) {

		}
	}

	public static void addPlayerBalance(final Player player, final double amount) {
		try {
			Economy.add(player.getUniqueId(), new BigDecimal(amount));
		} catch (NoLoanPermittedException | UserDoesNotExistException ignored) {
		}
	}

	public static double getPlayerBalance(final Player player) {
		final var user = ESSENTIALS.getUser(player);
		return Economy.getMoneyExact(user).doubleValue();
	}

	public static boolean hasEnough(final Player player, final double amount) {
		final var user = ESSENTIALS.getUser(player);
		return Economy.hasEnough(user, new BigDecimal(amount));
	}

	public static boolean hasMore(final Player player, final double amount) {
		final var user = ESSENTIALS.getUser(player);
		return Economy.hasMore(user, new BigDecimal(amount));
	}

	public static boolean hasLess(final Player player, final double amount) {
		final var user = ESSENTIALS.getUser(player);
		return Economy.hasLess(user, new BigDecimal(amount));
	}

	public static boolean isAFK(final Player player) {
		return ESSENTIALS.getUser(player).isAfk();
	}

	public static String getEssentialsPlayerName(final UUID uuid) {
		final var user = ESSENTIALS.getUser(uuid);
		if (user == null) return null;
		return ESSENTIALS.getUser(uuid).getName();
	}
}
