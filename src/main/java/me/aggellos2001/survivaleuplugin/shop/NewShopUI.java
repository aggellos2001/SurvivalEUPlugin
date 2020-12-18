package me.aggellos2001.survivaleuplugin.shop;

import co.aikar.commands.ConditionFailedException;
import me.aggellos2001.survivaleuplugin.hooks.EssentialsXHook;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

public class NewShopUI {

	//private final PaginatedGui mainGUI;

	public static void mainUI(final Player player) {

		//initializes main UI
		final var mainGUI = new PaginatedGui(6, 45, Utilities.colorize("<g:#ff0000:#001fff>SurvivalEU Shop"));
		//add default action to cancel any events to prevent theft from the menu etc
		mainGUI.setDefaultClickAction(event -> {
			event.setCancelled(true);
		});

		//next btn
		mainGUI.setItem(6, 6, ItemBuilder.from(Material.LIME_DYE)
				.setName(Utilities.colorize("&a&lNext"))
				.asGuiItem(event -> {
					mainGUI.next();
				}));

		//previous btn
		mainGUI.setItem(6, 4, ItemBuilder.from(Material.GRAY_DYE)
				.setName(Utilities.colorize("&c&lPrevious"))
				.asGuiItem(event -> {
					mainGUI.previous();
				}));

		//exit btn
		mainGUI.setItem(6, 9, ItemBuilder.from(Material.BARRIER)
				.setName(Utilities.colorize("&4&lExit")).glow(true)
				.asGuiItem(event -> {
					event.getWhoClicked().closeInventory(InventoryCloseEvent.Reason.PLUGIN);
				})
		);

		// add items to UI
		for (final Shop.ShopPrices shopMaterial : Shop.ShopPrices.values()) {
			final var item = Material.getMaterial(shopMaterial.name());
			if (item == null) {
				throw new ConditionFailedException("Something went wrong!");
			}
			final var guiItem = ItemBuilder.from(item)
					.setLore(Utilities.colorize("&aBuy price: &f" + shopMaterial.buy + "$"),
							Utilities.colorize("&eSell price: &f" + shopMaterial.sell + "$")
					).asGuiItem();
			guiItem.setAction(event -> {
				final var clickedMat = event.getInventory().getItem(event.getSlot());
				if (clickedMat == null) return;
				//create and open the buy sell UI
				createBuySellUI(((Player) event.getWhoClicked()), mainGUI, clickedMat.getType());
			});
			mainGUI.addItem(guiItem);
		}
		mainGUI.getFiller().fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").asGuiItem());

		mainGUI.open(player);

	}

