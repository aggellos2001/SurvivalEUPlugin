package me.aggellos2001.survivaleuplugin.ui;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Math.pow;
import static me.aggellos2001.survivaleuplugin.utils.Utilities.createRenamedItemStack;
import static org.bukkit.Bukkit.createInventory;

@CommandAlias("music")
public final class PlayMusicCommand extends PluginActivity {

	public static final Set<Inventory> openMenus = new HashSet<>();

	private void stopAllSounds(final Player player) {
		for (final Sound sound : Sound.values()) {
			player.stopSound(sound);
		}
	}

	@Default
	public static void openUI(final Player player) {

		final var inventory = createInventory(null, 36, Utilities.colorize(
				"#02A122&lSurvivalEU #c98d00&lMusic"));


		//Put items to inventory
		{
			//slot 0
			final var disc13 = createRenamedItemStack(Material.MUSIC_DISC_13, "#00f7ff&lDisc 13", (String[]) null);
			inventory.addItem(disc13);
		}
		{
			final var discCat = createRenamedItemStack(Material.MUSIC_DISC_CAT, "#00f7ff&lDisc Cat",(String[]) null);
			inventory.addItem(discCat);
		}

		{
			final var discBlocks = createRenamedItemStack(Material.MUSIC_DISC_BLOCKS, "#00f7ff&lDisc Blocks", (String[]) null);
			inventory.addItem(discBlocks);
		}
		{
			final var discChirp = createRenamedItemStack(Material.MUSIC_DISC_CHIRP, "#00f7ff&lDisc Chirp", (String[]) null);
			inventory.addItem(discChirp);
		}
		{
			final var discFar = createRenamedItemStack(Material.MUSIC_DISC_FAR, "#00f7ff&lDisc Far", (String[]) null);
			inventory.addItem(discFar);
		}
		{
			final var discMall = createRenamedItemStack(Material.MUSIC_DISC_MALL, "#00f7ff&lDisc Mall", (String[]) null);
			inventory.addItem(discMall);
		}
		{
			final var discMellohi = createRenamedItemStack(Material.MUSIC_DISC_MELLOHI, "#00f7ff&lDisc Mellohi", (String[]) null);
			inventory.addItem(discMellohi);
		}
		{
			final var discStal = createRenamedItemStack(Material.MUSIC_DISC_STAL, "#00f7ff&lDisc Stal", (String[]) null);
			inventory.addItem(discStal);
		}
		{
			final var discStrad = createRenamedItemStack(Material.MUSIC_DISC_STRAD, "#00f7ff&lDisc Strad", (String[]) null);
			inventory.addItem(discStrad);
		}
		{
			final var discWard = createRenamedItemStack(Material.MUSIC_DISC_WARD, "#00f7ff&lDisc Ward", (String[]) null);
			inventory.addItem(discWard);
		}
		{
			final var disc11 = createRenamedItemStack(Material.MUSIC_DISC_11, "#00f7ff&lDisc 11", (String[]) null);
			inventory.addItem(disc11);
		}
		{
			final var discWait = createRenamedItemStack(Material.MUSIC_DISC_WAIT, "#00f7ff&lDisc Wait", (String[]) null);
			inventory.addItem(discWait);
		}
		{
			final var discPigStep = createRenamedItemStack(Material.MUSIC_DISC_PIGSTEP, "#00f7ff&lDisc Pigstep", (String[]) null);
			inventory.addItem(discPigStep);
		}

		//=====minecraft music slot 13 + ======
		{

			final var creativeMusic = createRenamedItemStack(Material.MUSIC_DISC_PIGSTEP, "#00f7ff&lCreative Music", (String[]) null);
			inventory.addItem(creativeMusic);
		}
		{
			final var creditsMusic = createRenamedItemStack(Material.MUSIC_DISC_PIGSTEP, "#00f7ff&lCredits Music", (String[]) null);
			inventory.addItem(creditsMusic);
		}
		{
			final var dragonMusic = createRenamedItemStack(Material.MUSIC_DISC_PIGSTEP, "#00f7ff&lDragon Music", (String[]) null);
			inventory.addItem(dragonMusic);
		}
		{
			final var endMusic = createRenamedItemStack(Material.MUSIC_DISC_PIGSTEP, "#00f7ff&lEnd Music", (String[]) null);
			inventory.addItem(endMusic);
		}
		{
			final var gameMusic = createRenamedItemStack(Material.MUSIC_DISC_PIGSTEP, "#00f7ff&lGame Music", (String[]) null);
			inventory.addItem(gameMusic);
		}
		{
			final var menuMusic = createRenamedItemStack(Material.MUSIC_DISC_PIGSTEP, "#00f7ff&lMenu Music", (String[]) null);
			inventory.addItem(menuMusic);
		}

		//===stop and exit

		{
			final var stopMusic = createRenamedItemStack(Material.BARRIER, "#d10000&lStop music");
			inventory.setItem(31, stopMusic);
		}

		{
			final var exitMenu = createRenamedItemStack(Material.OAK_DOOR, "#025724&lExit");
			inventory.setItem(35, exitMenu);
		}

		openMenus.add(inventory);
		player.openInventory(inventory);

	}

	@EventHandler
	private void removeInventoryOnClose(final InventoryCloseEvent event) {
		openMenus.remove(event.getInventory());
	}

	@EventHandler
	private void removeDrag(final InventoryDragEvent event) {
		if (!openMenus.contains(event.getInventory())) {
			return;
		}
		event.setCancelled(true);
	}

	@EventHandler
	private void onItemClick(final InventoryClickEvent event) {

		final var inventory = event.getInventory();

		if (!openMenus.contains(inventory)) {
			return;
		}

		event.setCancelled(true);

		final var player = ((Player) event.getWhoClicked());
		final var playerLoc = player.getLocation();

		//ignore empty slots
		if (inventory.getItem(event.getRawSlot()) == null) {
			return;
		}

		//stop all sounds button
		if (event.getRawSlot() == 31) {
			stopAllSounds(player);
			return;
		}
		//exit inventory
		if (event.getRawSlot() == 35) {
			player.closeInventory();
			return;
		}

		//else stop all playing sounds before playing the sound
		stopAllSounds(player);

		if (event.getRawSlot() == 0) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_13, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 1) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_CAT, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 2) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_BLOCKS, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 3) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_CHIRP, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 4) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_FAR, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 5) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_MALL, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 6) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_MELLOHI, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 7) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_STAL, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 8) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_STRAD, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 9) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_WARD, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 10) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_11, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 11) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_WAIT, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 12) {
			player.playSound(playerLoc, Sound.MUSIC_DISC_PIGSTEP, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 13) {
			player.playSound(playerLoc, Sound.MUSIC_CREATIVE, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 14) {
			player.playSound(playerLoc, Sound.MUSIC_CREDITS, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 15) {
			player.playSound(playerLoc, Sound.MUSIC_DRAGON, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 16) {
			player.playSound(playerLoc, Sound.MUSIC_END, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 17) {
			player.playSound(playerLoc, Sound.MUSIC_GAME, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
		if (event.getRawSlot() == 18) {
			player.playSound(playerLoc, Sound.MUSIC_MENU, 3.0F, (float) pow(2.0, ((double) 1 - 12.0) / 12.0));
			return;
		}
	}
}
