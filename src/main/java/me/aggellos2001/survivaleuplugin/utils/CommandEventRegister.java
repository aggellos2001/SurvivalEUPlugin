package me.aggellos2001.survivaleuplugin.utils;

import co.aikar.commands.annotation.CommandAlias;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.hooks.LuckPermsHook;
import me.aggellos2001.survivaleuplugin.languages.PlayerLanguage;
import me.aggellos2001.survivaleuplugin.modules.*;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerDataCommand;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerDataEvent;
import me.aggellos2001.survivaleuplugin.shop.Shop;
import me.aggellos2001.survivaleuplugin.shop.ShopGUI;
import me.aggellos2001.survivaleuplugin.ui.PlayMusicCommand;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import java.lang.reflect.Method;
import java.util.Arrays;

public final class CommandEventRegister {

	private static final SurvivalEUPlugin instance = SurvivalEUPlugin.instance;

	public static void registerCommandsAndListeners() {

		CustomCommandConditions.registerConditions();

		final var commandsEvents = new PluginActivity[]{
				new AlertCommand(),
				new AntiCapsEvent(),
				new AntiSwearingEvent(),
				new BlockVPN(),
				new CommandCoolDownEvent(),
				new DeathMessagesEvent(),
				new DiscordCommand(),
				new DonationBenefitCommand(),
				new EntitySpawningLimitCommandEvent(),
				new GarbageCollectorCommand(),
				new LagInfoCommand(),
				new LeadVillagerEvent(),
				new MakeVillagersLoseJob(),
				new NoEmptyHomeEvent(),
				new PaySelfExploitEvent(),
				new ProtectTamedEvent(),
				new PvPCommand(),
				new ReloadCommand(),
				new SitOnStairsEventCommand(),
				new SlimeChunkCommand(),
				new SlowModeCommandEvent(),
				new ReloadCommand(),
				new TeleportationProtectionEvent(),
				new VoteCommand(),
				new PlayerLanguage(),
				new WelcomerEvent(),
				new WildCommand(),
				new XpAndDropMultiplyCommandEvent(),
				new Shop(),
				LuckPermsHook.INSTANCE,
				new HelpCommand(),
				new UwUCommand(),
				new PlayerDataEvent(),
				new PlayerDataCommand(),
				new VoteKickCommand(),
				new KeepInventoryEvent(),
				new ShopGUI(),
				new PlayMusicCommand(),
		};

		for (final PluginActivity unregisteredCommandEvent : commandsEvents) {

			if (unregisteredCommandEvent.getClass().isAnnotationPresent(CommandAlias.class)) {
				SurvivalEUPlugin.COMMAND_MANAGER.registerCommand(unregisteredCommandEvent);
			}

			if (Arrays.stream(unregisteredCommandEvent.getClass().getDeclaredMethods()).anyMatch(method -> method.isAnnotationPresent(EventHandler.class))){
				Bukkit.getServer().getPluginManager().registerEvents(unregisteredCommandEvent, instance);
			}

		}
	}
}
