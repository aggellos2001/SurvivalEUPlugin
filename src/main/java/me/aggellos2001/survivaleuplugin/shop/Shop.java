package me.aggellos2001.survivaleuplugin.shop;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;


@CommandAlias("shop")
public final class Shop extends PluginActivity {

	public static final double XP_PRICE = 50;

	public Shop() {
	}

//	public enum ShopPrices {
//		//<editor-fold desc="Prices" defaultstate="collapsed">
//
//		ACACIA_LOG(130.32, 6.0),
//		ACACIA_PLANKS(32.58, 1.50),
//		ACACIA_SAPLING(18.05, 3.0),
//		ANVIL(8_379.93, 50),
//		APPLE(10.83, 3.0),
//		BAMBOO(50.0, 0.1),
//		BEETROOT_SEEDS(0.72, 0.20),
//		BELL(6_498.0, 600.0),
//		BIRCH_LOG(130.32, 6.0),
//		BIRCH_PLANKS(32.58, 1.50),
//		BIRCH_SAPLING(18.05, 3.0),
//		BLACK_CONCRETE(87.21, 3.0),
//		BLACK_DYE(11.41, 3.0),
//		BLACK_TERRACOTTA(2_102.72, 20.0),
//		BLACK_WOOL(206.30, 5.0),
//		BLAST_FURNACE(5300.19, 0.5),
//		BLAZE_ROD(361.0, 0.3),
//		BLUE_CONCRETE(60.67, 3.0),
//		BLUE_DYE(22.81, 3.0),
//		BLUE_TERRACOTTA(2_093.16, 20.0),
//		BLUE_WOOL(147.49, 5.0),
//		BONE(18.05, 3.0),
//		BROWN_CONCRETE(129.67, 3.0),
//		BROWN_DYE(65.16, 3.0),
//		BROWN_TERRACOTTA(2_114.48, 20.0),
//		BROWN_WOOL(300.39, 5.0),
//		CAMPFIRE(513.68, 50.77),
//		CARROT(2.71, 0.75),
//		CARTOGRAPHY_TABLE(333.67, 0.5),
//		CHORUS_FRUIT(18.05, 5.0),
//		COAL_BLOCK(938.0, 150.0),
//		COBBLESTONE(3.61, 1),
//		COD_BUCKET(8_191.21, 0),
//		COMPOSTER(1_486.68, 0),
//		CONDUIT(18_244.94, 1_000),
//		COOKED_BEEF(78.19, 21.66),
//		COOKED_CHICKEN(78.19, 12.64),
//		COOKED_MUTTON(32.50, 7.22),
//		COOKED_PORKCHOP(65.16, 18.05),
//		COOKED_SALMON(55.39, 15.34),
//		CYAN_CONCRETE(230.50, 3.0),
//		CYAN_DYE(182.30, 3.0),
//		CYAN_TERRACOTTA(2_088.01, 20.0),
//		CYAN_WOOL(723.27, 5.0),
//		DARK_OAK_LOG(130.32, 6.0),
//		DARK_OAK_PLANKS(32.58, 1.50),
//		DARK_OAK_SAPLING(18.05, 3.0),
//		DRAGON_BREATH(361.0, 100.0),
//		DRAGON_HEAD(3_610.0, 80.0),
//		ELYTRA(10_000, 300.0),
//		ENCHANTED_GOLDEN_APPLE(500_000.0, 0),
//		ENDER_PEARL(180.0, 1.3),
//		EXPERIENCE_BOTTLE(1_000, 0),
//		FIREWORK_ROCKET(21.30, 0),
//		FLETCHING_TABLE(330.06, 0.5),
//		GHAST_TEAR(270.75, 75.0),
//		GLASS(28.88, 0),
//		GLOWSTONE_DUST(50.00, 8.00),
//		GRAY_CONCRETE(205.54, 3.0),
//		GRAY_DYE(111.73, 3.0),
//		GRAY_TERRACOTTA(254.47, 20.0),
//		GRAY_WOOL(468.52, 5.0),
//		GREEN_CONCRETE(150.90, 3.0),
//		GREEN_DYE(78.19, 3.0),
//		GREEN_TERRACOTTA(2_102.72, 20.0),
//		GREEN_WOOL(347.44, 5.0),
//		GRINDSTONE(416.16, 0.5),
//		GUNPOWDER(36.10, 5),
//		IRON_BLOCK(6_774.61, 300),
//		JUNGLE_LOG(130.32, 6.0),
//		JUNGLE_PLANKS(32.58, 1.50),
//		JUNGLE_SAPLING(18.05, 3.0),
//		LANTERN(114.44, 30.0),
//		LAPIS_BLOCK(351.87, 60.47),
//		LEAD(104.26, 28.88),
//		LECTERN(1_478.0, 0.5),
//		LIGHT_BLUE_CONCRETE(157.64, 3.0),
//		LIGHT_BLUE_DYE(82.33, 3.0),
//		LIGHT_BLUE_TERRACOTTA(2_764.42, 20.0),
//		LIGHT_BLUE_WOOL(362.37, 5.0),
//		LIGHT_GRAY_CONCRETE(181.59, 3.0),
//		LIGHT_GRAY_DYE(97.03, 3.0),
//		LIGHT_GRAY_TERRACOTTA(2_170.99, 20.0),
//		LIGHT_GRAY_WOOL(415.45, 5.0),
//		LIME_CONCRETE(320.50, 3.0),
//		LIME_DYE(182.30, 3.0),
//		LIME_TERRACOTTA(2_093.16, 20.0),
//		LIME_WOOL(723.27, 5.0),
//		MAGENTA_CONCRETE(334.77, 3.0),
//		MAGENTA_DYE(191.07, 3.0),
//		MAGENTA_TERRACOTTA(2_087.52, 20.0),
//		MAGENTA_WOOL(754.91, 5.0),
//		MAP(22_068.28, 500.0),
//		MELON_SEEDS(19.55, 5.42),
//		MUSIC_DISC_11(361.0, 0),
//		MUSIC_DISC_13(361.0, 0),
//		MUSIC_DISC_BLOCKS(361.0, 0),
//		MUSIC_DISC_CAT(361.0, 0),
//		MUSIC_DISC_CHIRP(361.0, 0),
//		MUSIC_DISC_FAR(361.0, 0),
//		MUSIC_DISC_MALL(361.0, 0),
//		MUSIC_DISC_MELLOHI(361.0, 0),
//		MUSIC_DISC_STAL(361.0, 0),
//		MUSIC_DISC_STRAD(361.0, 0),
//		MUSIC_DISC_WAIT(361.0, 0),
//		MUSIC_DISC_WARD(361.0, 0),
//		NAME_TAG(180.50, 0),
//		OAK_LOG(130.32, 6.0),
//		OAK_PLANKS(32.58, 1.50),
//		OAK_SAPLING(18.05, 3.0),
//		ORANGE_CONCRETE(61.84, 3.0),
//		ORANGE_DYE(23.52, 3.0),
//		ORANGE_TERRACOTTA(2_092.42, 20.0),
//		ORANGE_WOOL(150.08, 5.0),
//		PAPER(6.52, 1.81),
//		PHANTOM_MEMBRANE(18.05, 0),
//		PINK_CONCRETE(109.74, 3.0),
//		PINK_DYE(52.93, 3.0),
//		PINK_TERRACOTTA(280.99, 20.0),
//		PINK_WOOL(256.23, 5.0),
//		PLAYER_HEAD(3_000_000, 0),
//		POTATO(1.81, 0.50),
//		PUFFERFISH_BUCKET(8_217_27, 0),
//		PUMPKIN(18.05, 3.0),
//		PUMPKIN_SEEDS(16.29, 4.51),
//		PURPLE_CONCRETE(109.74, 3.0),
//		PURPLE_DYE(52.93, 3.0),
//		PURPLE_TERRACOTTA(2_093.22, 20.0),
//		PURPLE_WOOL(300.39, 5.0),
//		RED_CONCRETE(34.14, 3.0),
//		RED_DYE(6.52, 1.0),
//		RED_TERRACOTTA(181.30, 20.0),
//		RED_WOOL(88.68, 5.0),
//		SADDLE(361.0, 50.0),
//		SALMON_BUCKET(8_194.46, 0),
//		SCAFFOLDING(300.37, 3.0),
//		SHULKER_BOX(1_462.52, 401.78),
//		SKELETON_SKULL(5_000, 0),
//		SMITHING_TABLE(563.53, 0.5),
//		SMOKER(385.57, 0.5),
//		SNOWBALL(3.61, 0.1),
//		SPIDER_EYE(10.83, 3.00),
//		SPRUCE_LOG(130.32, 6.0),
//		SPRUCE_PLANKS(32.58, 1.50),
//		SPRUCE_SAPLING(18.05, 3.0),
//		STONECUTTER(1_035.01, 0.5),
//		STRING(20.42, 0),
//		SUGAR_CANE(3.12, 0.3),
//		SWEET_BERRIES(5.00, 0),
//		TRIDENT(10_000, 1_000),
//		TRIPWIRE_HOOK(139.86, 0.5),
//		TROPICAL_FISH_BUCKET(8_171.66, 0),
//		TURTLE_EGG(18.05, 0),
//		WHEAT_SEEDS(3.61, 1.0),
//		WHITE_CONCRETE(58.91, 3.0),
//		WHITE_DYE(22.81, 3.0),
//		WHITE_TERRACOTTA(2_093.89, 20.0),
//		WHITE_WOOL(90.05, 3.0),
//		ZOMBIE_HEAD(5_000, 0);
//
//
//		//</editor-fold>
//
//		protected final double buy;
//		protected final double sell;
//
//		ShopPrices(final double buy, final double sell) {
//			this.buy = buy;
//			this.sell = sell;
//		}
//
//		//end of enum
//	}

