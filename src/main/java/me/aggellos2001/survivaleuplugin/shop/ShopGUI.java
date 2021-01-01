package me.aggellos2001.survivaleuplugin.shop;

import me.aggellos2001.survivaleuplugin.hooks.EssentialsXHook;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static me.aggellos2001.survivaleuplugin.utils.Utilities.createRenamedItemStack;
import static org.bukkit.Bukkit.createInventory;


public class ShopGUI extends PluginActivity {


	public static final Set<Inventory> mainShopUISet = new HashSet<>();
	public static final Set<Inventory> purchaseShopUISet = new HashSet<>();


	public ShopGUI() {
		//Possible sizes 9, 18, 27, 36, 45,54
	}


	public static void openInventory(final Player player) {
		openMainShopMenu(0, player, null);
	}

	//============================== main shop =========================
	public static void openMainShopMenu(final int first, final Player player, Inventory inventory) {

		if (player == null) return;

		if (inventory == null) {
			inventory = createInventory(null, 54, Utilities.colorize(
					"#02A122&lSurvivalEU &e&lShop"));
		}

		if (first < 0) return;

		int last = first + 45;

		final var items = Shop.ShopPrices.values();

		if (first > items.length) {
			return;
		}
		if (last > items.length) {
			last = items.length;
		}

		inventory.clear();

		for (int i = first; i < last; i++) {
			final var item = Material.getMaterial(items[i].name());
			if (item == null) {
				continue;
			}
			final var itemToBeAdded = new ItemStack(item);
			final var buyPrice = items[i].buy;
			final var sellPrice = items[i].sell;
			itemToBeAdded.setLore(
					Arrays.asList(Utilities.colorize("#1eff00Buy price: #fffb00" + buyPrice + " #1dbf00$"),
							Utilities.colorize("#e30000Sell price: #fffb00" + sellPrice + " #1dbf00$"))
			);
			inventory.addItem(itemToBeAdded);
		}

		//adding menu items
		final var previousPage = createRenamedItemStack(Material.PAPER, "#e30000Previous page");

		final var nextPage = createRenamedItemStack(Material.PAPER, "#02a147Next page");

		final var homeButton = createRenamedItemStack(Material.BOOK, "#1a6debHome");

		final var exitButton = createRenamedItemStack(Material.BARRIER, "#e30000&lExit");


		inventory.setItem(48, previousPage);
		inventory.setItem(49, homeButton);
		inventory.setItem(50, nextPage);
		inventory.setItem(53, exitButton);

		if (!mainShopUISet.contains(inventory)) {
			//open inventory to player if inventory is not yet created
			player.openInventory(inventory);
		}


		// add inventory to Set if it doesn't exist
		mainShopUISet.add(inventory);
	}

	//========================== purchase menu ===========================
	public static void openPurchaseMenu(final Player player, final Material material) {

		final var inventory = createInventory(null, 9, Utilities.colorize("#02A122&lSurvivalEU &e&lShop"));


		//back button
		{
			final var backButton = createRenamedItemStack(Material.OAK_DOOR, "#f6ff00&lBack");

			inventory.setItem(0, backButton);
		}

		{
			//exit button
			final var exitButton = createRenamedItemStack(Material.BARRIER, "#e30000&lExit");

			inventory.setItem(8, new ItemStack(exitButton));
		}

		//=================  BUY, SELL and Middle Buttons  ===================
		final var maxStackOfMaterial = material.getMaxStackSize();
		final var shopPrice = Shop.ShopPrices.valueOf(material.name());

		//set middle slot with relevant material
		final var itemToBeAdded = createRenamedItemStack(material, null,
				"#1eff00Buy price: #fffb00" + shopPrice.buy + " #1dbf00$",
				"#e30000Sell price: #fffb00" + shopPrice.sell + " #1dbf00$");

		inventory.setItem(4, itemToBeAdded);


		//buy 1
		{
			final var buyOne = createRenamedItemStack(Material.GREEN_STAINED_GLASS, "#1eff00Buy&l 1x",
					"#fffb00" + shopPrice.buy + " #1dbf00$");

			inventory.setItem(2, buyOne);
		}

		//buy 64
		{

			final var buyFullStack = createRenamedItemStack(Material.GREEN_STAINED_GLASS,
					"#1eff00Buy&l " + maxStackOfMaterial + "x",
					"#fffb00" + shopPrice.buy * maxStackOfMaterial + " #1dbf00$"
			);

			inventory.setItem(3, buyFullStack);
		}

		//sell 1
		{
			final var sellOne = createRenamedItemStack(Material.RED_STAINED_GLASS_PANE,
					"#e30000Sell&l 1x",
					"#fffb00" + shopPrice.sell + " #1dbf00$");

			inventory.setItem(5, sellOne);
		}
		//sell all from inventory
		{
			final var sellInventory = createRenamedItemStack(Material.RED_STAINED_GLASS_PANE,
					"#e30000Sell&l Inventory",
					"#fffb00" + shopPrice.sell + " #1dbf00$ #fffb00each ");

			inventory.setItem(6, sellInventory);
		}


		player.openInventory(inventory);

		purchaseShopUISet.add(inventory);
	}


//===================  Main shop UI Handlers   ================

