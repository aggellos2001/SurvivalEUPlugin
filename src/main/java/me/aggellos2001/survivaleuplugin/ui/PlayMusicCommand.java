package me.aggellos2001.survivaleuplugin.ui;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfmsg.base.internal.Format;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.pow;

@CommandAlias("music")
public final class PlayMusicCommand extends PluginActivity {

	public static final Set<Inventory> openMenus = new HashSet<>();
	private static final Material[] musicList = {
			Material.MUSIC_DISC_13, Material.MUSIC_DISC_CAT, Material.MUSIC_DISC_BLOCKS, Material.MUSIC_DISC_CHIRP,
			Material.MUSIC_DISC_FAR, Material.MUSIC_DISC_MALL, Material.MUSIC_DISC_MELLOHI, Material.MUSIC_DISC_STAL,
			Material.MUSIC_DISC_STRAD, Material.MUSIC_DISC_WARD, Material.MUSIC_DISC_11, Material.MUSIC_DISC_WAIT,
			Material.MUSIC_DISC_PIGSTEP
	};

	private void stopAllSounds(final Player player) {
		for (final Sound sound : Sound.values()) {
			player.stopSound(sound);
		}
	}

	private Sound getSoundFromDisc(final Material disc) {
		switch (disc) {
			case MUSIC_DISC_13:
				return Sound.MUSIC_DISC_13;
			case MUSIC_DISC_CAT:
				return Sound.MUSIC_DISC_CAT;
			case MUSIC_DISC_BLOCKS:
				return Sound.MUSIC_DISC_BLOCKS;
			case MUSIC_DISC_CHIRP:
				return Sound.MUSIC_DISC_FAR;
			case MUSIC_DISC_MALL:
				return Sound.MUSIC_DISC_MALL;
			case MUSIC_DISC_MELLOHI:
				return Sound.MUSIC_DISC_MELLOHI;
			case MUSIC_DISC_STAL:
				return Sound.MUSIC_DISC_STAL;
			case MUSIC_DISC_STRAD:
				return Sound.MUSIC_DISC_STRAD;
			case MUSIC_DISC_WARD:
				return Sound.MUSIC_DISC_WARD;
			case MUSIC_DISC_11:
				return Sound.MUSIC_DISC_11;
			case MUSIC_DISC_WAIT:
				return Sound.MUSIC_DISC_WAIT;
			case MUSIC_DISC_PIGSTEP:
				return Sound.MUSIC_DISC_PIGSTEP;
		}
		return Sound.MUSIC_DISC_13;
	}

	@Default
	public void openUI(final Player player) {

		final var menu = new Gui(4, Utilities.colorize("<#02A122>&lSurvivalEU <#c98d00>&lMusic"));
		menu.setDefaultClickAction(event -> event.setCancelled(true));

		for (final Material disc : musicList) {
			final var discName = disc.name().replace('_', ' ');
			menu.addItem(ItemBuilder.from(disc).setName(Utilities.colorize("<#00f7ff>&l" + discName, Format.ITALIC))
					.addItemFlags(ItemFlag.values())
					.asGuiItem(event -> {
						stopAllSounds(player);
						player.playSound(event.getWhoClicked().getLocation(), getSoundFromDisc(disc), 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
					}));
		}

		menu.addItem(ItemBuilder.from(Material.MUSIC_DISC_PIGSTEP).setName(Utilities.colorize("<#00f7ff>&lCreative"))
				.addItemFlags(ItemFlag.values())
				.asGuiItem(event -> {
					stopAllSounds(player);
					player.playSound(event.getWhoClicked().getLocation(), Sound.MUSIC_CREATIVE, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
				}));

		menu.addItem(ItemBuilder.from(Material.MUSIC_DISC_PIGSTEP).setName(Utilities.colorize("<#00f7ff>&lCredits"))
				.addItemFlags(ItemFlag.values())
				.asGuiItem(event -> {
					stopAllSounds(player);
					player.playSound(event.getWhoClicked().getLocation(), Sound.MUSIC_CREDITS, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
				}));
		menu.addItem(ItemBuilder.from(Material.MUSIC_DISC_PIGSTEP).setName(Utilities.colorize("<#00f7ff>&lDragon music"))
				.addItemFlags(ItemFlag.values())
				.asGuiItem(event -> {
					stopAllSounds(player);
					player.playSound(event.getWhoClicked().getLocation(), Sound.MUSIC_DRAGON, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
				}));
		menu.addItem(ItemBuilder.from(Material.MUSIC_DISC_PIGSTEP).setName(Utilities.colorize("<#00f7ff>&lEnd music"))
				.addItemFlags(ItemFlag.values())
				.asGuiItem(event -> {
					stopAllSounds(player);
					player.playSound(event.getWhoClicked().getLocation(), Sound.MUSIC_END, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
				}));
		menu.addItem(ItemBuilder.from(Material.MUSIC_DISC_PIGSTEP).setName(Utilities.colorize("<#00f7ff>&lGame music"))
				.addItemFlags(ItemFlag.values())
				.asGuiItem(event -> {
					stopAllSounds(player);
					player.playSound(event.getWhoClicked().getLocation(), Sound.MUSIC_GAME, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
				}));
		menu.addItem(ItemBuilder.from(Material.MUSIC_DISC_PIGSTEP).setName(Utilities.colorize("<#00f7ff>&lMenu music"))
				.addItemFlags(ItemFlag.values())
				.asGuiItem(event -> {
					stopAllSounds(player);
					player.playSound(event.getWhoClicked().getLocation(), Sound.MUSIC_MENU, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
				}));

		menu.setItem(4, 5, ItemBuilder.from(Material.BARRIER).glow(true)
				.setName(Utilities.colorize("&c&lStop music"))
				.asGuiItem(event -> stopAllSounds(((Player) event.getWhoClicked())))
		);

		menu.open(player);
	}
}