	public static double getShopPrice(final Material material) {
		switch (material) {
			// YELLOW (CRAFTABLE) ITEMS
			case STONE:
				return 2.1;
			case SMOOTH_STONE:
				return 3.26;
			case POLISHED_GRANITE:
				return 1.05;
			case COARSE_DIRT:
				return 0.53;
			case OAK_PLANKS:
			case ACACIA_PLANKS:
			case BIRCH_PLANKS:
			case DARK_OAK_PLANKS:
			case JUNGLE_PLANKS:
			case SPRUCE_PLANKS:
				return 2.63;
			case OAK_WOOD:
			case SPRUCE_WOOD:
			case BIRCH_WOOD:
			case JUNGLE_WOOD:
				return 14;
			case SPONGE:
				return 11.55;
			case GLASS:
				return 1.58;
			case LAPIS_BLOCK:
				return 28.35;
			case DISPENSER:
				return 20.02;
			case SANDSTONE:
				return 2.1;
			case CHISELED_SANDSTONE:
				return 2.32;
			case CUT_SANDSTONE:
				return 2.21;
			case SMOOTH_SANDSTONE:
				return 3.26;
			case NOTE_BLOCK:
				return 25.20;
			case POWERED_RAIL:
				return 56.99;
			case DETECTOR_RAIL:
				return 18.94;
			case STICKY_PISTON:
				return 45.42;
			case PISTON:
				return 33.26;
			case ORANGE_WOOL:
				return 5.83;
			case MAGENTA_WOOL:
				return 6.62;
			case LIGHT_BLUE_WOOL:
				return 7.28;
			case YELLOW_WOOL:
				return 5.8;
			case LIME_WOOL:
				return 9.74;
			case PINK_WOOL:
				return 6.55;
			case GRAY_WOOL:
				return 8;
			case LIGHT_GRAY_WOOL:
				return 7.64;
			case CYAN_WOOL:
				return 9.74;
			case PURPLE_WOOL:
				return 10.76;
			case BLUE_WOOL:
				return 7.18;
			case BROWN_WOOL:
				return 10.76;
			case GREEN_WOOL:
				return 11.87;
			case RED_WOOL:
				return 5.8;
			case BLACK_WOOL:
				return 8.56;
			case GOLD_BLOCK:
				return 0;
			case IRON_BLOCK:
				return 158.76;
			case SMOOTH_STONE_SLAB:
				return 1.71;
			case SANDSTONE_SLAB:
				return 1.1;
			case COBBLESTONE_SLAB:
				return 0.53;
			case BRICK_SLAB:
				return 9.26;
			case STONE_BRICK_SLAB:
				return 1.16;
			case NETHER_BRICK_SLAB:
				return 13.89;
			case QUARTZ_SLAB:
				return 38.59;
			case STONE_SLAB:
				return 1.1;
			case ANDESITE_SLAB:
				return 0.53;
			case POLISHED_ANDESITE_SLAB:
				return 0.55;
			case DIORITE_SLAB:
				return 0.53;
			case POLISHED_DIORITE_SLAB:
				return 0.55;
			case GRANITE_SLAB:
				return 0.53;
			case POLISHED_GRANITE_SLAB:
				return 0.55;
			case MOSSY_STONE_BRICK_SLAB:
				return 3.97;
			case MOSSY_COBBLESTONE_SLAB:
				return 3.31;
			case SMOOTH_SANDSTONE_SLAB:
				return 1.71;
			case SMOOTH_RED_SANDSTONE_SLAB:
				return 4.02;
			case SMOOTH_QUARTZ_SLAB:
				return 41.07;
			case RED_NETHER_BRICK_SLAB:
				return 18.19;
			case END_STONE_BRICK_SLAB:
				return 16.18;
			case BRICKS:
				return 17.64;
			case TNT:
				return 54.60;
			case BOOKSHELF:
				return 46.55;
			case MOSSY_COBBLESTONE:
				return 6.3;
			case TORCH:
				return 2.46;
			case OAK_STAIRS:
				return 4.13;
			case CHEST:
				return 22.05;
			case DIAMOND_BLOCK:
				return 0;
			case CRAFTING_TABLE:
				return 11.03;
			case FURNACE:
				return 8.4;
			case LADDER:
				return 3.38;
			case RAIL:
				return 6.71;
			case COBBLESTONE_STAIRS:
				return 1.58;
			case LEVER:
				return 2.5;
			case STONE_PRESSURE_PLATE:
				return 4.41;
			case OAK_PRESSURE_PLATE:
			case SPRUCE_PRESSURE_PLATE:
			case BIRCH_PRESSURE_PLATE:
			case JUNGLE_PRESSURE_PLATE:
			case ACACIA_PRESSURE_PLATE:
			case DARK_OAK_PRESSURE_PLATE:
				return 5.51;
			case REDSTONE_TORCH:
				return 4.6;
			case STONE_BUTTON:
				return 4.41;
			case SNOW_BLOCK:
				return 4.2;
			case CLAY:
				return 12.6;
			case JUKEBOX:
				return 547.05;
			case OAK_FENCE:
				return 4.64;
			case GLOWSTONE:
				return 33.6;
			case JACK_O_LANTERN:
				return 7.83;
			case WHITE_STAINED_GLASS:
				return 1.88;
			case ORANGE_STAINED_GLASS:
				return 1.73;
			case MAGENTA_STAINED_GLASS:
				return 1.82;
			case LIGHT_BLUE_STAINED_GLASS:
				return 1.91;
			case YELLOW_STAINED_GLASS:
				return 1.72;
			case LIME_STAINED_GLASS:
				return 2.21;
			case PINK_STAINED_GLASS:
				return 1.82;
			case GRAY_STAINED_GLASS:
				return 2;
			case LIGHT_GRAY_STAINED_GLASS:
				return 1.95;
			case PURPLE_STAINED_GLASS:
				return 1.82;
			case BLUE_STAINED_GLASS:
				return 1.89;
			case BROWN_STAINED_GLASS:
				return 2.34;
			case GREEN_STAINED_GLASS:
				return 2.48;
			case RED_STAINED_GLASS:
				return 1.72;
			case BLACK_STAINED_GLASS:
				return 2.07;
			case OAK_TRAPDOOR:
			case SPRUCE_TRAPDOOR:
			case BIRCH_TRAPDOOR:
			case JUNGLE_TRAPDOOR:
			case ACACIA_TRAPDOOR:
			case DARK_OAK_TRAPDOOR:
				return 8.27;
			case STONE_BRICKS:
				return 2.21;
			case MOSSY_STONE_BRICKS:
				return 7.57;
			case CRACKED_STONE_BRICKS:
				return 3.37;
			case CHISELED_STONE_BRICKS:
				return 2.43;
			case IRON_BARS:
				return 6.62;
			case GLASS_PANE:
				return 0.62;
			case MELON:
				return 14.18;
			case OAK_FENCE_GATE:
				return 11.30;
			case BRICK_STAIRS:
				return 27.28;
			case STONE_BRICK_STAIRS:
				return 3.47;
			case STONE_STAIRS:
				return 3.31;
			case ANDESITE_STAIRS:
				return 1.58;
			case POLISHED_ANDESITE_STAIRS:
				return 1.65;
			case DIORITE_STAIRS:
				return 1.58;
			case POLISHED_DIORITE_STAIRS:
				return 1.65;
			case GRANITE_STAIRS:
				return 1.58;
			case POLISHED_GRANITE_STAIRS:
				return 1.65;
			case MOSSY_STONE_BRICK_STAIRS:
				return 11.92;
			case MOSSY_COBBLESTONE_STAIRS:
				return 9.92;
			case SMOOTH_SANDSTONE_STAIRS:
				return 5.13;
			case SMOOTH_RED_SANDSTONE_STAIRS:
				return 12.07;
			case SMOOTH_QUARTZ_STAIRS:
				return 123.20;
			case RED_NETHER_BRICK_STAIRS:
				return 54.57;
			case END_STONE_BRICK_STAIRS:
				return 48.24;
			case NETHER_BRICKS:
				return 26.46;
			case CRACKED_NETHER_BRICKS:
				return 28.83;
			case CHISELED_NETHER_BRICKS:
				return 29.17;
			case NETHER_BRICK_FENCE:
				return 20.73;
			case NETHER_BRICK_STAIRS:
				return 41.67;
			case ENCHANTING_TABLE:
				return 1270.01;
			case REDSTONE_LAMP:
				return 47.88;
			case OAK_SLAB:
			case SPRUCE_SLAB:
			case BIRCH_SLAB:
			case JUNGLE_SLAB:
			case ACACIA_SLAB:
			case DARK_OAK_SLAB:
				return 1.38;
			case SANDSTONE_STAIRS:
				return 0.79;
			case ENDER_CHEST:
				return 390.08;
			case TRIPWIRE_HOOK:
				return 10.92;
			case EMERALD_BLOCK:
				return 0;
			case SPRUCE_STAIRS:
			case BIRCH_STAIRS:
			case JUNGLE_STAIRS:
				return 4.13;
			case BEACON:
				return 0;
			case COBBLESTONE_WALL:
				return 1.05;
			case MOSSY_COBBLESTONE_WALL:
				return 6.62;
			case ANDESITE_WALL:
			case DIORITE_WALL:
			case GRANITE_WALL:
				return 1.05;
			case SANDSTONE_WALL:
				return 2.21;
			case RED_SANDSTONE_WALL:
				return 6.62;
			case BRICK_WALL:
				return 18.52;
			case STONE_BRICK_WALL:
				return 2.32;
			case MOSSY_STONE_BRICK_WALL:
				return 7.94;
			case NETHER_BRICK_WALL:
				return 27.28;
			case RED_NETHER_BRICK_WALL:
				return 36.38;
			case END_STONE_BRICK_WALL:
				return 32.16;
			case PRISMARINE_WALL:
				return 22.05;
			case OAK_BUTTON:
			case SPRUCE_BUTTON:
			case BIRCH_BUTTON:
			case JUNGLE_BUTTON:
			case ACACIA_BUTTON:
			case DARK_OAK_BUTTON:
				return 2.76;
			case ANVIL:
				return 570.65;
			case CHIPPED_ANVIL:
				return 285.33;
			case DAMAGED_ANVIL:
				return 142.66;
			case TRAPPED_CHEST:
				return 34.62;
			case LIGHT_WEIGHTED_PRESSURE_PLATE:
				return 112.46;
			case HEAVY_WEIGHTED_PRESSURE_PLATE:
				return 35.28;
			case DAYLIGHT_DETECTOR:
				return 64.43;
			case REDSTONE_BLOCK:
				return 28.35;
			case HOPPER:
				return 111.35;
			case QUARTZ_BLOCK:
				return 73.5;
			case CHISELED_QUARTZ_BLOCK:
				return 81.03;
			case QUARTZ_PILLAR:
				return 77.18;
			case SMOOTH_QUARTZ:
				return 78.23;
			case QUARTZ_BRICKS:
				return 77.18;
			case QUARTZ_STAIRS:
				return 115.76;
			case ACTIVATOR_RAIL:
				return 18.93;
			case DROPPER:
				return 10.50;
			case WHITE_TERRACOTTA:
				return 15.20;
			case ORANGE_TERRACOTTA:
				return 15.17;
			case MAGENTA_TERRACOTTA:
				return 15.19;
			case LIGHT_BLUE_TERRACOTTA:
				return 19.62;
			case YELLOW_TERRACOTTA:
				return 15.06;
			case LIME_TERRACOTTA:
				return 15.18;
			case PINK_TERRACOTTA:
				return 17.54;
			case GRAY_TERRACOTTA:
				return 18.15;
			case LIGHT_GRAY_TERRACOTTA:
				return 16.65;
			case CYAN_TERRACOTTA:
				return 15.06;
			case PURPLE_TERRACOTTA:
				return 15.65;
			case BLUE_TERRACOTTA:
				return 15.24;
			case BROWN_TERRACOTTA:
				return 15.68;
			case GREEN_TERRACOTTA:
				return 15.82;
			case RED_TERRACOTTA:
				return 15.06;
			case BLACK_TERRACOTTA:
				return 15.41;
			case WHITE_STAINED_GLASS_PANE:
				return .45;
			case ORANGE_STAINED_GLASS_PANE:
				return .36;
			case MAGENTA_STAINED_GLASS_PANE:
				return .41;
			case LIGHT_BLUE_STAINED_GLASS_PANE:
				return .45;
			case YELLOW_STAINED_GLASS_PANE:
				return 0.36;
			case LIME_STAINED_GLASS_PANE:
				return .61;
			case PINK_STAINED_GLASS_PANE:
				return .41;
			case GRAY_STAINED_GLASS_PANE:
				return .5;
			case LIGHT_GRAY_STAINED_GLASS_PANE:
				return .47;
			case CYAN_STAINED_GLASS_PANE:
				return .61;
			case PURPLE_STAINED_GLASS_PANE:
				return .41;
			case BLUE_STAINED_GLASS_PANE:
				return .45;
			case BROWN_STAINED_GLASS_PANE:
				return .67;
			case GREEN_STAINED_GLASS_PANE:
				return .74;
			case RED_STAINED_GLASS_PANE:
				return .36;
			case BLACK_STAINED_GLASS_PANE:
				return .53;
			case ACACIA_WOOD:
			case DARK_OAK_WOOD:
				return 14;
			case ACACIA_STAIRS:
			case DARK_OAK_STAIRS:
				return 4.13;
			case SLIME_BLOCK:
				return 94.50;
			case IRON_TRAPDOOR:
				return 70.56;
			case PRISMARINE:
				return 21;
			case PRISMARINE_BRICKS:
				return 47.25;
			case DARK_PRISMARINE:
				return 45.31;
			case PRISMARINE_STAIRS:
				return 33.08;
			case PRISMARINE_BRICK_STAIRS:
				return 74.42;
			case DARK_PRISMARINE_STAIRS:
				return 71.36;
			case PRISMARINE_SLAB:
				return 11.03;
			case PRISMARINE_BRICK_SLAB:
				return 24.81;
			case DARK_PRISMARINE_SLAB:
				return 23.79;
			case SEA_LANTERN:
				return 60.38;
			case HAY_BLOCK:
				return 28.35;
			case WHITE_CARPET:
				return 10.5;
			case ORANGE_CARPET:
				return 11.1;
			case MAGENTA_CARPET:
				return 11.2;
			case LIGHT_BLUE_CARPET:
				return 11.28;
			case YELLOW_CARPET:
				return 11.09;
			case LIME_CARPET:
				return 11.59;
			case PINK_CARPET:
				return 11.19;
			case GRAY_CARPET:
				return 11.37;
			case LIGHT_GRAY_CARPET:
				return 11.32;
			case CYAN_CARPET:
				return 11.59;
			case PURPLE_CARPET:
				return 11.19;
			case BLUE_CARPET:
				return 11.27;
			case BROWN_CARPET:
				return 11.71;
			case GREEN_CARPET:
				return 11.85;
			case RED_CARPET:
				return 11.09;
			case BLACK_CARPET:
				return 11.44;
			case TERRACOTTA:
				return 14.28;
			case COAL_BLOCK:
				return 75.60;
			case PACKED_ICE:
				return 66.15;
			case RED_SANDSTONE:
				return 6.3;
			case CHISELED_RED_SANDSTONE:
				return 7.29;
			case CUT_RED_SANDSTONE:
				return 6.62;
			case SMOOTH_RED_SANDSTONE:
				return 7.67;
			case RED_SANDSTONE_STAIRS:
				return 9.92;
			case RED_SANDSTONE_SLAB:
				return 3.47;
			case SPRUCE_FENCE_GATE:
			case BIRCH_FENCE_GATE:
			case JUNGLE_FENCE_GATE:
			case DARK_OAK_FENCE_GATE:
			case ACACIA_FENCE_GATE:
				return 11.3;
			case SPRUCE_FENCE:
			case BIRCH_FENCE:
			case JUNGLE_FENCE:
			case DARK_OAK_FENCE:
			case ACACIA_FENCE:
				return 4.64;
			case END_ROD:
				return 27.9;
			case PURPUR_BLOCK:
				return 26.46;
			case PURPUR_PILLAR:
				return 29.17;
			case PURPUR_STAIRS:
				return 41.67;
			case PURPUR_SLAB:
				return 13.89;
			case END_STONE_BRICKS:
				return 30.63;
			case MAGMA_BLOCK:
				return 91.88;
			case NETHER_WART_BLOCK:
				return 141.75;
			case RED_NETHER_BRICKS:
				return 34.65;
			case BONE_BLOCK:
				return 16.54;
			case OBSERVER:
				return 30.98;
			case SHULKER_BOX:
				return 75.65;
			case WHITE_SHULKER_BOX:
				return 81.27;
			case ORANGE_SHULKER_BOX:
				return 80.01;
			case MAGENTA_SHULKER_BOX:
				return 80.80;
			case LIGHT_BLUE_SHULKER_BOX:
				return 81.46;
			case YELLOW_SHULKER_BOX:
				return 79.99;
			case LIME_SHULKER_BOX:
				return 83.92;
			case PINK_SHULKER_BOX:
				return 80.74;
			case GRAY_SHULKER_BOX:
				return 82.18;
			case LIGHT_GRAY_SHULKER_BOX:
				return 81.82;
			case CYAN_SHULKER_BOX:
				return 83.92;
			case PURPLE_SHULKER_BOX:
				return 80.74;
			case BLUE_SHULKER_BOX:
				return 81.36;
			case BROWN_SHULKER_BOX:
				return 84.95;
			case GREEN_SHULKER_BOX:
				return 86.05;
			case RED_SHULKER_BOX:
				return 79.99;
			case BLACK_SHULKER_BOX:
				return 82.74;
			case WHITE_GLAZED_TERRACOTTA:
				return 17.01;
			case ORANGE_GLAZED_TERRACOTTA:
				return 16.97;
			case MAGENTA_GLAZED_TERRACOTTA:
				return 17;
			case LIGHT_BLUE_GLAZED_TERRACOTTA:
				return 21.66;
			case YELLOW_GLAZED_TERRACOTTA:
				return 16.87;
			case LIME_GLAZED_TERRACOTTA:
				return 16.99;
			case PINK_GLAZED_TERRACOTTA:
				return 19.47;
			case GRAY_GLAZED_TERRACOTTA:
				return 20.1;
			case LIGHT_GRAY_GLAZED_TERRACOTTA:
				return 18.54;
			case CYAN_GLAZED_TERRACOTTA:
				return 16.87;
			case PURPLE_GLAZED_TERRACOTTA:
				return 17.48;
			case BLUE_GLAZED_TERRACOTTA:
				return 17.05;
			case BROWN_GLAZED_TERRACOTTA:
				return 17.52;
			case GREEN_GLAZED_TERRACOTTA:
				return 17.66;
			case RED_GLAZED_TERRACOTTA:
				return 16.87;
			case BLACK_GLAZED_TERRACOTTA:
				return 17.23;
			case WHITE_CONCRETE:
				return .79;
			case ORANGE_CONCRETE:
				return .63;
			case MAGENTA_CONCRETE:
				return .73;
			case LIGHT_BLUE_CONCRETE:
				return .82;
			case YELLOW_CONCRETE:
				return .62;
			case LIME_CONCRETE:
				return 1.14;
			case PINK_CONCRETE:
				return .72;
			case GRAY_CONCRETE:
				return .91;
			case LIGHT_GRAY_CONCRETE:
				return .86;
			case CYAN_CONCRETE:
				return 1.14;
			case PURPLE_CONCRETE:
				return .72;
			case BLUE_CONCRETE:
				return .8;
			case BROWN_CONCRETE:
				return 1.27;
			case GREEN_CONCRETE:
				return 1.42;
			case RED_CONCRETE:
				return .62;
			case BLACK_CONCRETE:
				return .99;
			case WHITE_CONCRETE_POWDER:
				return .75;
			case ORANGE_CONCRETE_POWDER:
				return .6;
			case MAGENTA_CONCRETE_POWDER:
				return 0.7;
			case LIGHT_BLUE_CONCRETE_POWDER:
				return .78;
			case YELLOW_CONCRETE_POWDER:
				return .59;
			case LIME_CONCRETE_POWDER:
				return 1.09;
			case PINK_CONCRETE_POWDER:
				return .69;
			case GRAY_CONCRETE_POWDER:
				return .87;
			case LIGHT_GRAY_CONCRETE_POWDER:
				return .82;
			case CYAN_CONCRETE_POWDER:
				return 1.09;
			case PURPLE_CONCRETE_POWDER:
				return .69;
			case BLUE_CONCRETE_POWDER:
				return .77;
			case BROWN_CONCRETE_POWDER:
				return 1.21;
			case GREEN_CONCRETE_POWDER:
				return 1.35;
			case RED_CONCRETE_POWDER:
				return .59;
			case BLACK_CONCRETE_POWDER:
				return .94;
			case IRON_SHOVEL:
				return 20.53;
			case IRON_PICKAXE:
			case IRON_AXE:
				return 55.81;
			case FLINT_AND_STEEL:
				return 18.17;
			case BOW:
				return 9.07;
			case ARROW:
				return .62;
			case CHARCOAL:
				return 11.55;
			case IRON_INGOT:
				return 16.8;
			case GOLD_INGOT:
				return 0;
			case IRON_SWORD:
				return 36.73;
			case WOODEN_SWORD:
				return 6.96;
			case WOODEN_PICKAXE:
			case WOODEN_AXE:
				return 11.16;
			case STONE_SWORD:
				return 3.55;
			case STONE_SHOVEL:
				return 3.94;
			case STONE_PICKAXE:
			case STONE_AXE:
				return 6.04;
			case DIAMOND_SWORD:
				return 0;
			case DIAMOND_SHOVEL:
				return 527.89;
			case DIAMOND_PICKAXE:
			case DIAMOND_AXE:
				return 0;
			case STICK:
				return 1.38;
			case BOWL:
				return 2.07;
			case MUSHROOM_STEW:
				return 4.27;
			case GOLDEN_SWORD:
				return 0;
			case GOLDEN_SHOVEL:
				return 59.12;
			case GOLDEN_PICKAXE:
			case GOLDEN_AXE:
				return 171.58;
			case WOODEN_HOE:
				return 8.41;
			case STONE_HOE:
				return 4.99;
			case IRON_HOE:
				return 38.17;
			case DIAMOND_HOE:
				return 1052.89;
			case GOLDEN_HOE:
				return 115.35;
			case BREAD:
				return 9.45;
			case LEATHER_HELMET:
				return 39.38;
			case LEATHER_CHESTPLATE:
				return 63;
			case LEATHER_LEGGINGS:
				return 55.13;
			case LEATHER_BOOTS:
				return 31.50;
			case CHAINMAIL_HELMET:
				return 300;
			case CHAINMAIL_CHESTPLATE:
				return 650;
			case CHAINMAIL_LEGGINGS:
				return 500;
			case CHAINMAIL_BOOTS:
				return 300;
			case IRON_HELMET:
				return 88;
			case IRON_CHESTPLATE:
				return 141.12;
			case IRON_LEGGINGS:
				return 123.48;
			case IRON_BOOTS:
				return 70.56;
			case DIAMOND_HELMET:
				return 0;
			case DIAMOND_CHESTPLATE:
				return 0;
			case DIAMOND_LEGGINGS:
				return 0;
			case DIAMOND_BOOTS:
				return 0;
			case GOLDEN_HELMET:
				return 281.14;
			case GOLDEN_CHESTPLATE:
				return 449.82;
			case GOLDEN_LEGGINGS:
				return 393.59;
			case GOLDEN_BOOTS:
				return 224.91;
			case COOKED_PORKCHOP:
				return 5.25;
			case PAINTING:
				return 16.83;
			case GOLDEN_APPLE:
				return 55.63;
			case ENCHANTED_GOLDEN_APPLE:
				return 100000;
			case OAK_SIGN:
			case SPRUCE_SIGN:
			case BIRCH_SIGN:
			case JUNGLE_SIGN:
			case ACACIA_SIGN:
			case DARK_OAK_SIGN:
				return 5.99;
			case OAK_DOOR:
				return 5.51;
			case BUCKET:
				return 52.92;
			case WATER_BUCKET:
				return 0;
			case LAVA_BUCKET:
				return 0;
			case MINECART:
				return 88.20;
			case IRON_DOOR:
				return 35.28;
			case OAK_BOAT:
				return 13.78;
			case MILK_BUCKET:
				return 56.09;
			case BRICK:
				return 4.20;
			case PAPER:
				return 0.53;
			case BOOK:
				return 9.53;
			case CHEST_MINECART:
				return 115.76;
			case FURNACE_MINECART:
				return 101.43;
			case COMPASS:
				return 144.27;
			case FISHING_ROD:
				return 7.49;
			case CLOCK:
				return 228.06;
			case COOKED_SALMON:
				return 4.46;
			case RED_DYE:
				return 0.53;
			case GREEN_DYE:
				return 6.30;
			case PURPLE_DYE:
				return 1.24;
			case CYAN_DYE:
				return 4.27;
			case LIGHT_GRAY_DYE:
				return 2.27;
			case GRAY_DYE:
				return 2.62;
			case PINK_DYE:
				return 1.24;
			case LIME_DYE:
				return 4.27;
			case YELLOW_DYE:
				return 0.53;
			case LIGHT_BLUE_DYE:
				return 1.93;
			case MAGENTA_DYE:
				return 1.30;
			case ORANGE_DYE:
				return 0.55;
			case BONE_MEAL:
				return 1.75;
			case BLACK_DYE:
				return 3.15;
			case BROWN_DYE:
				return 5.25;
			case BLUE_DYE:
			case WHITE_DYE:
				return 1.84;
			case SUGAR:
				return .53;
			case CAKE:
				return 12.65;
			case WHITE_BED:
				return 24.02;
			case REPEATER:
				return 19.42;
			case COOKIE:
				return 1.44;
			case SHEARS:
				return 35.28;
			case PUMPKIN_SEEDS:
				return 1.31;
			case MELON_SEEDS:
				return 1.58;
			case COOKED_BEEF:
				return 6.30;
			case COOKED_CHICKEN:
				return 3.68;
			case GOLD_NUGGET:
				return 0;
			case GLASS_BOTTLE:
				return 1.65;
			case FERMENTED_SPIDER_EYE:
				return 4.75;
			case BLAZE_POWDER:
				return 52.50;
			case MAGMA_CREAM:
				return 21.88;
			case BREWING_STAND:
				return 108.15;
			case CAULDRON:
				return 123.48;
			case ENDER_EYE:
				return 107.63;
			case GLISTERING_MELON_SLICE:
				return 8.13;
			case FIRE_CHARGE:
				return 74.03;
			case WRITABLE_BOOK:
				return 13.68;
			case ITEM_FRAME:
				return 19.45;
			case FLOWER_POT:
				return 13.23;
			case BAKED_POTATO:
				return 1.58;
			case MAP:
				return 155.89;
			case GOLDEN_CARROT:
				return 53.27;
			case CARROT_ON_A_STICK:
				return 8.65;
			case PUMPKIN_PIE:
				return 6.33;
			case FIREWORK_ROCKET:
				return 11.05;
			case COMPARATOR:
				return 272.70;
			case NETHER_BRICK:
				return 6.30;
			case TNT_MINECART:
				return 149.94;
			case HOPPER_MINECART:
				return 209.53;
			case COOKED_RABBIT:
				return 2.63;
			case RABBIT_STEW:
				return 8.42;
			case ARMOR_STAND:
				return 9.23;
			case LEATHER_HORSE_ARMOR:
				return 39.38;
			case LEAD:
				return 8.40;
			case COOKED_MUTTON:
				return 2.10;
			case BLACK_BANNER:
				return 55.36;
			case RED_BANNER:
				return 37.99;
			case GREEN_BANNER:
				return 76.20;
			case BROWN_BANNER:
				return 69.25;
			case BLUE_BANNER:
				return 46.68;
			case PURPLE_BANNER:
				return 69.25;
			case CYAN_BANNER:
				return 62.78;
			case LIGHT_GRAY_BANNER:
				return 49.56;
			case GRAY_BANNER:
				return 51.84;
			case PINK_BANNER:
				return 42.73;
			case LIME_BANNER:
				return 62.78;
			case YELLOW_BANNER:
				return 37.99;
			case LIGHT_BLUE_BANNER:
				return 47.28;
			case MAGENTA_BANNER:
				return 43.14;
			case ORANGE_BANNER:
				return 38.17;
			case WHITE_BANNER:
				return 32.95;
			case FLOWER_BANNER_PATTERN:
				return 2.13;
			case CREEPER_BANNER_PATTERN:
				return 263.05;
			case SKULL_BANNER_PATTERN:
				return 53.05;
			case MOJANG_BANNER_PATTERN:
				return 10_000;
			case GLOBE_BANNER_PATTERN:
				return 400;
			case PIGLIN_BANNER_PATTERN:
				return 500;
			case END_CRYSTAL:
				return 203.33;
			case SPRUCE_DOOR:
			case BIRCH_DOOR:
			case JUNGLE_DOOR:
			case ACACIA_DOOR:
			case DARK_OAK_DOOR:
				return 5.51;
			case CHORUS_FRUIT:
				return 5;
			case BEETROOT:
				return .6;
			case BEETROOT_SOUP:
				return 5.95;
			case SPECTRAL_ARROW:
				return 17.13;
			case SPRUCE_BOAT:
			case BIRCH_BOAT:
			case JUNGLE_BOAT:
			case ACACIA_BOAT:
			case DARK_OAK_BOAT:
				return 13.78;
			case DRIED_KELP:
				return 2.10;
			case DRIED_KELP_BLOCK:
				return 19.85;
			case COD_BUCKET:
				return 58.72;
			case SALMON_BUCKET:
				return 58.98;
			case TROPICAL_FISH_BUCKET:
				return 57.14;
			case PUFFERFISH_BUCKET:
				return 60.82;
			case BLUE_ICE:
				return 625.12;
			case CONDUIT:
				return 1_470;
			case LOOM:
				return 8.66;
			case CROSSBOW:
				return 36.60;
			case BARREL:
				return 19.43;
			case BLAST_FURNACE:
				return 107.27;
			case SMOKER:
				return 19.85;
			case CARTOGRAPHY_TABLE:
				return 12.13;
			case FLETCHING_TABLE:
				return 12.08;
			case SMITHING_TABLE:
				return 46.31;
			case STONECUTTER:
				return 24.26;
			case GRINDSTONE:
				return 11.01;
			case LECTERN:
				return 54.67;
			case SCAFFOLDING:
				return 2.21;
			case LANTERN:
				return 19.38;
			case CAMPFIRE:
				return 21.01;
			case COMPOSTER:
				return 10.13;
			case SOUL_CAMPFIRE:
				return 50.06;
			case BEEHIVE:
				return 19.69;
			case HONEY_BOTTLE:
				return 2.79;
			case HONEY_BLOCK:
			case HONEYCOMB_BLOCK:
				return 4.20;
			case POLISHED_BASALT:
				return 10.5;
			case CRIMSON_HYPHAE:
			case WARPED_HYPHAE:
				return 17.5;
			case CRIMSON_PLANKS:
			case WARPED_PLANKS:
				return 3.28;
			case CRIMSON_SLAB:
			case WARPED_SLAB:
				return 1.72;
			case CRIMSON_STAIRS:
			case WARPED_STAIRS:
				return 5.17;
			case CRIMSON_FENCE:
			case WARPED_FENCE:
				return 5.56;
			case CRIMSON_FENCE_GATE:
			case WARPED_FENCE_GATE:
				return 12.68;
			case CRIMSON_PRESSURE_PLATE:
			case WARPED_PRESSURE_PLATE:
				return 6.89;
			case CRIMSON_BUTTON:
			case WARPED_BUTTON:
				return 3.45;
			case CRIMSON_DOOR:
			case WARPED_DOOR:
				return 6.89;
			case CRIMSON_TRAPDOOR:
			case WARPED_TRAPDOOR:
				return 10.34;
			case CRIMSON_SIGN:
			case WARPED_SIGN:
				return 7.37;
			case SOUL_TORCH:
				return 11.95;
			case SOUL_LANTERN:
				return 29.34;
			case TARGET:
				return 122.22;
			case RESPAWN_ANCHOR:
				return 735.84;
			case LODESTONE:
				return 3_502.56;
			case WARPED_FUNGUS_ON_A_STICK:
				return 9.18;
			case POLISHED_BLACKSTONE:
				return 1.58;
			case CHISELED_POLISHED_BLACKSTONE:
				return 1.74;
			case BLACKSTONE_SLAB:
				return .79;
			case POLISHED_BLACKSTONE_SLAB:
				return .83;
			case POLISHED_BLACKSTONE_BRICK_SLAB:
				return .87;
			case POLISHED_BLACKSTONE_BRICKS:
				return 1.65;
			case CRACKED_POLISHED_BLACKSTONE_BRICKS:
				return 2.79;
			case BLACKSTONE_STAIRS:
				return 2.36;
			case POLISHED_BLACKSTONE_STAIRS:
				return 2.48;
			case POLISHED_BLACKSTONE_BRICK_STAIRS:
				return 2.6;
			case BLACKSTONE_WALL:
				return 1.58;
			case POLISHED_BLACKSTONE_WALL:
				return 1.65;
			case POLISHED_BLACKSTONE_BRICK_WALL:
				return 1.74;
			case POLISHED_BLACKSTONE_BUTTON:
				return 1.65;
			case POLISHED_BLACKSTONE_PRESSURE_PLATE:
				return 3.31;
			case CHAIN:
				return 21.84;
			//GREEN (BASE) ITEMS
			case WHITE_WOOL:
				return 15;
			case DANDELION:
			case POPPY:
				return 1;
			case BLUE_ORCHID:
				return 2.5;
			case ALLIUM:
				return 5;
			case AZURE_BLUET:
			case RED_TULIP:
			case ORANGE_TULIP:
			case WHITE_TULIP:
			case PINK_TULIP:
			case CORNFLOWER:
			case LILY_OF_THE_VALLEY:
				return 1.75;
			case OXEYE_DAISY:
				return 1.5;
			case WITHER_ROSE:
				return 25;
			case BROWN_MUSHROOM:
			case RED_MUSHROOM:
				return 1;
			case ICE:
				return 7;
			case CACTUS:
				return 5;
			case CARVED_PUMPKIN:
				return 3;
			case NETHERRACK:
				return 5;
			case SOUL_SAND:
				return 10;
			case MUSHROOM_STEM:
				return 2;
			case VINE:
				return 5;
			case MYCELIUM:
				return 10;
			case LILY_PAD:
				return 3;
			case END_STONE:
				return 10;
			case ACACIA_LEAVES:
			case DARK_OAK_LEAVES:
				return 2;
			case ACACIA_LOG:
			case DARK_OAK_LOG:
			case STRIPPED_ACACIA_LOG:
			case STRIPPED_DARK_OAK_LOG:
			case STRIPPED_ACACIA_WOOD:
			case STRIPPED_DARK_OAK_WOOD:
				return 10;
			case SUNFLOWER:
				return 3;
			case LILAC:
				return 2.5;
			case ROSE_BUSH:
			case PEONY:
				return 1;
			case CHORUS_FLOWER:
				return 25;
			case APPLE:
				return 3;
			case STRING:
				return 1.5;
			case FEATHER:
				return .5;
			case GUNPOWDER:
				return 10;
			case WHEAT:
				return 3;
			case SADDLE:
				return 500;
			case REDSTONE:
				return 3;
			case SNOWBALL:
				return 1;
			case LEATHER:
				return 7.5;
			case CLAY_BALL:
				return 3;
			case SUGAR_CANE:
				return .5;
			case COCOA_BEANS:
				return 5;
			case BONE:
				return 5;
			case MELON_SLICE:
				return 1.5;
			case BLAZE_ROD:
				return 100;
			case GHAST_TEAR:
				return 75;
			case SPIDER_EYE:
				return 3;
			case CARROT:
				return .75;
			case POTATO:
				return .5;
			case ZOMBIE_HEAD:
			case SKELETON_SKULL:
			case PLAYER_HEAD:
			case CREEPER_HEAD:
				return 10_000;
			case QUARTZ:
				return 17.5;
			case ELYTRA:
				return 5_000;
			case TOTEM_OF_UNDYING:
			case TRIDENT:
				return 500;
			case BELL:
				return 1_800;
			case SUSPICIOUS_STEW:
				return 10;
			case SWEET_BERRIES:
				return 0.5;
			case BASALT:
				return 10;
			case CRIMSON_FUNGUS:
			case WARPED_FUNGUS:
				return 1.25;
			case CRIMSON_STEM:
			case WARPED_STEM:
			case STRIPPED_CRIMSON_STEM:
			case STRIPPED_WARPED_STEM:
			case STRIPPED_CRIMSON_HYPHAE:
			case STRIPPED_WARPED_HYPHAE:
				return 12.5;
			case SOUL_SOIL:
				return 10;
			case TWISTING_VINES:
			case WEEPING_VINES:
				return 5;
			case CRYING_OBSIDIAN:
				return 100;
			case BLACKSTONE:
				return 1.5;
			case GILDED_BLACKSTONE:
				return 25;
			case OAK_LOG:
			case SPRUCE_LOG:
			case BIRCH_LOG:
			case JUNGLE_LOG:
			case STRIPPED_OAK_LOG:
			case STRIPPED_SPRUCE_LOG:
			case STRIPPED_BIRCH_LOG:
			case STRIPPED_JUNGLE_LOG:
			case STRIPPED_OAK_WOOD:
			case STRIPPED_SPRUCE_WOOD:
			case STRIPPED_BIRCH_WOOD:
			case STRIPPED_JUNGLE_WOOD:
				return 10;
			case OAK_LEAVES:
			case SPRUCE_LEAVES:
			case BIRCH_LEAVES:
			case JUNGLE_LEAVES:
				return 2;
			case WET_SPONGE:
				return 10;
			case LAPIS_ORE:
				return 20;
			case REDSTONE_ORE:
				return 15;
			case IRON_ORE:
				return 15;
			case SAND:
				return .5;
			case RED_SAND:
				return 1.5;
			case GRAVEL:
				return .5;
			case GOLD_ORE:
			case NETHER_GOLD_ORE:
				return 50;
			case COAL_ORE:
				return 10;
			case NETHER_QUARTZ_ORE:
				return 20;
			case ENDER_PEARL:
				return 50;
			case ROTTEN_FLESH:
				return 1;
			case NAME_TAG:
				return 50;
			case MUSIC_DISC_13:
			case MUSIC_DISC_CAT:
			case MUSIC_DISC_BLOCKS:
			case MUSIC_DISC_CHIRP:
			case MUSIC_DISC_MALL:
			case MUSIC_DISC_MELLOHI:
			case MUSIC_DISC_STAL:
			case MUSIC_DISC_STRAD:
			case MUSIC_DISC_WARD:
			case MUSIC_DISC_11:
			case MUSIC_DISC_WAIT:
				return 100;
			case MUSIC_DISC_PIGSTEP:
				return 250;

		}
		return 0;
	}

	@Default
	private void onCommand(final Player player) {
		//Utilities.sendMsg(player, Language.SHOP_INFO.getTranslation(player));
		ShopGUI.mainUI(player,0);
	}

}
