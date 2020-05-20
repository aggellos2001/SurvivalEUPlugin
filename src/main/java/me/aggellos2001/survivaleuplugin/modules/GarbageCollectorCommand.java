package me.aggellos2001.survivaleuplugin.modules;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import me.aggellos2001.survivaleuplugin.utils.PluginActivity;
import me.aggellos2001.survivaleuplugin.utils.Utilities;
import org.bukkit.command.CommandSender;

@CommandAlias("garbage")
@CommandPermission("seu.garbage")
public final class GarbageCollectorCommand extends PluginActivity {

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
		System.gc();
		Utilities.sendMsg(sender,"&aGarbage collector is being called!");
	}


}
