package me.aggellos2001.survivaleuplugin.utils;

import co.aikar.commands.ConditionFailedException;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.shop.Shop;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomCommandConditions {

	public static final Map<Player, Long> COOLDOWN_MAP = new HashMap<>();

	public static void registerConditions() {

		final var manager = SurvivalEUPlugin.COMMAND_MANAGER;

		/*
		 * Adds @Conditions("ConsoleOrOp") for parameter and class/method usage
		 */
		manager.getCommandConditions().addCondition("ConsoleOrOp", context -> {
			final var issuer = context.getIssuer().getPlayer();
			if (issuer == null) return;
			if (!issuer.isOp()) {
				throw new ConditionFailedException("You must be op to run this command!");
			}
		});

		manager.getCommandConditions().addCondition(CommandSender.class, "ConsoleOrOp", (context, execContext, value) -> {
			final var issuer = context.getIssuer().getPlayer();
			if (issuer == null) return;
			if (!issuer.isOp()) {
				throw new ConditionFailedException("You must be op to run this command!");
			}
		});

		/*
		 * Adds @Conditions("cooldown:seconds=x) entire class/method command cooldown
		 */

		manager.getCommandConditions().addCondition("cooldown", context -> {

			var player = context.getIssuer().getPlayer();
			if (player == null) return;
			if (player.isOp()) return;
			if (!context.hasConfig("seconds")) return;

			if (COOLDOWN_MAP.getOrDefault(player, null) != null) {
				var lastTime = COOLDOWN_MAP.get(player);
				var difference = System.currentTimeMillis() - lastTime;
				var cooldown = context.getConfigValue("seconds", 0) * 1000;
				if (difference < cooldown) {
					throw new ConditionFailedException(String.format("Wait %s seconds before using this command again!", (cooldown - difference) / 1000));
				} else {
					COOLDOWN_MAP.put(player, System.currentTimeMillis());
				}
			} else {
				COOLDOWN_MAP.put(player, System.currentTimeMillis());
			}
		});

		/*
			Adds tab completer for shop command
		 */
		manager.getCommandCompletions().registerCompletion("shopItems", context -> Stream.of(Shop.ShopPrices.values()).map(Enum::name).collect(Collectors.toList()));
	}
}
