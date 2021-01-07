package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Conditions;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.config.Config;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.command.CommandSender;

@CommandAlias("seureload|seurl")
@Conditions("ConsoleOrOp")
public final class ReloadCommand extends PluginActivity {

	@Default
	private void onCommand(final CommandSender sender) {
		Config.loadConfig(SurvivalEUPlugin.instance.getDataFolder());
		Utilities.sendMsg(sender, "&aReload completed!");
	}
}
