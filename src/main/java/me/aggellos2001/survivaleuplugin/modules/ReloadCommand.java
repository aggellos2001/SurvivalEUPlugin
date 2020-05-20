package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.SurvivalEUPlugin;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.command.CommandSender;

@CommandAlias("seureload|seurl")
public final class ReloadCommand extends PluginActivity {

	@Override
	public boolean hasEvents() {
		return false;
	}

	@Override
	public boolean hasCommands() {
		return true;
	}

	@Default
	private void onCommand(final CommandSender sender) {
		SurvivalEUPlugin.config.saveConfig();
		Utilities.sendMsg(sender, "&aReload completed!");
	}
}
