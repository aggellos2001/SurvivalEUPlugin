package me.aggellos2001.survivaleuplugin.shop;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.entity.Player;


@CommandAlias("shop")
public final class Shop extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	public enum ShopPrices {
		//<editor-fold desc="Prices" defaultstate="collapsed">

		ACACIA_LOG(130.32, 6.0),
		ACACIA_PLANKS(32.58, 1.50),
		ACACIA_SAPLING(18.05, 3.0),
		ANVIL(8_379.93, 50),
		APPLE(10.83, 3.0),
		BAMBOO(50.0, 0.1),
		BEETROOT_SEEDS(0.72, 0.20),
		BELL(6_498.0, 600.0),
		BIRCH_LOG(130.32, 6.0),
		BIRCH_PLANKS(32.58, 1.50),
		BIRCH_SAPLING(18.05, 3.0),
		BLACK_CONCRETE(87.21, 3.0),
		BLACK_DYE(11.41, 3.0),
		BLACK_TERRACOTTA(2_102.72, 20.0),
		BLACK_WOOL(206.30, 5.0),
		BLAST_FURNACE(5300.19, 0.5),
		BLAZE_ROD(361.0, 0.3),
		BLUE_CONCRETE(60.67, 3.0),
		BLUE_DYE(22.81, 3.0),
		BLUE_TERRACOTTA(2_093.16, 20.0),
		BLUE_WOOL(147.49, 5.0),
		BONE(18.05, 3.0),
		BROWN_CONCRETE(129.67, 3.0),
		BROWN_DYE(65.16, 3.0),
		BROWN_TERRACOTTA(2_114.48, 20.0),
		BROWN_WOOL(300.39, 5.0),
		CAMPFIRE(513.68, 50.77),
		CARROT(2.71, 0.75),
		CARTOGRAPHY_TABLE(333.67, 0.5),
		CHORUS_FRUIT(18.05, 5.0),
		COAL_BLOCK(938.0, 150.0),
		COBBLESTONE(3.61, 1),
		COD_BUCKET(8_191.21, 0),
		COMPOSTER(1_486.68, 0),
		CONDUIT(18_244.94, 1_000),
		COOKED_BEEF(78.19, 21.66),
		COOKED_CHICKEN(78.19, 12.64),
		COOKED_MUTTON(32.50, 7.22),
		COOKED_PORKCHOP(65.16, 18.05),
		COOKED_SALMON(55.39, 15.34),
		CYAN_CONCRETE(230.50, 3.0),
		CYAN_DYE(182.30, 3.0),
		CYAN_TERRACOTTA(2_088.01, 20.0),
		CYAN_WOOL(723.27, 5.0),
		DARK_OAK_LOG(130.32, 6.0),
		DARK_OAK_PLANKS(32.58, 1.50),
		DARK_OAK_SAPLING(18.05, 3.0),
		DRAGON_BREATH(361.0, 100.0),
		DRAGON_HEAD(3_610.0, 80.0),
		ELYTRA(10_000, 300.0),
		ENCHANTED_GOLDEN_APPLE(500_000.0, 0),
		ENDER_PEARL(180.0, 1.3),
		EXPERIENCE_BOTTLE(1_000, 0),
		FIREWORK_ROCKET(21.30, 0),
		FLETCHING_TABLE(330.06, 0.5),
		GHAST_TEAR(270.75, 75.0),
		GLASS(28.88, 0),
		GLOWSTONE_DUST(50.00, 8.00),
		GRAY_CONCRETE(205.54, 3.0),
		GRAY_DYE(111.73, 3.0),
		GRAY_TERRACOTTA(254.47, 20.0),
		GRAY_WOOL(468.52, 5.0),
		GREEN_CONCRETE(150.90, 3.0),
		GREEN_DYE(78.19, 3.0),
		GREEN_TERRACOTTA(2_102.72, 20.0),
		GREEN_WOOL(347.44, 5.0),
		GRINDSTONE(416.16, 0.5),
		GUNPOWDER(36.10, 5),
		IRON_BLOCK(6_774.61, 300),
		JUNGLE_LOG(130.32, 6.0),
		JUNGLE_PLANKS(32.58, 1.50),
		JUNGLE_SAPLING(18.05, 3.0),
		LANTERN(114.44, 30.0),
		LAPIS_BLOCK(351.87, 60.47),
		LEAD(104.26, 28.88),
		LECTERN(1_478.0, 0.5),
		LIGHT_BLUE_CONCRETE(157.64, 3.0),
		LIGHT_BLUE_DYE(82.33, 3.0),
		LIGHT_BLUE_TERRACOTTA(2_764.42, 20.0),
		LIGHT_BLUE_WOOL(362.37, 5.0),
		LIGHT_GRAY_CONCRETE(181.59, 3.0),
		LIGHT_GRAY_DYE(97.03, 3.0),
		LIGHT_GRAY_TERRACOTTA(2_170.99, 20.0),
		LIGHT_GRAY_WOOL(415.45, 5.0),
		LIME_CONCRETE(320.50, 3.0),
		LIME_DYE(182.30, 3.0),
		LIME_TERRACOTTA(2_093.16, 20.0),
		LIME_WOOL(723.27, 5.0),
		MAGENTA_CONCRETE(334.77, 3.0),
		MAGENTA_DYE(191.07, 3.0),
		MAGENTA_TERRACOTTA(2_087.52, 20.0),
		MAGENTA_WOOL(754.91, 5.0),
		MAP(22_068.28, 500.0),
		MELON_SEEDS(19.55, 5.42),
		MUSIC_DISC_11(361.0, 0),
		MUSIC_DISC_13(361.0, 0),
		MUSIC_DISC_BLOCKS(361.0, 0),
		MUSIC_DISC_CAT(361.0, 0),
		MUSIC_DISC_CHIRP(361.0, 0),
		MUSIC_DISC_FAR(361.0, 0),
		MUSIC_DISC_MALL(361.0, 0),
		MUSIC_DISC_MELLOHI(361.0, 0),
		MUSIC_DISC_STAL(361.0, 0),
		MUSIC_DISC_STRAD(361.0, 0),
		MUSIC_DISC_WAIT(361.0, 0),
		MUSIC_DISC_WARD(361.0, 0),
		NAME_TAG(180.50, 0),
		OAK_LOG(130.32, 6.0),
		OAK_PLANKS(32.58, 1.50),
		OAK_SAPLING(18.05, 3.0),
		ORANGE_CONCRETE(61.84, 3.0),
		ORANGE_DYE(23.52, 3.0),
		ORANGE_TERRACOTTA(2_092.42, 20.0),
		ORANGE_WOOL(150.08, 5.0),
		PAPER(6.52, 1.81),
		PHANTOM_MEMBRANE(18.05, 0),
		PINK_CONCRETE(109.74, 3.0),
		PINK_DYE(52.93, 3.0),
		PINK_TERRACOTTA(280.99, 20.0),
		PINK_WOOL(256.23, 5.0),
		PLAYER_HEAD(3_000_000, 0),
		POTATO(1.81, 0.50),
		PUFFERFISH_BUCKET(8_217_27, 0),
		PUMPKIN(18.05, 3.0),
		PUMPKIN_SEEDS(16.29, 4.51),
		PURPLE_CONCRETE(109.74, 3.0),
		PURPLE_DYE(52.93, 3.0),
		PURPLE_TERRACOTTA(2_093.22, 20.0),
		PURPLE_WOOL(300.39, 5.0),
		RED_CONCRETE(34.14, 3.0),
		RED_DYE(6.52, 1.0),
		RED_TERRACOTTA(181.30, 20.0),
		RED_WOOL(88.68, 5.0),
		SADDLE(361.0, 50.0),
		SALMON_BUCKET(8_194.46, 0),
		SCAFFOLDING(300.37, 3.0),
		SHULKER_BOX(1_462.52, 401.78),
		SKELETON_SKULL(5_000, 0),
		SMITHING_TABLE(563.53, 0.5),
		SMOKER(385.57, 0.5),
		SNOWBALL(3.61, 0.1),
		SPIDER_EYE(10.83, 3.00),
		SPRUCE_LOG(130.32, 6.0),
		SPRUCE_PLANKS(32.58, 1.50),
		SPRUCE_SAPLING(18.05, 3.0),
		STONECUTTER(1_035.01, 0.5),
		STRING(20.42, 0),
		SUGAR_CANE(3.12, 0.3),
		SWEET_BERRIES(5.00, 0),
		TRIDENT(10_000, 1_000),
		TRIPWIRE_HOOK(139.86, 0.5),
		TROPICAL_FISH_BUCKET(8_171.66, 0),
		TURTLE_EGG(18.05, 0),
		WHEAT_SEEDS(3.61, 1.0),
		WHITE_CONCRETE(58.91, 3.0),
		WHITE_DYE(22.81, 3.0),
		WHITE_TERRACOTTA(2_093.89, 20.0),
		WHITE_WOOL(90.05, 3.0),
		ZOMBIE_HEAD(5_000, 0);


