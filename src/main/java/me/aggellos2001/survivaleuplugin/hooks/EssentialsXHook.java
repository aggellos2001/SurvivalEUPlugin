package me.aggellos2001.survivaleuplugin.hooks;

import com.earth2me.essentials.api.Economy;
import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.entity.Player;

import java.math.BigDecimal;

public final class EssentialsXHook {

	public static void subtractPlayerBalance(final Player player, final double amount) {
		try {
			Economy.substract(player.getName(), new BigDecimal(amount));
		} catch (UserDoesNotExistException | NoLoanPermittedException e) {
			e.printStackTrace();
		}
	}

	public static void addPlayerBalance(final Player player, final double amount) {
		try {
			Economy.add(player.getName(), new BigDecimal(amount));
		} catch (UserDoesNotExistException | NoLoanPermittedException e) {
			e.printStackTrace();
		}
	}

	public static double getPlayerBalance(final Player player) {
		try {
			return Economy.getMoneyExact(player.getName()).doubleValue();
		} catch (final UserDoesNotExistException e) {
			e.printStackTrace();
			return 0D;
		}

	}

	public static boolean hasEnough(final Player player, final double amount) {
		try {
			return Economy.hasEnough(player.getName(), new BigDecimal(amount));
		} catch (final UserDoesNotExistException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean hasMore(final Player player, final double amount) {
		try {
			return Economy.hasMore(player.getName(), new BigDecimal(amount));
		} catch (final UserDoesNotExistException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean hasLess(final Player player, final double amount) {
		try {
			return Economy.hasLess(player.getName(), new BigDecimal(amount));
		} catch (final UserDoesNotExistException e) {
			e.printStackTrace();
			return false;
		}
	}
}