	@EventHandler
	private void mainShopMenuHandler(final InventoryClickEvent event) {

		final var inventory = event.getInventory();
		if (!mainShopUISet.contains(inventory)) return;

		event.setCancelled(true);

		//Handle menu buttons for front shop menu

		final var player = ((Player) event.getWhoClicked());
		if (event.getRawSlot() > inventory.getSize())
			return; //fixes NullPointerException from the line getItem(event.getRawSlot() if >inv size)
		final var clickedItem = event.getInventory().getItem(event.getRawSlot());


		//previous page.
		if (event.getRawSlot() == 48) {
			final var firstItem = inventory.getItem(0);
			if (firstItem == null) return;
			final var firstItemNumber = Arrays.asList(Shop.ShopPrices.values()).indexOf(Shop.ShopPrices.valueOf(firstItem.getType().name()));
			openMainShopMenu(firstItemNumber - 45, player, inventory);
			return;
		}
		//home button
		if (event.getRawSlot() == 49) {
			openMainShopMenu(0, player, inventory);
			return;
		}
		//next page
		if (event.getRawSlot() == 50) {
			final var lastItem = inventory.getItem(44);
			if (lastItem == null) return;
			final var lastItemNumber = Arrays.asList(Shop.ShopPrices.values()).indexOf(Shop.ShopPrices.valueOf(lastItem.getType().name()));
			openMainShopMenu(lastItemNumber + 1, player, inventory);
			return;
		}
		//exit
		if (event.getRawSlot() == 53) {
			player.closeInventory();
			return;
		}
		//else open purchase menu
		if (clickedItem == null) return;

		player.closeInventory();

		openPurchaseMenu(player, clickedItem.getType());


	}


//===========================Purchase menu handlers =========================


