package me.aggellos2001.survivaleuplugin.shop;

import me.aggellos2001.survivaleuplugin.hooks.EssentialsXHook;
import me.aggellos2001.survivaleuplugin.languages.Language;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import me.mattstudios.mfgui.gui.components.ItemBuilder;
import me.mattstudios.mfgui.gui.guis.Gui;
import me.mattstudios.mfgui.gui.guis.PaginatedGui;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class ShopGUI {

	//private final PaginatedGui mainGUI;

	public static void mainUI(final Player player, final int filter) {

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

		//info btn
		mainGUI.setItem(6, 1, ItemBuilder.from(Material.PAPER)
				.setName(Utilities.colorize("&eHow to get in-game money ($)?\n")).glow(true)
				.setLore(
						Utilities.colorize("&b1. Breaking blocks (All buyable blocks)&c*"),
						Utilities.colorize("&b2. Killing mobs&c*"),
						Utilities.colorize("&b3. Actively playing (Not being AFK)&c*"),
						Utilities.colorize("&b4. Selling items on /ah"),
						Utilities.colorize("&b5. Voting for the server /vote"),
						Utilities.colorize("&c&o(*) Money drops are random!")
				)
				.asGuiItem()
		);

		//home btn
		mainGUI.setItem(6, 5, ItemBuilder.from(Material.COMPASS)
				.setName(Utilities.colorize("&eHome")).glow(true)
				.asGuiItem(event -> mainUI(((Player) event.getWhoClicked()), 0))
		);

		//food(1) filter button
		mainGUI.setItem(6, 2, ItemBuilder.from(Material.COOKED_BEEF)
				.setName(Utilities.colorize("&eFood"))
				.asGuiItem(event -> mainUI(((Player) event.getWhoClicked()), 1))
		);

		//tools & combat(2) filter button
		mainGUI.setItem(6, 3, ItemBuilder.from(Material.DIAMOND_SWORD)
				.setName(Utilities.colorize("&eTools & Combat"))
				.addItemFlags(ItemFlag.values())
				.asGuiItem(event -> mainUI(((Player) event.getWhoClicked()), 2))
		);

		//transportation(3) filter btn
		mainGUI.setItem(6, 7, ItemBuilder.from(Material.MINECART)
				.setName(Utilities.colorize("&eTransportation"))
				.asGuiItem(event -> mainUI(((Player) event.getWhoClicked()), 3))
		);

		//records(4) filter btn
		mainGUI.setItem(6, 8, ItemBuilder.from(Material.MUSIC_DISC_PIGSTEP)
				.setName(Utilities.colorize("&eMusic Disc"))
				.addItemFlags(ItemFlag.values())
				.asGuiItem(event -> mainUI(((Player) event.getWhoClicked()), 4))
		);



		switch (filter) {
			case 0:
				for (final Material material : Material.values()) {
					final var price = Shop.getShopPrice(material);
					if (price == 0) continue;
					final var guiItem = ItemBuilder.from(material)
							.setLore(Utilities.colorize("&aBuy price: &f" + price + "&a$"))
							.asGuiItem();
					guiItem.setAction(event -> {
						final var clickedMat = event.getInventory().getItem(event.getSlot());
						if (clickedMat == null) return;
						//create and open the buy sell UI
						createBuySellUI(((Player) event.getWhoClicked()), mainGUI, clickedMat.getType());
					});
					mainGUI.addItem(guiItem);
				}
				break;
			case 1:
				for (final Material material : Material.values()) {
					if (!material.isEdible()) continue;
					final var price = Shop.getShopPrice(material);
					if (price == 0) continue;
					final var guiItem = ItemBuilder.from(material)
							.setLore(Utilities.colorize("&aBuy price: &f" + price + "&a$"))
							.asGuiItem();
					guiItem.setAction(event -> {
						final var clickedMat = event.getInventory().getItem(event.getSlot());
						if (clickedMat == null) return;
						//create and open the buy sell UI
						createBuySellUI(((Player) event.getWhoClicked()), mainGUI, clickedMat.getType());
					});
					mainGUI.addItem(guiItem);
				}
				break;
			case 2:
				for (final Material material : Material.values()) {
					if (!Utilities.isTool(material)) continue;
					final var price = Shop.getShopPrice(material);
					if (price == 0) continue;
					final var guiItem = ItemBuilder.from(material)
							.setLore(Utilities.colorize("&aBuy price: &f" + price + "&a$"))
							.asGuiItem();
					guiItem.setAction(event -> {
						final var clickedMat = event.getInventory().getItem(event.getSlot());
						if (clickedMat == null) return;
						//create and open the buy sell UI
						createBuySellUI(((Player) event.getWhoClicked()), mainGUI, clickedMat.getType());
					});
					mainGUI.addItem(guiItem);
				}
				break;
			case 3:
				for (final Material material : Material.values()) {
					if (!Utilities.isTransportation(material)) continue;
					final var price = Shop.getShopPrice(material);
					if (price == 0) continue;
					final var guiItem = ItemBuilder.from(material)
							.setLore(Utilities.colorize("&aBuy price: &f" + price + "&a$"))
							.asGuiItem();
					guiItem.setAction(event -> {
						final var clickedMat = event.getInventory().getItem(event.getSlot());
						if (clickedMat == null) return;
						//create and open the buy sell UI
						createBuySellUI(((Player) event.getWhoClicked()), mainGUI, clickedMat.getType());
					});
					mainGUI.addItem(guiItem);
				}
				break;
			case 4:
				for (final Material material : Material.values()) {
					if (!material.isRecord()) continue;
					final var price = Shop.getShopPrice(material);
					if (price == 0) continue;
					final var guiItem = ItemBuilder.from(material)
							.setLore(Utilities.colorize("&aBuy price: &f" + price + "&a$"))
							.asGuiItem();
					guiItem.setAction(event -> {
						final var clickedMat = event.getInventory().getItem(event.getSlot());
						if (clickedMat == null) return;
						//create and open the buy sell UI
						createBuySellUI(((Player) event.getWhoClicked()), mainGUI, clickedMat.getType());
					});
					mainGUI.addItem(guiItem);
				}
				break;
		}
		mainGUI.getFiller().fillBottom(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).setName(" ").asGuiItem());
		mainGUI.open(player);

	}


	/**
	 * Creates the buy menu and displays it to the player
	 *
	 * @param player
	 * @param mainUI
	 * @param material
	 */
	private static void createBuySellUI(final Player player, final PaginatedGui mainUI, final Material material) {

		final var buyGUI = new Gui(1, Utilities.colorize("<g:#00bdff:#0009ff>Buy " + Utilities.readableEnumName(material.name())));
		buyGUI.setDefaultClickAction(event -> event.setCancelled(true));
		final var price = Shop.getShopPrice(material);

		final var backButton = ItemBuilder.from(Material.OAK_DOOR).setName(Utilities.colorize("&eBack")).asGuiItem();
		backButton.setAction(event -> {
			mainUI.open(player);
		});
		buyGUI.setItem(0, backButton);

		//item display btn
		buyGUI.setItem(4,
				ItemBuilder.from(material)
						.setName(Utilities.colorize("&e&l" + Utilities.readableEnumName(material.name())))
						.setLore(Utilities.colorize("&aBuy price: &f" + price + "&a$")
						)
						.asGuiItem()
		);

		//buy 1x
		buyGUI.setItem(2, ItemBuilder.from(Material.LIME_STAINED_GLASS_PANE)
				.setName(Utilities.colorize("&aBuy 1x"))
				.setLore(Utilities.colorize("&f" + price + "&a$"))
				.asGuiItem(event -> {

					final var amountToPay = price * 1;
					if (!EssentialsXHook.hasEnough(player, amountToPay)) {
						Utilities.sendMsg(player, Language.NO_MONEY.getTranslation(player));
						return;
					}
					EssentialsXHook.subtractPlayerBalance(player, amountToPay);
					final var map = player.getInventory().addItem(new ItemStack(material, 1));
					Utilities.sendMsg(player, String.format(Language.SHOP_SUCCESS_BUY.getTranslation(player), 1, Utilities.readableEnumName(material.name()), amountToPay));
					//if ivnentory was full throw to the ground extra items
					if (!map.isEmpty()) {
						map.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
						Utilities.sendMsg(player, Language.SHOP_NOT_ENOUGH_SPACE.getTranslation(player));
					}
				})
		);

		//buy 1/4 of stack
		buyGUI.setItem(3, ItemBuilder.from(Material.LIME_STAINED_GLASS)
				.setName(Utilities.colorize("&aBuy " + (int) (material.getMaxStackSize() * 0.25) + "x"))
				.setLore(Utilities.colorize("&f" + price * (material.getMaxStackSize() * 0.25) + "&a$"))
				.asGuiItem(event -> {
					final var maxStack = (int) (material.getMaxStackSize() * 0.25);
					final var amountToPay = price * maxStack;
					if (!EssentialsXHook.hasEnough(player, amountToPay)) {
						Utilities.sendMsg(player, Language.NO_MONEY.getTranslation(player));
						return;
					}
					EssentialsXHook.subtractPlayerBalance(player, amountToPay);
					final var map = player.getInventory().addItem(new ItemStack(material, maxStack));
					Utilities.sendMsg(player, String.format(Language.SHOP_SUCCESS_BUY.getTranslation(player), maxStack, Utilities.readableEnumName(material.name()), amountToPay));
					//if inventory was full throw to the ground extra items
					if (!map.isEmpty()) {
						map.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
						Utilities.sendMsg(player, Language.SHOP_NOT_ENOUGH_SPACE.getTranslation(player));
					}
				})
		);

		//buy 2/4
		buyGUI.setItem(5, ItemBuilder.from(Material.LIME_STAINED_GLASS)
				.setName(Utilities.colorize("&aBuy " + (int) (material.getMaxStackSize() * 0.5) + "x"))
				.setLore(Utilities.colorize("&f" + price * (material.getMaxStackSize() * 0.5) + "&a$"))
				.asGuiItem(event -> {
					final var maxStack = (int) (material.getMaxStackSize() * 0.5);
					final var amountToPay = price * maxStack;
					if (!EssentialsXHook.hasEnough(player, amountToPay)) {
						Utilities.sendMsg(player, Language.NO_MONEY.getTranslation(player));
						return;
					}
					EssentialsXHook.subtractPlayerBalance(player, amountToPay);
					final var map = player.getInventory().addItem(new ItemStack(material, maxStack));
					Utilities.sendMsg(player, String.format(Language.SHOP_SUCCESS_BUY.getTranslation(player), maxStack, Utilities.readableEnumName(material.name()), amountToPay));
					//if inventory was full throw to the ground extra items
					if (!map.isEmpty()) {
						map.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
						Utilities.sendMsg(player, Language.SHOP_NOT_ENOUGH_SPACE.getTranslation(player));
					}
				})
		);

		//buy stack
		buyGUI.setItem(6, ItemBuilder.from(Material.LIME_STAINED_GLASS)
				.setName(Utilities.colorize("&aBuy " + material.getMaxStackSize() + "x"))
				.setLore(Utilities.colorize("&f" + price * material.getMaxStackSize() + "&a$"))
				.asGuiItem(event -> {
					final var maxStack = material.getMaxStackSize();
					final var amountToPay = price * maxStack;
					if (!EssentialsXHook.hasEnough(player, amountToPay)) {
						Utilities.sendMsg(player, Language.NO_MONEY.getTranslation(player));
						return;
					}
					EssentialsXHook.subtractPlayerBalance(player, amountToPay);
					final var map = player.getInventory().addItem(new ItemStack(material, maxStack));
					Utilities.sendMsg(player, String.format(Language.SHOP_SUCCESS_BUY.getTranslation(player), maxStack, Utilities.readableEnumName(material.name()), amountToPay));
					//if inventory was full throw to the ground extra items
					if (!map.isEmpty()) {
						map.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
						Utilities.sendMsg(player, Language.SHOP_NOT_ENOUGH_SPACE.getTranslation(player));
					}
				})
		);

		//exit btn
		buyGUI.setItem(8, ItemBuilder.from(Material.BARRIER)
				.setName(Utilities.colorize("&4&lClose")).glow(true)
				.asGuiItem(event -> {
					buyGUI.close(player);
				}));
		buyGUI.open(player);
	}
}