		//</editor-fold>

		protected final double buy;
		protected final double sell;

		ShopPrices(final double buy, final double sell) {
			this.buy = buy;
			this.sell = sell;
		}

		//end of enum
	}

	@Default
	private void onCommand(Player player) {
		//Utilities.sendMsg(player, Language.SHOP_INFO.getTranslation(player));
		ShopGUI.openInventory(player);
	}

//	@Subcommand("buy")
//	@CommandCompletion("@shopItems 1")
//	private void buyItem(Player player, ShopPrices item, @Optional Integer amount) {
//
//		if (item.buy == 0) {
//			throw new ConditionFailedException(Language.SHOP_CANNOT_BUY.getTranslation(player));
//		}
//
//		if (amount == null) {
//			Utilities.sendMsg(player, String.format(Language.SHOP_PRICES_BUY.getTranslation(player), item.buy));
//			return;
//		}
//
//		if (amount == 0) {
//			throw new ConditionFailedException(String.format(Language.SHOP_BUY_ZERO.getTranslation(player),item.name()));
//		}
//
//		final var amountToPay = item.buy * amount;
//		if (!EssentialsXHook.hasEnough(player, amountToPay)) {
//			throw new ConditionFailedException(Language.NO_MONEY.getTranslation(player));
//		}
//		EssentialsXHook.subtractPlayerBalance(player, amountToPay);
//		final var map = player.getInventory().addItem(new ItemStack(Material.valueOf(item.name()), amount));
//		Utilities.sendMsg(player, String.format(Language.SHOP_SUCCESS_BUY.getTranslation(player), amount, item.name(), amountToPay));
//
//		//if ivnentory was full
//		if (!map.isEmpty()) {
//			map.forEach((integer, itemStack) -> player.getWorld().dropItemNaturally(player.getLocation(), itemStack));
//			Utilities.sendMsg(player, Language.SHOP_NOT_ENOUGH_SPACE.getTranslation(player));
//		}
//	}
//
//	@Subcommand("sell")
//	@CommandCompletion("@shopItems hand|inventory")
//	private void sell(Player player, ShopPrices item, @Optional String handOrInventory) {
//
//		if (handOrInventory == null) {
//			Utilities.sendMsg(player, String.format(Language.SHOP_PRICES_SELL.getTranslation(player), item.sell));
//			return;
//		}
//
//		if (item.sell == 0) {
//			throw new ConditionFailedException(Language.SHOP_CANNOT_SELL.getTranslation(player));
//
//		}
//		if (handOrInventory.equalsIgnoreCase("hand")){
//			final var itemInHand = player.getInventory().getItemInMainHand();
//			if (!itemInHand.getType().equals(Material.valueOf(item.name()))) {
//				throw new ConditionFailedException(String.format(Language.SHOP_SELL_HAND_DIFFERENT_ITEM.getTranslation(player), Material.valueOf(item.name()), itemInHand.getType()));
//			}
//			final var amountToGetPaid = item.sell * itemInHand.getAmount();
//			player.getInventory().setItemInMainHand(null);
//			EssentialsXHook.addPlayerBalance(player, amountToGetPaid);
//			Utilities.sendMsg(player, String.format(Language.SHOP_SELL_SUCCESS.getTranslation(player), itemInHand.getAmount(), item.name(), amountToGetPaid));
//		}
//		if (handOrInventory.equalsIgnoreCase("inventory")) {
//			final var inv = player.getInventory();
//			var amountOfItemOnInventory = 0;
//			//check every slot for the item
//			for (final ItemStack slot : inv.getContents()) {
//				//slot may be null
//				if (slot == null) continue;
//
//				if (slot.getType().equals(Material.valueOf(item.name()))) {
//					amountOfItemOnInventory += slot.getAmount();
//					slot.setAmount(-1);
//				}
//			}
//			if (amountOfItemOnInventory == 0) {
//				throw new ConditionFailedException(String.format(Language.SHOP_SELL_INVENTORY_NO_ITEM.getTranslation(player), item.name()));
//			}
//			final var amountToGetPaid = amountOfItemOnInventory * item.sell;
//			EssentialsXHook.addPlayerBalance(player, amountToGetPaid);
//			Utilities.sendMsg(player, String.format(Language.SHOP_SELL_SUCCESS.getTranslation(player), amountOfItemOnInventory, item.name(), Math.round(amountToGetPaid * 100.0) / 100.0));
//		}
//	}
}
