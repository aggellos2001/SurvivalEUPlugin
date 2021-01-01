package me.aggellos2001.survivaleuplugin.ui;

import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;

import static me.aggellos2001.survivaleuplugin.utils.Utilities.colorize;

public class WarpUICommand extends PluginActivity {


	@EventHandler(priority = EventPriority.LOW)
	private void warpUI(final PlayerCommandPreprocessEvent event) {

		if (!event.getMessage().equalsIgnoreCase("/warp") && !event.getMessage().equalsIgnoreCase("/warps")) {
			return;
		}
		final var player = event.getPlayer();

		event.setCancelled(true);

		final Gui warpUI;
		final var warps = SurvivalEUPlugin.IEssentials.getWarps();

		if (warps.getCount() <= 9)
			warpUI = new Gui(1, colorize("&cWarps"));
		else if (warps.getCount() <= 18)
			warpUI = new Gui(2, colorize("&cWarps"));
		else if (warps.getCount() <= 27)
			warpUI = new Gui(3, colorize("&cWarps"));
		else if (warps.getCount() <= 36)
			warpUI = new Gui(4, colorize("&cWarps"));
		else if (warps.getCount() <= 45)
			warpUI = new Gui(5, colorize("&cWarps"));
		else
			warpUI = new Gui(6, colorize("&cWarps"));

		warpUI.setDefaultClickAction(event1 -> event1.setCancelled(true));

		for (final String warpName : warps.getList()) {
			if (warpName.toLowerCase().contains("pvp"))
				warpUI.addItem(
						ItemBuilder.from(Material.NETHERITE_SWORD)
								.setName(colorize("&e&l" + warpName))
								.glow(true)
								.asGuiItem(event1 -> {
									Bukkit.dispatchCommand(player, "warp " + warpName);
								})
				);
			else if (warpName.toLowerCase().contains("jail"))
				warpUI.addItem(
						ItemBuilder.from(Material.IRON_BARS)
								.setName(colorize("&e&l" + warpName))
								.glow(true)
								.asGuiItem(event1 -> {
									Bukkit.dispatchCommand(player, "warp " + warpName);
								})
				);
			else if (warpName.toLowerCase().contains("game"))
				warpUI.addItem(
						ItemBuilder.from(Material.EMERALD)
								.setName(colorize("&e&l" + warpName))
								.glow(true)
								.asGuiItem(event1 -> {
									Bukkit.dispatchCommand(player, "warp " + warpName);
								})
				);
			else {
				final var checkMaterial = Arrays.stream(Material.values()).filter(materials -> materials.name().toLowerCase().contains(warpName.toLowerCase())).findFirst();
				final Material material;
				material = checkMaterial.orElse(Material.MAP);
				warpUI.addItem(
						ItemBuilder.from(material)
								.setName(colorize("&e&l" + warpName))
								.glow(true)
								.asGuiItem(event1 -> {
									Bukkit.dispatchCommand(player, "warp " + warpName);
								})
				);
			}
		}

		warpUI.open(player);
	}
}