	private static void createBuySellUI(final Player player, final PaginatedGui mainUI, final Material material) {

		final var buySellGUI = new Gui(1, Utilities.colorize("<g:#00bdff:#0009ff>Buy / Sell"));
		buySellGUI.setDefaultClickAction(event -> event.setCancelled(true));
		final var shopMaterial = Shop.ShopPrices.valueOf(material.name());

		final var backButton = ItemBuilder.from(Material.OAK_DOOR).setName(Utilities.colorize("&eBack")).asGuiItem();
		backButton.setAction(event -> {
			mainUI.open(player);
		});
		buySellGUI.setItem(0, backButton);

		//item display btn
		buySellGUI.setItem(4,
				ItemBuilder.from(material)
						.setLore(Utilities.colorize("&aBuy price: &f" + shopMaterial.buy + "&e$"),
								Utilities.colorize("&eSell price: &f" + shopMaterial.sell + "&e$")
						)
						.asGuiItem()
		);

		//buy 1x
		buySellGUI.setItem(2, ItemBuilder.from(Material.GREEN_STAINED_GLASS_PANE)
				.setName(Utilities.colorize("&a&lBuy 1x"))
				.setLore(Utilities.colorize("&f" + shopMaterial.buy + "&a$"))
				.asGuiItem(event -> {
					if (shopMaterial.buy == 0) {
						Utilities.sendMsg(player, Language.SHOP_CANNOT_BUY.getTranslation(player));
						return;
					}

					final var amountToPay = shopMaterial.buy * 1;
					if (!EssentialsXHook.hasEnough(player, amountToPay)) {
						Utilities.sendMsg(player, Language.NO_MONEY.getTranslation(player));
						return;
					}
					EssentialsXHook.subtractPlayerBalance(player, amountToPay);
					final var map = player.getInventory().addItem(new ItemStack(Material.valueOf(shopMaterial.name()), 1));
					Utilities.sendMsg(player, String.format(Language.SHOP_SUCCESS_BUY.getTranslation(player), 1, shopMaterial.name(), amountToPay));
					//if ivnentory was full throw to the ground extra items
					if (!map.isEmpty()) {
						map.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
						Utilities.sendMsg(player, Language.SHOP_NOT_ENOUGH_SPACE.getTranslation(player));
					}
				})
		);

		//buy stack
		buySellGUI.setItem(3, ItemBuilder.from(Material.GREEN_STAINED_GLASS)
				.setName(Utilities.colorize("&a&lBuy " + material.getMaxStackSize() + "x"))
				.setLore(Utilities.colorize("&f" + shopMaterial.buy * material.getMaxStackSize() + "&a$"))
				.asGuiItem(event -> {
					final var maxStack = material.getMaxStackSize();

					if (shopMaterial.buy == 0) {
						Utilities.sendMsg(player, Language.SHOP_CANNOT_BUY.getTranslation(player));
						return;
					}
					final var amountToPay = shopMaterial.buy * maxStack;
					if (!EssentialsXHook.hasEnough(player, amountToPay)) {
						Utilities.sendMsg(player, Language.NO_MONEY.getTranslation(player));
						return;
					}
					EssentialsXHook.subtractPlayerBalance(player, amountToPay);
					final var map = player.getInventory().addItem(new ItemStack(Material.valueOf(shopMaterial.name()), maxStack));
					Utilities.sendMsg(player, String.format(Language.SHOP_SUCCESS_BUY.getTranslation(player), maxStack, shopMaterial.name(), amountToPay));
					//if ivnentory was full throw to the ground extra items
					if (!map.isEmpty()) {
						map.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
						Utilities.sendMsg(player, Language.SHOP_NOT_ENOUGH_SPACE.getTranslation(player));
					}
				})
		);

		//sell 1x
		buySellGUI.setItem(5, ItemBuilder.from(Material.RED_STAINED_GLASS_PANE)
				.setName(Utilities.colorize("&c&lSell 1x"))
				.setLore(Utilities.colorize("&f" + shopMaterial.sell + "&a$"))
				.asGuiItem(event -> {
					if (shopMaterial.sell == 0) {
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
					final var amountToGetPaid = amountOfItemOnInventory * shopMaterial.sell;
					EssentialsXHook.addPlayerBalance(player, amountToGetPaid);
					Utilities.sendMsg(player, String.format(Language.SHOP_SELL_SUCCESS.getTranslation(player), amountOfItemOnInventory, material.name(), Math.round(amountToGetPaid * 100.0) / 100.0));
				})
		);

		//sell Inventory
		buySellGUI.setItem(6, ItemBuilder.from(Material.RED_STAINED_GLASS)
				.setName(Utilities.colorize("&c&lSell Inventory"))
				.asGuiItem(event -> {
					if (shopMaterial.sell == 0) {
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
					final var amountToGetPaid = amountOfItemOnInventory * shopMaterial.sell;
					EssentialsXHook.addPlayerBalance(player, amountToGetPaid);
					Utilities.sendMsg(player, String.format(Language.SHOP_SELL_SUCCESS.getTranslation(player), amountOfItemOnInventory, material.name(), Math.round(amountToGetPaid * 100.0) / 100.0));
				}));

		//exit btn
		buySellGUI.setItem(8, ItemBuilder.from(Material.BARRIER)
				.setName(Utilities.colorize("&4&lClose")).glow(true)
				.asGuiItem(event -> {
					buySellGUI.close(player);
				}));
		buySellGUI.open(player);
	}
}