	@EventHandler
	private void purchaseMenuHandler(final InventoryClickEvent event) {

		final var inventory = event.getInventory();
		if (!purchaseShopUISet.contains(inventory)) return;

		event.setCancelled(true);

		final var player = ((Player) event.getWhoClicked());
		if (event.getSlotType() == InventoryType.SlotType.CONTAINER)

			if (event.getRawSlot() == 0) {
				//close inventory first to clear it from purchaseShopUISet
				player.closeInventory();
				openMainShopMenu(0, player, null);
				return;
			}
		if (event.getRawSlot() == 8) {
			player.closeInventory();
			return;
		}
		//Buy - sell handlers
		var clickedItem = inventory.getItem(4);
		if (clickedItem == null) return;

		final var shopPrice = Shop.ShopPrices.valueOf(clickedItem.getType().name());
		final var material = clickedItem.getType();
		final var maxStack = Material.valueOf(shopPrice.name()).getMaxStackSize();

		//buy 1 button
		{
			if (event.getRawSlot() == 2) {
				if (shopPrice.buy == 0) {
					Utilities.sendMsg(player, Language.SHOP_CANNOT_BUY.getTranslation(player));
					return;
				}

				final var amountToPay = shopPrice.buy * 1;
				if (!EssentialsXHook.hasEnough(player, amountToPay)) {
					Utilities.sendMsg(player, Language.NO_MONEY.getTranslation(player));
					return;
				}
				EssentialsXHook.subtractPlayerBalance(player, amountToPay);
				final var map = player.getInventory().addItem(new ItemStack(Material.valueOf(shopPrice.name()), 1));
				Utilities.sendMsg(player, String.format(Language.SHOP_SUCCESS_BUY.getTranslation(player), 1, shopPrice.name(), amountToPay));
				//if ivnentory was full throw to the ground extra items
				if (!map.isEmpty()) {
					map.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
					Utilities.sendMsg(player, Language.SHOP_NOT_ENOUGH_SPACE.getTranslation(player));
				}
			}
		}

		//buy full stack button
		{
			if (event.getRawSlot() == 3) {
				if (shopPrice.buy == 0) {
					Utilities.sendMsg(player, Language.SHOP_CANNOT_BUY.getTranslation(player));
					return;
				}
				final var amountToPay = shopPrice.buy * maxStack;
				if (!EssentialsXHook.hasEnough(player, amountToPay)) {
					Utilities.sendMsg(player, Language.NO_MONEY.getTranslation(player));
					return;
				}
				EssentialsXHook.subtractPlayerBalance(player, amountToPay);
				final var map = player.getInventory().addItem(new ItemStack(Material.valueOf(shopPrice.name()), maxStack));
				Utilities.sendMsg(player, String.format(Language.SHOP_SUCCESS_BUY.getTranslation(player), maxStack, shopPrice.name(), amountToPay));
				//if ivnentory was full throw to the ground extra items
				if (!map.isEmpty()) {
					map.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
					Utilities.sendMsg(player, Language.SHOP_NOT_ENOUGH_SPACE.getTranslation(player));
				}
			}
		}
		//sell x1 button handler
		{
			if (event.getRawSlot() == 5) {
				if (shopPrice.sell == 0) {
					Utilities.sendMsg(player, Language.SHOP_CANNOT_SELL.getTranslation(player));
					return;
				}
				final var playerInventory = player.getInventory();
				var amountOfItemOnInventory = 0;
				//check every slot for the item in PLAYERS inventory
				for (final ItemStack slot : playerInventory.getContents()) {
					//slot may be null
					if (slot == null) continue;

					if (slot.getType().equals(material)) {
						amountOfItemOnInventory += 1; //only 1 because button is x1
						slot.setAmount(slot.getAmount() - 1);
						// break when first item is found (button is sell x1)
						break;
					}
				}
				if (amountOfItemOnInventory == 0) {
					Utilities.sendMsg(player, String.format(Language.SHOP_SELL_INVENTORY_NO_ITEM.getTranslation(player), material.name()));
					return;
				}
				final var amountToGetPaid = amountOfItemOnInventory * shopPrice.sell;
				EssentialsXHook.addPlayerBalance(player, amountToGetPaid);
				Utilities.sendMsg(player, String.format(Language.SHOP_SELL_SUCCESS.getTranslation(player), amountOfItemOnInventory, material.name(), Math.round(amountToGetPaid * 100.0) / 100.0));

			}
		}

		// Sell inventory of the item button
		{
			if (event.getRawSlot() == 6) {
				if (shopPrice.sell == 0) {
					Utilities.sendMsg(player, Language.SHOP_CANNOT_SELL.getTranslation(player));
					return;
				}
				final var playerInventory = player.getInventory();
				var amountOfItemOnInventory = 0;
				//check every slot for the item in PLAYERS inventory
				for (final ItemStack slot : playerInventory.getContents()) {
					//slot may be null
					if (slot == null) continue;

					if (slot.getType().equals(material)) {
						amountOfItemOnInventory += slot.getAmount();
						slot.setAmount(-1);
					}
				}
				if (amountOfItemOnInventory == 0) {
					Utilities.sendMsg(player, String.format(Language.SHOP_SELL_INVENTORY_NO_ITEM.getTranslation(player), material.name()));
					return;
				}
				final var amountToGetPaid = amountOfItemOnInventory * shopPrice.sell;
				EssentialsXHook.addPlayerBalance(player, amountToGetPaid);
				Utilities.sendMsg(player, String.format(Language.SHOP_SELL_SUCCESS.getTranslation(player), amountOfItemOnInventory, material.name(), Math.round(amountToGetPaid * 100.0) / 100.0));

			}
		}


	}


	//==========Listener for all UIS

	@EventHandler
	private void removeFromHash(final InventoryCloseEvent event) {

		final var inventory = event.getInventory();
		//remove inventory - checks if it's already there
		mainShopUISet.remove(inventory);
		purchaseShopUISet.remove(inventory);
	}

	@EventHandler
	private void disableDrag(final InventoryDragEvent event) {

		final var inventory = event.getInventory();

		//check if inventory is not shop GUI both main menu and purchase one
		if (!mainShopUISet.contains(inventory) | !purchaseShopUISet.contains(inventory)) {
			return;
		}

		event.setCancelled(true);
	}
}
