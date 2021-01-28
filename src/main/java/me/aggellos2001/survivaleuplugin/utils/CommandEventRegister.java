package me.aggellos2001.survivaleuplugin.utils;

import co.aikar.commands.annotation.CommandAlias;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.discordreport.ReportPlayerCommand;
import me.aggellos2001.survivaleuplugin.hooks.LuckPermsHook;
import me.aggellos2001.survivaleuplugin.languages.PlayerLanguage;
import me.aggellos2001.survivaleuplugin.modules.*;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerDataCommand;
import me.aggellos2001.survivaleuplugin.playerdata.PlayerDataEvent;
import me.aggellos2001.survivaleuplugin.shop.Shop;
import me.aggellos2001.survivaleuplugin.ui.PlayMusicCommand;
import me.aggellos2001.survivaleuplugin.ui.WarpUICommand;
import me.aggellos2001.survivaleuplugin.vpnprotection.BlockVPN;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;

import java.util.Arrays;

public final class CommandEventRegister {

	private static final SurvivalEUPlugin instance = SurvivalEUPlugin.instance;

	public static void registerCommandsAndListeners() {

		CustomCommandConditions.registerConditions();

		final var commandsEvents = new PluginActivity[]{
				new AlertCommand(),
				new AntiCapsEvent(),
//				new SwearFilterEvent(),
				new CommandCoolDownEvent(),
				new DeathMessagesEvent(),
				new DiscordCommand(),
				new DonationBenefitCommand(),
				new EntitySpawningLimitCommandEvent(),
				new LagInfoCommand(),
				new LeadVillagerEvent(),
				new MakeVillagersLoseJob(),
				new NoEmptyHomeEvent(),
				new PaySelfExploitEvent(),
				new ProtectTamedEvent(),
				new PvPCommandEvent(),
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
				new PlayMusicCommand(),
//				new ServerSnow(),
				new ReportPlayerCommand(),
				new PlayerFinder(),
				new VoteKickCommand(),
				new WarpUICommand(),
				new PlayerWarpCommand(),
				new SignEditorCommand(),
				new BlockVPN(),
//				new FestiveFireworks()
				new StaffChatCommand(),
				new AntiSpamEvent(),
				new AdFilterEvent(),
		};

		for (final PluginActivity unregisteredCommandEvent : commandsEvents) {

			if (unregisteredCommandEvent.getClass().isAnnotationPresent(CommandAlias.class)) {
				SurvivalEUPlugin.COMMAND_MANAGER.registerCommand(unregisteredCommandEvent);
			}

			if (Arrays.stream(unregisteredCommandEvent.getClass().getDeclaredMethods()).anyMatch(method -> method.isAnnotationPresent(EventHandler.class))) {
				Bukkit.getServer().getPluginManager().registerEvents(unregisteredCommandEvent, instance);
			}

		}
	}
}
